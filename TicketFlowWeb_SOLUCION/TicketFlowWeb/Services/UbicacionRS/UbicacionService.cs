using System.Net.Http.Json;
using System.Text.Json;
using TicketFlowWeb.Models;

namespace TicketFlowWeb.Services
{
    public class UbicacionRestService
    {
        private readonly HttpClient _httpClient;

        // Configuramos el JSON para ignorar mayúsculas/minúsculas al mapear
        private static readonly JsonSerializerOptions JsonOptions = new()
        {
            PropertyNameCaseInsensitive = true,
            PropertyNamingPolicy = null
        };

        public UbicacionRestService(HttpClient http)
        {
            _httpClient = http;
        }

        public async Task<List<Distrito>> ListarDistritosAsync()
        {
            try
            {
                // Consumimos el endpoint de Java que me mostraste
                var lista = await _httpClient.GetFromJsonAsync<List<Distrito>>("DistritoRS/listar", JsonOptions);
                return lista ?? new List<Distrito>();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error obteniendo distritos: {ex.Message}");
                return new List<Distrito>();
            }
        }
    }
}