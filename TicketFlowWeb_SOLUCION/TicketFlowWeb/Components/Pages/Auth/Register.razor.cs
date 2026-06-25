using Microsoft.AspNetCore.Components;
using Microsoft.JSInterop;
using TicketFlowWeb.Models;
using TicketFlowWeb.Services.UsuarioRS;

namespace TicketFlowWeb.Components.Pages.Auth
{
    public partial class Register
    {
        [Inject]
        public IJSRuntime JS { get; set; } = default!;

        [Inject]
        public UsuarioRestService usuarioService { get; set; }

        [Inject]
        public NavigationManager Navigation { get; set; } = default!;

        public ClienteViewModel Cliente{ get; set; } = new();

        public string Error { get; set; } = "";

        public async Task Registrar()
        {

            try
            {
                var distritoSeleccionado = ListaDistritos.FirstOrDefault(d => d.idDistrito == Cliente.idDistritoForm);

                if (distritoSeleccionado != null)
                {
                    Cliente.distrito = new Distrito
                    {
                        idDistrito = distritoSeleccionado.idDistrito,
                        nombre = distritoSeleccionado.nombre 
                    };
                }

                Cliente.genero = new Genero
                {
                    idGenero = Cliente.idGeneroForm,
                    nombre = Cliente.idGeneroForm == 1 ? "MASCULINO" : "FEMENINO"
                };

                await usuarioService.RegistrarAsyncCliente(Cliente);
                await JS.InvokeVoidAsync("alert", $"¡Registro exitoso, {Cliente.nombre}!\nTu cuenta ha sido creada correctamente.");
                Navigation.NavigateTo("/");
            }
            catch (Exception ex)
            {
                Error = ex.Message + "Elige otra por favor ";
            }
        }

        public void Cancelar()
        {
            Navigation.NavigateTo("/");
        }

        //------------caso distritos----------
        public List<Distrito> ListaDistritos { get; set; } = new();
        public string FechaMinima { get; set; } = "1926-01-01";
        public string FechaMaxima { get; set; } = DateTime.Now.ToString("yyyy-MM-dd");

        protected override async Task OnInitializedAsync()
        {
            ListaDistritos = await usuarioService.ObtenerDistritosAsync();

            Cliente.fechaNacimiento = new DateOnly(1926, 1, 1);

            FechaMinima = "1926-01-01";
            FechaMaxima = DateTime.Now.ToString("yyyy-MM-dd");
        }
        //---------------------caso edades---------------------------
        public int EdadCalculada
        {
            get
            {
                var hoy = DateOnly.FromDateTime(DateTime.Now);

                int edad = hoy.Year - Cliente.fechaNacimiento.Year;

                if (hoy < Cliente.fechaNacimiento.AddYears(edad))
                {
                    edad--;
                }

                Cliente.edad = edad;

                return edad;
            }
        }
        //---------------------caso regiones-------------------------

        public string RegionSeleccionada { get; set; } = string.Empty;

        public void ActualizarRegion(ChangeEventArgs e)
        {
            if (int.TryParse(e.Value?.ToString(), out int idSeleccionado))
            {
                Cliente.idDistritoForm = idSeleccionado;

                var distritoEncontrado = ListaDistritos.FirstOrDefault(d => d.idDistrito == idSeleccionado);

                if (distritoEncontrado != null && distritoEncontrado.region != null)
                {
                    RegionSeleccionada = distritoEncontrado.region.nombre;
                }
                else
                {
                    RegionSeleccionada = string.Empty;
                }
            }
        }


    }
}
