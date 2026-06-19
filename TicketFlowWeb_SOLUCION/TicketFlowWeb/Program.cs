using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Components;
using TicketFlowWeb.Components;
using TicketFlowWeb.Services;
using TicketFlowWeb.Services.EventoRS;
using TicketFlowWeb.Services.UsuarioRS;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();
//--------------------------------
builder.Services.AddHttpClient<EventoRestService>(client =>
{
    client.BaseAddress = new Uri("http://localhost:8080/TicketFlow/api/");
});
builder.Services.AddHttpClient<UsuarioRestService>(client =>
{
    client.BaseAddress = new Uri("http://localhost:8080/TicketFlow/api/");
});

//----------------------------------------------------------

builder.Services.AddScoped<IServicioSesionAuth, ServicioSesionAuth>();
builder.Services.AddHttpContextAccessor();

builder.Services.AddAuthentication(CookieAuthenticationDefaults.AuthenticationScheme)
    .AddCookie(opciones =>
    {
        opciones.LoginPath = "/login";
        opciones.LogoutPath = "/auth/logout";
        opciones.ExpireTimeSpan = TimeSpan.FromHours(8);

        // INTERCEPTOR DINÁMICO DE REDIRECCIÓN
        opciones.Events = new CookieAuthenticationEvents
        {
            OnRedirectToLogin = redirectContext =>
            {
                // Si la URL a la que intentan entrar empieza con /admin, lo mandamos al login de Admin
                if (redirectContext.Request.Path.StartsWithSegments("/admin"))
                {
                    redirectContext.Response.Redirect("/loginAdmin");
                }
                else
                {
                    // Si no, lo mandamos al login normal (Clientes / Anfitriones)
                    redirectContext.Response.Redirect("/login");
                }

                return Task.CompletedTask;
            }
        };
    });

builder.Services.AddAuthorization();
builder.Services.AddCascadingAuthenticationState();

builder.Services.AddScoped(sp =>
{
    var navigationManager = sp.GetRequiredService<NavigationManager>();

    return new HttpClient
    {
        BaseAddress = new Uri(navigationManager.BaseUri)
    };
});

//-------------------------------------------------------

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}
app.UseStatusCodePagesWithReExecute("/not-found", createScopeForStatusCodePages: true);
app.UseHttpsRedirection();

app.UseAntiforgery();

app.MapStaticAssets();

//-------------------------------------------------------------
app.UseAuthentication();
app.UseAuthorization();
app.MapAuthEndpoints();

//-------------------------------------------------------------

app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();


app.Run();
