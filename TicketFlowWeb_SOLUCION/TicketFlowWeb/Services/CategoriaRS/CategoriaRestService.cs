using System.Net.Http.Json;
using System.Text.Json;
using TicketFlowWeb.Models;

namespace TicketFlowWeb.Services
{
    public class CategoriaRestService
    {
        private readonly HttpClient _httpClient;

        private static readonly JsonSerializerOptions JsonOptions = new()
        {
            PropertyNameCaseInsensitive = true,
            PropertyNamingPolicy = null
        };

        public CategoriaRestService(HttpClient http)
        {
            _httpClient = http;
        }

        public async Task<List<Categoria>> ListarCategoriasAsync()
        {
            var response = await _httpClient.GetAsync("CategoriaRS/listar");

            if (response.IsSuccessStatusCode)
            {
                var lista = await response.Content.ReadFromJsonAsync<List<Categoria>>(JsonOptions);
                return lista ?? new List<Categoria>();
            }
            else
            {
                var errorMsg = await response.Content.ReadAsStringAsync();
                throw new Exception($"HTTP {response.StatusCode} - {errorMsg}");
            }
        }
    }
}