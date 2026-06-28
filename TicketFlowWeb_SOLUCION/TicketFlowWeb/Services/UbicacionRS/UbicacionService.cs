using System.Net.Http.Json;
using System.Text.Json;
using TicketFlowWeb.Models;

namespace TicketFlowWeb.Services
{
    public class UbicacionRestService
    {
        private readonly HttpClient _httpClient;

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
            // Hacemos la petición a Java
            var response = await _httpClient.GetAsync("DistritoRS/listar");

            if (response.IsSuccessStatusCode)
            {
                // Si Java responde bien (200 OK), mapeamos los datos
                var lista = await response.Content.ReadFromJsonAsync<List<Distrito>>(JsonOptions);
                return lista ?? new List<Distrito>();
            }
            else
            {
                // Si falla, CAPTURAMOS el error exacto y lo lanzamos a la vista
                var errorMsg = await response.Content.ReadAsStringAsync();
                throw new Exception($"HTTP {response.StatusCode} - {errorMsg}");
            }
        }
    }
}