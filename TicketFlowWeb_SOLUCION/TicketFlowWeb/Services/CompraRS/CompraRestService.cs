using System.Net.Http.Json;
using System.Text.Json;
using System.Text.Json.Serialization;
using TicketFlowWeb.Models.COMPRA;

namespace TicketFlowWeb.Services.CompraRS
{
    public class CompraRestService
    {
        private readonly HttpClient _httpClient;
        private readonly string _baseUrl = "http://localhost:8080/TicketFlow/api/CompraRS";

        public CompraRestService(HttpClient httpClient)
        {
            _httpClient = httpClient;
        }

        public async Task<RegistroCompraVM?> RegistrarCompraAsync(RegistroCompraVM compraReq)
        {
            try
            {
                // Configuración maestra del serializador nativo
                var opcionesSerializador = new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true, // Ignora diferencias de mayúsculas/minúsculas de Java
                    NumberHandling = JsonNumberHandling.AllowReadingFromString | JsonNumberHandling.WriteAsString
                };

                // Añadimos nuestro convertidor seguro corregido
                opcionesSerializador.Converters.Add(new SafeTimeOnlyConverter());

                var response = await _httpClient.PostAsJsonAsync($"{_baseUrl}/registrar", compraReq, opcionesSerializador);

                if (response.IsSuccessStatusCode)
                {
                    return await response.Content.ReadFromJsonAsync<RegistroCompraVM>(opcionesSerializador);
                }
                else
                {
                    var errorMsg = await response.Content.ReadAsStringAsync();
                    Console.WriteLine($"Error de Negocio en Java: {errorMsg}");
                    return null;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error crítico en RegistrarCompraAsync: {ex.Message}");
                return null;
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