using System.Net.Http.Json;
using System.Text.Json;
using TicketFlowWeb.Models;

namespace TicketFlowWeb.Services.AnfitrionRS
{
    public class AnfitrionRestService
    {
        private readonly HttpClient _httpClient;

        // Configuramos el JSON para ignorar diferencias de mayúsculas y minúsculas
        private static readonly JsonSerializerOptions JsonOptions = new()
        {
            PropertyNameCaseInsensitive = true,
            PropertyNamingPolicy = null
        };

        public AnfitrionRestService(HttpClient http)
        {
            _httpClient = http;
            _httpClient.DefaultRequestHeaders.Accept.Clear();
            _httpClient.DefaultRequestHeaders.Accept.Add(
                new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("application/json"));
        }

        // ==========================================
        // 1. GESTIÓN DEL ANFITRIÓN
        // ==========================================

        public async Task<PerfilAnfitrionViewModel?> RegistrarAnfitrionAsync(PerfilAnfitrionViewModel anfitrion)
        {
            var response = await _httpClient.PostAsJsonAsync("AnfitrionRS/registrar", anfitrion, JsonOptions);

            if (response.IsSuccessStatusCode)
            {
                return await response.Content.ReadFromJsonAsync<PerfilAnfitrionViewModel>(JsonOptions);
            }

            var errorMsg = await response.Content.ReadAsStringAsync();
            Console.WriteLine($"Error al registrar anfitrión: {errorMsg}");
            return null;
        }

        public async Task<PerfilAnfitrionViewModel?> BuscarAnfitrionAsync(int idAnfitrion)
        {
            if (idAnfitrion <= 0) return null;

            var response = await _httpClient.GetAsync($"AnfitrionRS/{idAnfitrion}");

            if (response.IsSuccessStatusCode)
            {
                return await response.Content.ReadFromJsonAsync<PerfilAnfitrionViewModel>(JsonOptions);
            }
            return null;
        }

        public async Task<bool> EditarPerfilAsync(PerfilAnfitrionViewModel anfitrion)
        {
            var response = await _httpClient.PutAsJsonAsync("AnfitrionRS/editarPerfil", anfitrion, JsonOptions);
            return response.IsSuccessStatusCode;
        }

        // ==========================================
        // 2. GESTIÓN DE EVENTOS
        // ==========================================

        public async Task<EventoViewModel?> CrearEventoAsync(EventoViewModel evento)
        {
            var response = await _httpClient.PostAsJsonAsync("AnfitrionRS/eventos/crear", evento, JsonOptions);
            if (response.IsSuccessStatusCode)
            {
                return await response.Content.ReadFromJsonAsync<EventoViewModel>(JsonOptions);
            }
            return null;
        }

        public async Task<EventoViewModel?> ObtenerEventoAsync(int idEvento)
        {
            var response = await _httpClient.GetAsync($"AnfitrionRS/eventos/{idEvento}");
            if (response.IsSuccessStatusCode)
            {
                return await response.Content.ReadFromJsonAsync<EventoViewModel>(JsonOptions);
            }
            return null;
        }

        public async Task<bool> ActualizarEventoAsync(int idEvento, EventoViewModel evento)
        {
            var response = await _httpClient.PutAsJsonAsync($"AnfitrionRS/eventos/actualizar/{idEvento}", evento, JsonOptions);
            return response.IsSuccessStatusCode;
        }

        public async Task<bool> EliminarEventoAsync(int idEvento)
        {
            var response = await _httpClient.DeleteAsync($"AnfitrionRS/eventos/eliminar/{idEvento}");
            return response.IsSuccessStatusCode;
        }

        public async Task<List<EventoViewModel>> VerTodosLosEventosAsync()
        {
            try
            {
                var lista = await _httpClient.GetFromJsonAsync<List<EventoViewModel>>("AnfitrionRS/eventos/todos", JsonOptions);
                return lista ?? new List<EventoViewModel>();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error obteniendo eventos del anfitrión: {ex.Message}");
                return new List<EventoViewModel>();
            }
        }

        // ==========================================
        // 3. GESTIÓN DE COMPRAS Y PAGOS
        // ==========================================

        public async Task<List<CompraViewModel>> VerComprasDeSusEventosAsync(int idAnfitrion)
        {
            try
            {
                var lista = await _httpClient.GetFromJsonAsync<List<CompraViewModel>>($"AnfitrionRS/{idAnfitrion}/compras", JsonOptions);
                return lista ?? new List<CompraViewModel>();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error obteniendo compras: {ex.Message}");
                return new List<CompraViewModel>();
            }
        }

        public async Task<List<PagoViewModel>> VerPagosDeSusEventosAsync(int idAnfitrion)
        {
            try
            {
                var lista = await _httpClient.GetFromJsonAsync<List<PagoViewModel>>($"AnfitrionRS/{idAnfitrion}/pagos", JsonOptions);
                return lista ?? new List<PagoViewModel>();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error obteniendo pagos: {ex.Message}");
                return new List<PagoViewModel>();
            }
        }
    }
}
