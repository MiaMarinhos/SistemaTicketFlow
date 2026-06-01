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

                var usuario = servicioSesionAuth.ValidarUsuario(login);

                if (usuario == null)
                {
                    return Results.Redirect("/login");
                }

                var principal = servicioSesionAuth.CrearPrincipal(usuario);

                await context.SignInAsync(
                    CookieAuthenticationDefaults.AuthenticationScheme,
                    principal
                );

                return Results.Redirect("/");
            });

            endpoints.MapGet("/auth/logout", async Task<IResult> (
                HttpContext context) =>
            {
                await context.SignOutAsync(
                    CookieAuthenticationDefaults.AuthenticationScheme
                );

                return Results.Redirect("/");
            });

            return endpoints;
        }

    }
}
