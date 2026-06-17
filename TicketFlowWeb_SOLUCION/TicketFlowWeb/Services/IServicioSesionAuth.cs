using System.Security.Claims;
using TicketFlowWeb.Models;

namespace TicketFlowWeb.Services
{
    public interface IServicioSesionAuth
    {
        Task<Usuario?> ValidarUsuarioAsync(LoginViewModel login);
        ClaimsPrincipal CrearPrincipal(Usuario usuario);
    }
}
