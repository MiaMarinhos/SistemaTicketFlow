using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.JSInterop;
using QuestPDF.Fluent;
using QuestPDF.Helpers;
using QuestPDF.Infrastructure;
using QRCoder;
using System.IO;
using TicketFlowWeb.Models.COMPRA; 
using TicketFlowWeb.Services.CompraRS;

namespace TicketFlowWeb.Components.Pages.Cliente
{
    public partial class MisEntradas
    {
        [Inject]
        public AuthenticationStateProvider AuthenticationStateProvider { get; set; } = default!;

        [Inject]
        public NavigationManager Navigation { get; set; } = default!;

        [Inject]
        public CompraRestService CompraService { get; set; } = default!;

        [Inject]
        public IJSRuntime JS { get; set; } = default!;

        // Variables de estado
        private List<detalleCompraCliente>? compras;
        private string? mensajeError;

        protected override async Task OnInitializedAsync()
        {
            try
            {
                var authState = await AuthenticationStateProvider.GetAuthenticationStateAsync();
                var usuario = authState.User;

                if (usuario.Identity?.IsAuthenticated != true)
                {
                    Navigation.NavigateTo("/login", forceLoad: true);
                    return;
                }

                // Buscamos el ID del usuario en los Claims
                var idTexto = usuario.FindFirst(ClaimTypes.NameIdentifier)?.Value
                               ?? usuario.FindFirst("IdUsuario")?.Value;

                if (!int.TryParse(idTexto, out var idUsuario))
                {
                    mensajeError = "No se encontró el ID del usuario en la sesión. Vuelve a iniciar sesión.";
                    return;
                }

                // Consumo efectivo del servicio
                compras = await CompraService.ListarComprasPorClienteAsync(idUsuario);
            }
            catch (Exception ex)
            {
                mensajeError = $"Error cargando las compras: {ex.Message}";
            }
        }

