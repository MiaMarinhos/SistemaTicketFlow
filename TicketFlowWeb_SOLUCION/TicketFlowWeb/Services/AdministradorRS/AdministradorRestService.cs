using TicketFlowWeb.Models;
using System.Net.Http.Json;
using TicketFlowWeb.Models;
using TicketFlowWeb.Services.UsuarioRS;
using System.Text.Json;

namespace TicketFlowWeb.Services.AdministradorRS
{
    public class AdministradorRestService
    {
        private readonly HttpClient _http;

        public AdministradorRestService(HttpClient http)
        {
            _http = http;
        }

        public async Task<AdministratorViewModel?> ObtenerAdministrador(int id)
        {
            var response = await _http.GetAsync($"AdminBasico/{id}");

            var contenido = await response.Content.ReadAsStringAsync();

            if (!response.IsSuccessStatusCode)
            {
                throw new Exception(contenido);
            }

            return await response.Content.ReadFromJsonAsync<AdministratorViewModel>();
        }

        public async Task<AdministratorViewModel?> ActualizarAdministrador(int id, AdministratorViewModel administrador)
        {
            var response = await _http.PutAsJsonAsync(
                $"AdminBasico/actualizar/{id}",
                administrador
            );

            var contenido = await response.Content.ReadAsStringAsync();

            if (!response.IsSuccessStatusCode)
                throw new Exception(contenido);

            return await response.Content.ReadFromJsonAsync<AdministratorViewModel>();
        }

        public async Task<List<Usuario>> ListarUsuarios()
        {
            return await _http.GetFromJsonAsync<List<Usuario>>(
                "AdminUsuarios/listar"
            ) ?? new List<Usuario>();
        }

        //Solicitudes
        public async Task<List<SolicitudViewModel>> ListarSolicitudes()
        {
            return await _http.GetFromJsonAsync<List<SolicitudViewModel>>(
                "AdminSolicitudes/listar"
            ) ?? new List<SolicitudViewModel>();
        }
        public async Task<SolicitudViewModel?> AprobarSolicitud(int id)
        {
            var response = await _http.PutAsync(
                $"AdminSolicitudes/aprobar/{id}",
                null
            );

            var contenido = await response.Content.ReadAsStringAsync();

            if (!response.IsSuccessStatusCode)
                throw new Exception(contenido);

            return await response.Content.ReadFromJsonAsync<SolicitudViewModel>();
        }

        public async Task<SolicitudViewModel?> RechazarSolicitud(int id)
        {
            var response = await _http.PutAsync(
                $"AdminSolicitudes/rechazar/{id}",
                null
            );

            var contenido = await response.Content.ReadAsStringAsync();

            if (!response.IsSuccessStatusCode)
                throw new Exception(contenido);

            return await response.Content.ReadFromJsonAsync<SolicitudViewModel>();
        }
        //EVENTOS
        public async Task<List<EventoViewModel>> ListarEventos()
        {
            return await _http.GetFromJsonAsync<List<EventoViewModel>>(
                "AdminEventos/listar"
            ) ?? new List<EventoViewModel>();
        }

        public async Task<EventoViewModel?> AprobarEvento(int id)
        {
            var response = await _http.PutAsync(
                $"AdminEventos/aprobar/{id}",
                null
            );

            var contenido = await response.Content.ReadAsStringAsync();

            if (!response.IsSuccessStatusCode)
                throw new Exception(contenido);

            return await response.Content.ReadFromJsonAsync<EventoViewModel>();
        }

        public async Task<EventoViewModel?> RechazarEvento(int id)
        {
            var response = await _http.PutAsync(
                $"AdminEventos/rechazar/{id}",
                null
            );

            var contenido = await response.Content.ReadAsStringAsync();

            if (!response.IsSuccessStatusCode)
                throw new Exception(contenido);

            return await response.Content.ReadFromJsonAsync<EventoViewModel>();
        }

        public async Task<Usuario?> ObtenerUsuario(int id)
        {
            return await _http.GetFromJsonAsync<Usuario>($"AdminUsuarios/{id}");
        }



        public async Task<Usuario?> ActualizarUsuario(int id, Usuario usuario)
        {
            var response = await _http.PutAsJsonAsync($"AdminUsuarios/actualizar/{id}", usuario);
            var contenido = await response.Content.ReadAsStringAsync();

            if (!response.IsSuccessStatusCode)
                throw new Exception(contenido);

            return await response.Content.ReadFromJsonAsync<Usuario>();
        }

        public async Task<Usuario?> BloquearUsuario(int id)
        {
            var response = await _http.PutAsync($"AdminUsuarios/bloquear/{id}", null);
            var contenido = await response.Content.ReadAsStringAsync();

            if (!response.IsSuccessStatusCode)
                throw new Exception(contenido);

            return await response.Content.ReadFromJsonAsync<Usuario>();
        }

