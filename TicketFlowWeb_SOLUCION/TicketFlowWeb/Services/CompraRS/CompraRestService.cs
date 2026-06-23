using System.Text.Json;
using TicketFlowWeb.Models;

namespace TicketFlowWeb.Services.CompraRS
{
    public class CompraRestService
    {
        private readonly HttpClient _httpClient;
        // Reemplaza con el puerto correcto de tu GlassFish si varía
        private readonly string _baseUrl = "http://localhost:8080/TicketFlow/api/CompraRS";

        public CompraRestService(HttpClient httpClient)
        {
            _httpClient = httpClient;
        }

        /// <summary>
        /// Envía la solicitud de compra a Java y retorna el comprobante procesado.
        /// </summary>
        public async Task<CompraViewModel?> RegistrarCompraAsync(CompraViewModel compraReq)
        {
            try
            {
                // 🌟 CONFIGURACIÓN CRUCIAL: Mantiene las letras tal cual las escribiste en el modelo de C#
                var opcionesSerializador = new JsonSerializerOptions
                {
                    PropertyNamingPolicy = null // Evita que .NET ponga la primera letra en minúscula de forma automática
                };

                // Enviamos el POST a Java forzando el uso de nuestras opciones de nomenclatura
                var response = await _httpClient.PostAsJsonAsync($"{_baseUrl}/registrar", compraReq, opcionesSerializador);

                if (response.IsSuccessStatusCode)
                {
                    // Al leer la respuesta, también usamos las mismas opciones por seguridad
                    return await response.Content.ReadFromJsonAsync<CompraViewModel?>(opcionesSerializador);
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
                Console.WriteLine($"Error de comunicación con el servicio de compras: {ex.Message}");
                return null;
            }
        }
    }
}
