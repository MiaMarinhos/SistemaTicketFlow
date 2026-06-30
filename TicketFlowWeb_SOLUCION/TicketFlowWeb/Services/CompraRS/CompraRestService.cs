using System.Net.Http.Json;
using System.Text.Json;
using System.Text.Json.Serialization;
using TicketFlowWeb.Models.COMPRA;

namespace TicketFlowWeb.Services.CompraRS
{
    public class CompraRestService
    {
        private readonly HttpClient _httpClient;
        private readonly string _baseUrl = "http://34.202.176.10:8080/TicketFlow/api/CompraRS";

        public CompraRestService(HttpClient httpClient)
        {
            _httpClient = httpClient;
        }

        public async Task<RegistroCompraVM?> RegistrarCompraAsync(RegistroCompraVM compraReq)
        {
            try
            {
                var opcionesSerializador = new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true,
                    NumberHandling = JsonNumberHandling.AllowReadingFromString | JsonNumberHandling.WriteAsString
                };

                opcionesSerializador.Converters.Add(new SafeTimeOnlyConverter());

                var response = await _httpClient.PostAsJsonAsync($"{_baseUrl}/registrar", compraReq, opcionesSerializador);

                if (response.IsSuccessStatusCode)
                {
                    return await response.Content.ReadFromJsonAsync<RegistroCompraVM>(opcionesSerializador);
                }
                else
                {
                    var errorResult = await response.Content.ReadFromJsonAsync<JsonElement>();
                    string mensajeDesdeJava = "Ocurrió un error inesperado al procesar la compra.";

                    if (errorResult.TryGetProperty("error", out var errorProp))
                    {
                        mensajeDesdeJava = errorProp.GetString() ?? mensajeDesdeJava;
                    }

                    // 🛑 LANZAMOS EL ERROR: Así viaja el mensaje directo hacia la pantalla .razor
                    throw new ApplicationException(mensajeDesdeJava);
                }
            }
            catch (ApplicationException)
            {
                // Re-lanzamos nuestra propia excepción de negocio para que la reciba la pantalla
                throw;
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error crítico en RegistrarCompraAsync: {ex.Message}");
                throw new Exception("No se pudo conectar con el servidor de pasarela.");
            }
        }

        public async Task<List<detalleCompraCliente>> ListarComprasPorClienteAsync(int idCliente)
        {
            try
            {
                var opcionesSerializador = new JsonSerializerOptions
                {
                    PropertyNamingPolicy = null
                };

                var resultado = await _httpClient.GetFromJsonAsync<List<detalleCompraCliente>>(
                    $"{_baseUrl}/listar/{idCliente}",
                    opcionesSerializador
                );

                return resultado ?? new List<detalleCompraCliente>();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"❌ Error de comunicación al listar compras del cliente {idCliente}: {ex.Message}");
                return new List<detalleCompraCliente>();
            }
        }

    }

    public class SafeTimeOnlyConverter : JsonConverter<TimeOnly?>
    {
        public override TimeOnly? Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            var value = reader.GetString();
            if (string.IsNullOrEmpty(value)) return null;

            // Cortamos los milisegundos sobrantes de Java "13:56:26.6461238" -> "13:56:26"
            if (value.Contains('.'))
            {
                value = value.Split('.')[0];
            }

            return TimeOnly.Parse(value);
        }

        public override void Write(Utf8JsonWriter writer, TimeOnly? value, JsonSerializerOptions options)
        {
            if (value == null)
            {
                writer.WriteNullValue();
            }
            else
            {
                writer.WriteStringValue(value.Value.ToString("HH:mm:ss"));
            }
        }
    }



}