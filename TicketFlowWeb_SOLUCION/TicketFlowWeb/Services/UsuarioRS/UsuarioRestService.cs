using System.Net.Http.Json;
using System.Text.Json;
using TicketFlowWeb.Models;

namespace TicketFlowWeb.Services.UsuarioRS
{
    public class UsuarioRestService
    {
        private readonly HttpClient _httpClient;
        private static readonly JsonSerializerOptions JsonOptions = new()
        {
            PropertyNameCaseInsensitive = true
        };

        public UsuarioRestService(HttpClient http)
        {
            _httpClient = http;
            _httpClient.DefaultRequestHeaders.Accept.Clear();
            _httpClient.DefaultRequestHeaders.Accept.Add(
                new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("application/json"));
        }

        public async Task<Usuario?> IniciarSesionAsync(string correo, string password, string rol)
        {
            var loginRequest = new
            {
                correo,
                password,
                rol
            };

            var response = await _httpClient.PostAsJsonAsync("UsuarioRS/login", loginRequest);

            if (!response.IsSuccessStatusCode)
            {
                return null;
            }

            return await response.Content.ReadFromJsonAsync<Usuario>(JsonOptions);
        }

        public async Task RegistrarAsyncCliente(ClienteViewModel cliente)
        {
            var response = await _httpClient.PostAsJsonAsync("ClienteRS/Register", cliente);

            if (!response.IsSuccessStatusCode)
            {
                var detalle = await response.Content.ReadAsStringAsync();
                throw new InvalidOperationException($"No se pudo registrar el cliente. HTTP {(int)response.StatusCode}: {detalle}");
            }
        }

        public async Task<ClientePerfilViewModel?> ObtenerPerfilClienteAsync(int idUsuario)
        {
            if (idUsuario <= 0)
            {
                return null;
            }

            var response = await _httpClient.GetAsync($"ClienteRS/perfil/{idUsuario}");

            if (!response.IsSuccessStatusCode)
            {
                var detalle = await response.Content.ReadAsStringAsync();
                Console.WriteLine($"Error obteniendo perfil de cliente. HTTP {(int)response.StatusCode}: {detalle}");
                return null;
            }

            return await response.Content.ReadFromJsonAsync<ClientePerfilViewModel>(JsonOptions);
        }

        public async Task<List<Distrito>> ObtenerDistritosAsync()
        {
            try
            {
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
