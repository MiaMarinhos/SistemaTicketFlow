using System.Net.Http.Json;
using TicketFlowWeb.Models;

namespace TicketFlowWeb.Services.UsuarioRS
{
    public class UsuarioRestService
    {
        private readonly HttpClient _httpClient;

        // Inyectamos el HttpClient que configuraste en Program.cs apuntando a Java
        public UsuarioRestService(HttpClient httpClient)
        {
            _httpClient = httpClient;
        }

        public async Task<Usuario?> IniciarSesionAsync(string correo, string password, string rol)
        {
            try
            {
                // 1. Armamos el objeto exactamente como lo espera tu LoginRequest en Java
                var loginRequest = new
                {
                    correo = correo,
                    password = password, // Ojo: tu servicio Java recibe 'password', asegúrate de que el DTO en Java tenga este getter
                    rol = rol
                };

                // 2. Hacemos el POST al endpoint de Java: "UsuarioRS/login"
                // El BaseAddress ya tiene "http://localhost:8080/TicketFlow/api/"
                var response = await _httpClient.PostAsJsonAsync("UsuarioRS/login", loginRequest);

                if (response.IsSuccessStatusCode)
                {
                    // 3. Si Java responde 200 OK, deserializamos el JSON al objeto Usuario de C#
                    var usuario = await response.Content.ReadFromJsonAsync<Usuario>();
                    return usuario;
                }

                // Si devuelve 404 (No encontrado) o 400, retornamos null
                return null;
            }
            catch (Exception ex)
            {
                // Loguear el error si es necesario
                Console.WriteLine($"Error conectando a Java: {ex.Message}");
                return null;
            }
        }
    }
}