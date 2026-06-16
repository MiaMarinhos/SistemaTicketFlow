using System.Security.Claims;
using Microsoft.AspNetCore.Authentication.Cookies;
using TicketFlowWeb.Models;
using TicketFlowWeb.Services.UsuarioRS; // <-- Importamos el servicio REST

namespace TicketFlowWeb.Services
{
    public class ServicioSesionAuth : IServicioSesionAuth
    {
        // YA NO USAMOS usuarioBL directo a BD. Usamos el servicio REST de Java.
        private readonly UsuarioRestService _usuarioRestService;

        // Inyectamos el servicio REST
        public ServicioSesionAuth(UsuarioRestService usuarioRestService)
        {
            _usuarioRestService = usuarioRestService;
        }

        public ClaimsPrincipal CrearPrincipal(Usuario usuario)
        {



            var claims = new List<Claim>
            {
                new Claim(ClaimTypes.NameIdentifier, usuario.idUsuario.ToString()),
                new Claim(ClaimTypes.Name, usuario.correoElectronico),
                new Claim("Nombres", usuario.nombre),
                // Asegúrate de que usuario.tipo no sea null al venir de Java
                new Claim(ClaimTypes.Role, usuario.tipo.tipoUsuario)
            };

            var identity = new ClaimsIdentity(
                claims,
                CookieAuthenticationDefaults.AuthenticationScheme
            );

            return new ClaimsPrincipal(identity);
        }

        // Modificamos este método para que sea asíncrono y llame a Java
        public Usuario? ValidarUsuario(LoginViewModel login)
        {
            // Bloqueamos el hilo síncronamente con .Result para no romper tu interfaz actual AuthEndpoints
            return _usuarioRestService.IniciarSesionAsync(login.Usuario, login.Password, login.Rol).Result;
        }
    }
}