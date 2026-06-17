using System.Security.Claims;
using Microsoft.AspNetCore.Authentication.Cookies;
using TicketFlowWeb.Models;
using TicketFlowWeb.Services.UsuarioRS;

namespace TicketFlowWeb.Services
{
    public class ServicioSesionAuth : IServicioSesionAuth
    {
        private readonly UsuarioRestService _usuarioRestService;

        public ServicioSesionAuth(UsuarioRestService usuarioRestService)
        {
            _usuarioRestService = usuarioRestService;
        }

        public ClaimsPrincipal CrearPrincipal(Usuario usuario)
        {
            var rol = usuario.tipo?.tipoUsuario ?? string.Empty;

            var claims = new List<Claim>
            {
                new Claim(ClaimTypes.NameIdentifier, usuario.idUsuario.ToString()),
                new Claim(ClaimTypes.Name, usuario.correoElectronico ?? string.Empty),
                new Claim("Nombres", usuario.nombre ?? string.Empty),
                new Claim(ClaimTypes.Role, rol)
            };

            var identity = new ClaimsIdentity(
                claims,
                CookieAuthenticationDefaults.AuthenticationScheme
            );

            return new ClaimsPrincipal(identity);
        }

        public Task<Usuario?> ValidarUsuarioAsync(LoginViewModel login)
        {
            return _usuarioRestService.IniciarSesionAsync(login.Usuario, login.Password, login.Rol);
        }
    }
}