        public async Task<Usuario?> DesbloquearUsuario(int id)
        {
            var response = await _http.PutAsync($"AdminUsuarios/desbloquear/{id}", null);
            var contenido = await response.Content.ReadAsStringAsync();

            if (!response.IsSuccessStatusCode)
                throw new Exception(contenido);

            return await response.Content.ReadFromJsonAsync<Usuario>();
        }

        public async Task<List<Usuario>> FiltrarUsuariosPorTipo(int idTipoUsuario)
        {
            return await _http.GetFromJsonAsync<List<Usuario>>(
                $"AdminUsuarios/filtrar/tipo/{idTipoUsuario}"
            ) ?? new List<Usuario>();
        }

        public async Task<List<Usuario>> FiltrarUsuariosPorEstado(int idEstado)
        {
            return await _http.GetFromJsonAsync<List<Usuario>>(
                $"AdminUsuarios/filtrar/estado/{idEstado}"
            ) ?? new List<Usuario>();
        }
        public async Task<Usuario?> RegistrarUsuario(object usuarioRegistro)
        {
            var response = await _http.PostAsJsonAsync(
                "AdminUsuarios/registrar",
                usuarioRegistro
            );

            var contenido = await response.Content.ReadAsStringAsync();

            if (!response.IsSuccessStatusCode)
                throw new Exception(contenido);

            return await response.Content.ReadFromJsonAsync<Usuario>();
        }
        public async Task<List<GeneroViewModel>> ListarGeneros()
        {
            return await _http.GetFromJsonAsync<List<GeneroViewModel>>(
                "AdminUsuarios/generos"
            ) ?? new List<GeneroViewModel>();
        }

        public async Task<List<DistritoViewModel>> ListarDistritos()
        {
            return await _http.GetFromJsonAsync<List<DistritoViewModel>>(
                "AdminUsuarios/distritos"
            ) ?? new List<DistritoViewModel>();
        }

        public async Task<List<EventoViewModel>> FiltrarEventosPorEstado(int idEstadoEvento)
        {
            return await _http.GetFromJsonAsync<List<EventoViewModel>>(
                $"AdminEventos/filtrar/estado/{idEstadoEvento}"
            ) ?? new List<EventoViewModel>();
        }

        public async Task<EventoViewModel?> ObtenerDetalleEvento(int id)
        {
            return await _http.GetFromJsonAsync<EventoViewModel>(
                $"AdminEventos/detalle/{id}"
            );
        }

        public async Task<EventoViewModel?> RegistrarEvento(object evento)
        {
            var options = new JsonSerializerOptions
            {
                PropertyNamingPolicy = null
            };

            var response = await _http.PostAsJsonAsync(
                "AdminEventos/registrar",
                evento,
                options
            );

            var contenido = await response.Content.ReadAsStringAsync();

            if (!response.IsSuccessStatusCode)
                throw new Exception(contenido);

            return await response.Content.ReadFromJsonAsync<EventoViewModel>();
        }

        public async Task<EventoViewModel?> ActualizarEvento(int id, object evento)
        {
            var options = new JsonSerializerOptions
            {
                PropertyNamingPolicy = null
            };

            var response = await _http.PutAsJsonAsync(
                $"AdminEventos/actualizar/{id}",
                evento,
                options
            );

            var contenido = await response.Content.ReadAsStringAsync();

            if (!response.IsSuccessStatusCode)
                throw new Exception(contenido);

            return await response.Content.ReadFromJsonAsync<EventoViewModel>();
        }

        public async Task<EventoViewModel?> EliminarEvento(int id)
        {
            var response = await _http.PutAsync(
                $"AdminEventos/eliminar/{id}",
                null
            );

            var contenido = await response.Content.ReadAsStringAsync();

            if (!response.IsSuccessStatusCode)
                throw new Exception(contenido);

            return await response.Content.ReadFromJsonAsync<EventoViewModel>();
        }

        public async Task<List<CategoriaEventoViewModel>> ListarCategoriasEvento()
        {
            return await _http.GetFromJsonAsync<List<CategoriaEventoViewModel>>(
                "AdminEventos/categorias"
            ) ?? new List<CategoriaEventoViewModel>();
        }

        public async Task<List<SolicitudViewModel>> FiltrarSolicitudesPorEstado(int idEstado)
        {
            return await _http.GetFromJsonAsync<List<SolicitudViewModel>>(
                $"AdminSolicitudes/filtrar/estado/{idEstado}"
            ) ?? new List<SolicitudViewModel>();
        }
    }
}