        //-------------pa ver la entrada en pdf-----------------
        public async Task ExportarEntradaPdf(int idCompra)
        {
            QuestPDF.Settings.License = LicenseType.Community;

            if (compras == null)
            {
                await JS.InvokeVoidAsync("alert", "⚠️ La lista de compras no está cargada.");
                return;
            }

            var entrada = compras.FirstOrDefault(c => c.idCompra == idCompra);

            if (entrada == null || entrada.evento == null)
            {
                await JS.InvokeVoidAsync("alert", "⚠️ No se encontraron los detalles locales para esta entrada.");
                return;
            }

            string tituloEvento = entrada.evento.titulo ?? "Evento Sin Nombre";
            string idTicket = $"#{entrada.idCompra}";
            string cantidadEntradas = $"{entrada.entradasCompradas} Entrada(s)";
            string nombreCliente = entrada.cliente != null
                ? $"{entrada.cliente.nombre} {entrada.cliente.apellidoPaterno} {entrada.cliente.apellidoMaterno}"
                : "Asistente General";
            string dniCliente = entrada.cliente != null ? entrada.cliente.dni : "---";
            string fechaEvento = entrada.evento.fecha ?? "---";
            string horaEvento = $"{entrada.evento.hora_inicio} - {entrada.evento.hora_fin}";
            string establecimiento = entrada.evento.nombre_establecimiento ?? "---";
            string ubicacionDistrito = entrada.evento.distrito != null
                ? $" {entrada.evento.ubicacion}, {entrada.evento.distrito.nombre}, {entrada.evento.distrito.region?.nombre}"
                : "---";
            string nombreAnfitrion = entrada.evento.anfitrion.nombre?? "---";
            string razonAnfitrion = entrada.evento.anfitrion.razonSocial ?? "---";

            byte[] qrCodeBytes;
            try
            {
                
                string urlValidacion = $"http://localhost:8080/TicketFlow/api/CompraRS/validarIngreso/{entrada.idCompra}";

                using (QRCodeGenerator qrGenerator = new QRCodeGenerator())
                {
                    using (QRCodeData qrCodeData = qrGenerator.CreateQrCode(urlValidacion, QRCodeGenerator.ECCLevel.Q))
                    {
                        using (PngByteQRCode qrCode = new PngByteQRCode(qrCodeData))
                        {
                            qrCodeBytes = qrCode.GetGraphic(20);
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                await JS.InvokeVoidAsync("alert", $"⚠️ Error al procesar el código de barras QR: {ex.Message}");
                return;
            }

            byte[] pdf = Document.Create(container =>
            {
                container.Page(page =>
                {
                    page.Size(PageSizes.A4);
                    page.Margin(0);

                    // Cabecera elegante (Se queda igual)
                    page.Header().Background(Color.FromHex("#1e3c72")).Padding(25).AlignCenter().Column(col =>
                    {
                        col.Item().Text("TICKET DIGITAL DE INGRESO").FontSize(22).Bold().FontColor(Colors.White);
                        col.Item().PaddingTop(4).Text("TicketFlow - Acceso Oficial").FontSize(11).FontColor(Colors.Grey.Lighten2);
                    });

                    // 🌟 SOLUCIÓN DEFINITIVA: El fondo gris se aplica directamente al bloque de contenido
                    page.Content().Background(Colors.Grey.Lighten4).Padding(35).Column(col =>
                    {
                        col.Item().Text(tituloEvento).FontSize(20).Bold().FontColor(Color.FromHex("#1e3c72"));

                        col.Item().PaddingVertical(10).Height(2).Background(Colors.Grey.Lighten2);

                        // Nota de Seguridad
                        col.Item().PaddingBottom(25).Background(Color.FromHex("#fffbeb")).BorderLeft(4).BorderColor(Color.FromHex("#f59e0b")).Padding(15).Column(c =>
                        {
                            c.Item().Text("📌 Control de Seguridad en Puerta").FontSize(12).Bold().FontColor(Color.FromHex("#b45309"));
                            c.Item().PaddingTop(4).Text("Este pase es personal e intransferible. Al ser escaneado en los controles de acceso, el identificador único del sistema inhabilitará copias o descargas paralelas automáticamente.").FontSize(10.5f).FontColor(Color.FromHex("#78350f"));
                        });

                        // Tabla de datos
                        col.Item().Background(Colors.White).Border(1).BorderColor(Colors.Grey.Lighten2).Padding(20).Table(table =>
                        {
                            table.ColumnsDefinition(columns =>
                            {
                                columns.RelativeColumn();
                                columns.RelativeColumn();
                            });

                            table.Cell().PaddingVertical(10).PaddingHorizontal(5).Column(c => { c.Item().Text("Código de Entrada (ID)").FontSize(10).FontColor(Colors.Grey.Medium).Bold(); c.Item().Text(idTicket).FontSize(13).Bold().FontColor(Colors.Grey.Darken3); });
                            table.Cell().PaddingVertical(10).PaddingHorizontal(5).Column(c => { c.Item().Text("Cantidad de Pases").FontSize(10).FontColor(Colors.Grey.Medium).Bold(); c.Item().Text(cantidadEntradas).FontSize(13).FontColor(Colors.Grey.Darken3); });

                            table.Cell().PaddingVertical(10).PaddingHorizontal(5).Column(c => { c.Item().Text("Titular del Ticket").FontSize(10).FontColor(Colors.Grey.Medium).Bold(); c.Item().Text(nombreCliente).FontSize(13).FontColor(Colors.Grey.Darken3); });
                            table.Cell().PaddingVertical(10).PaddingHorizontal(5).Column(c => { c.Item().Text("Documento (DNI)").FontSize(10).FontColor(Colors.Grey.Medium).Bold(); c.Item().Text(dniCliente).FontSize(13).FontColor(Colors.Grey.Darken3); });

                            table.Cell().PaddingVertical(10).PaddingHorizontal(5).Column(c => { c.Item().Text("Fecha del Evento").FontSize(10).FontColor(Colors.Grey.Medium).Bold(); c.Item().Text(fechaEvento).FontSize(13).FontColor(Colors.Grey.Darken3); });
                            table.Cell().PaddingVertical(10).PaddingHorizontal(5).Column(c => { c.Item().Text("Horario de Apertura").FontSize(10).FontColor(Colors.Grey.Medium).Bold(); c.Item().Text(horaEvento).FontSize(13).FontColor(Colors.Grey.Darken3); });

                            table.Cell().PaddingVertical(10).PaddingHorizontal(5).Column(c => { c.Item().Text("Establecimiento").FontSize(10).FontColor(Colors.Grey.Medium).Bold(); c.Item().Text(establecimiento).FontSize(13).FontColor(Colors.Grey.Darken3); });
                            table.Cell().PaddingVertical(10).PaddingHorizontal(5).Column(c => { c.Item().Text("Ciudad / Ubicación").FontSize(10).FontColor(Colors.Grey.Medium).Bold(); c.Item().Text(ubicacionDistrito).FontSize(13).FontColor(Colors.Grey.Darken3); });

                            table.Cell().PaddingVertical(10).PaddingHorizontal(5).Column(c => { c.Item().Text("Nombre del Anfitrion").FontSize(10).FontColor(Colors.Grey.Medium).Bold(); c.Item().Text(nombreAnfitrion).FontSize(13).FontColor(Colors.Grey.Darken3); });
                            table.Cell().PaddingVertical(10).PaddingHorizontal(5).Column(c => { c.Item().Text("Razon social").FontSize(10).FontColor(Colors.Grey.Medium).Bold(); c.Item().Text(razonAnfitrion).FontSize(13).FontColor(Colors.Grey.Darken3); });
                        });

                        col.Item().PaddingTop(30).Height(1).Background(Colors.Grey.Lighten2);

                        col.Item().AlignCenter().Column(qrCol =>
                        {
                            
                            qrCol.Item().PaddingTop(20).Width(140).Height(140)
                                  .Image(qrCodeBytes); 

                            //qrCol.Item().PaddingTop(12).Text("Pase de Control Único Electrónico").FontSize(11).Bold().FontColor(Color.FromHex("#1e3c72"));
                        });
                    });

                    page.Footer().Background(Colors.Grey.Lighten3).Padding(15).AlignCenter().Text("Plataforma de compras TicketFlow Web Corp. 2026.").FontSize(10).FontColor(Colors.Grey.Medium);
                });
            }).GeneratePdf();

            // 5. INVOCACIÓN DE DESCARGA DIRECTA
            await JS.InvokeVoidAsync(
                "descargarArchivo",
                $"Ticket_{idTicket}.pdf",
                "application/pdf",
                Convert.ToBase64String(pdf)
            );
        }
    }
}