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
                throw new InvalidOperationException("Ya existe una cuenta con ese correo. ");
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

        public async Task<bool> ActualizarPerfilClienteAsync(int idUsuario, ClientePerfilViewModel perfil)
        {
            if (idUsuario <= 0 || perfil is null)
            {
                return false;
            }

            var dto = new
            {
                dni = perfil.dni,
                nombre = perfil.nombre,
                apellidoPaterno = perfil.apellidoPaterno,
                apellidoMaterno = perfil.apellidoMaterno,
                telefono = perfil.telefono,
                edad = perfil.edad,
                idGenero = perfil.genero?.idGenero ?? 1,
                correoElectronico = perfil.correoElectronico,

                
                contrasena = (string?)null,

                idDistrito = perfil.idDistrito
            };

            var response = await _httpClient.PutAsJsonAsync($"ClienteRS/perfil/{idUsuario}", dto, JsonOptions);

            if (!response.IsSuccessStatusCode)
            {
                var detalle = await response.Content.ReadAsStringAsync();
                Console.WriteLine($"Error actualizando perfil cliente. HTTP {(int)response.StatusCode}: {detalle}");
                return false;
            }

            return true;
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

        public async Task<int> ObtenerPuntosBonusAsync(int idUsuario)
        {
            if (idUsuario <= 0)
            {
                return -1;
            }

            var response = await _httpClient.GetAsync($"ClienteRS/verPuntosBonus/{idUsuario}");

            if (!response.IsSuccessStatusCode)
            {
                var detalle = await response.Content.ReadAsStringAsync();
                Console.WriteLine($"Error obteniendo puntos bonus de cliente. HTTP {(int)response.StatusCode}: {detalle}");
                return -1;
            }

            return await response.Content.ReadFromJsonAsync<int>(JsonOptions);
        }

        public async Task EnviarSolicitud(SolicitudViewModel solicitud)
        {
            var response = await _httpClient.PostAsJsonAsync("ClienteRS/enviarSolicitud", solicitud);

            if (!response.IsSuccessStatusCode)
            {
                var detalle = await response.Content.ReadAsStringAsync();
                throw new Exception($"Error del servidor: {detalle}");
            }
        }
    }

      
}