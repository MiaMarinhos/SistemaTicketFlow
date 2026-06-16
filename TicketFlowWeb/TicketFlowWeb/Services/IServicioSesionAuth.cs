using System.Security.Claims;
using TicketFlowWeb.Models;

namespace TicketFlowWeb.Services
{
    public interface IServicioSesionAuth
    {
        Usuario? ValidarUsuario(LoginViewModel login);
        ClaimsPrincipal CrearPrincipal(Usuario usuario);


    }
}
