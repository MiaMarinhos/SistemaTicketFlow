using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using TicketFlowWeb.Models;

namespace TicketFlowWeb.Services
{
    public static class AuthEndpoints
    {
        public static IEndpointRouteBuilder MapAuthEndpoints(this IEndpointRouteBuilder endpoints)
        {
            endpoints.MapPost("/auth/login", async Task<IResult> (
                HttpContext context,
                IServicioSesionAuth servicioSesionAuth) =>
            {
                var form = await context.Request.ReadFormAsync();

                var login = new LoginViewModel
                {
                    Usuario = form["usuario"].ToString(),
                    Password = form["password"].ToString(),
                    Rol = form["rol"].ToString()
                };

                var usuario = await servicioSesionAuth.ValidarUsuarioAsync(login);

                if (usuario == null)
                {
                    return login.Rol == "ADMIN"
                        ? Results.Redirect("/loginAdmin")
                        : Results.Redirect("/login");
                }

                var principal = servicioSesionAuth.CrearPrincipal(usuario);

                await context.SignInAsync(
                    CookieAuthenticationDefaults.AuthenticationScheme,
                    principal
                );

                return login.Rol == "ADMIN"
                    ? Results.Redirect("/admin")
                    : Results.Redirect("/");
            });

            endpoints.MapGet("/auth/logout", async Task<IResult> (
                HttpContext context, string? deDonde = null) =>
            {
                await context.SignOutAsync(
                    CookieAuthenticationDefaults.AuthenticationScheme
                );

                return deDonde == "admin"
                    ? Results.Redirect("/loginAdmin")
                    : Results.Redirect("/");
            });

            return endpoints;
        }
    }
}
