using Microsoft.AspNetCore.Components;
using TicketFlowWeb.Models;
using TicketFlowWeb.Services.UsuarioRS;

namespace TicketFlowWeb.Components.Pages.Auth
{
    public partial class Register
    {
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
                    // 🛠️ Construimos el objeto Distrito con los datos REALES de la base de datos
                    Cliente.distrito = new Distrito
                    {
                        idDistrito = distritoSeleccionado.idDistrito,
                        nombre = distritoSeleccionado.nombre // <-- ¡Aquí viaja el nombre dinámico correcto!
                    };
                }

                // 🛠️ 2. Construimos el objeto Género (Este sí puede ser estático)
                Cliente.genero = new Genero
                {
                    idGenero = Cliente.idGeneroForm,
                    nombre = Cliente.idGeneroForm == 1 ? "MASCULINO" : "FEMENINO"
                };

                await usuarioService.RegistrarAsyncCliente(Cliente);
                Navigation.NavigateTo("/");
            }
            catch (Exception ex)
            {
                Error = "Error al registrar cliente: " + ex.Message;
            }
        }

        public void Cancelar()
        {
            Navigation.NavigateTo("/");
        }

        //------------caso distritos----------
        public List<Distrito> ListaDistritos { get; set; } = new();
        public string FechaMinima { get; set; } = "1940-01-01";
        public string FechaMaxima { get; set; } = DateTime.Now.ToString("yyyy-MM-dd");

        protected override async Task OnInitializedAsync()
        {
            // 1. Traemos los distritos de Java
            ListaDistritos = await usuarioService.ObtenerDistritosAsync();

            // 🌟 CORRECCIÓN: Inicializamos la fecha en el año 2000 para evitar el 0001
            Cliente.fechaNacimiento = new DateOnly(2000, 1, 1);

            // Configuración de límites que ya tenías
            FechaMinima = "1940-01-01";
            FechaMaxima = DateTime.Now.ToString("yyyy-MM-dd");
        }

        //---------------------caso regiones-------------------------

        public string RegionSeleccionada { get; set; } = string.Empty;

        // 🟢 Este método se ejecuta en tiempo real cuando el usuario cambia de opción en el combo
        public void ActualizarRegion(ChangeEventArgs e)
        {
            // 1. Capturamos el ID del distrito que el usuario acaba de seleccionar
            if (int.TryParse(e.Value?.ToString(), out int idSeleccionado))
            {
                Cliente.idDistritoForm = idSeleccionado;

                // 2. Buscamos el distrito completo dentro de la lista que trajo Java
                var distritoEncontrado = ListaDistritos.FirstOrDefault(d => d.idDistrito == idSeleccionado);

                if (distritoEncontrado != null && distritoEncontrado.region != null)
                {
                    // 3. Extraemos el nombre de la región anidada ("Lima")
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
