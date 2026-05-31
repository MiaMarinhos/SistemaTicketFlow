using Microsoft.AspNetCore.Authentication.Cookies;
using System.Security.Claims;
using TicketFlowBL;
using TicketFlowModel.Usuario;
using TicketFlowWeb.Models;

namespace TicketFlowWeb.Services
{
    public class ServicioSesionAuth : IServicioSesionAuth
    {
        public readonly IUsuarioBL usuarioBL;

        public ServicioSesionAuth()
        {
            usuarioBL = new UsuarioBL();
        }

        public ClaimsPrincipal CrearPrincipal(Usuario usuario)
        {
            var claims = new List<Claim>
            {
                new Claim(ClaimTypes.NameIdentifier, usuario.idUsuario.ToString()),
                new Claim(ClaimTypes.Name, usuario.correoElectronico),
                new Claim("Nombres", usuario.nombre),  //este es adicional
                new Claim(ClaimTypes.Role, usuario.tipo.tipoUsuario)
            };

            var identity = new ClaimsIdentity(
                claims,
                CookieAuthenticationDefaults.AuthenticationScheme
            );

            return new ClaimsPrincipal(identity);
        }

        public Usuario? ValidarUsuario(LoginViewModel login)
        {
            return usuarioBL.ValidarAcceso(login.Usuario, login.Password, login.Rol);
        } 
    }
}
