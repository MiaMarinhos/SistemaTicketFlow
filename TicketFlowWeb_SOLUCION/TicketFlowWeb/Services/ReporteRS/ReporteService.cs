using Microsoft.JSInterop;

namespace TicketFlow.Services.ReporteRS
{
    public class ReporteService
    {
        private readonly HttpClient _httpClient;
        private readonly IJSRuntime _jsRuntime;
        private const string BaseUrl = "http://34.202.176.10:8080/TicketFlow/api/ReporteRS/";

        public ReporteService(HttpClient httpClient, IJSRuntime jsRuntime)
        {
            _httpClient = httpClient;
            _jsRuntime = jsRuntime;
        }

        public async Task DescargarReporteFidelizacionAsync()
        {
            var response = await _httpClient.GetAsync($"{BaseUrl}fidelizacion/pdf");

            if (!response.IsSuccessStatusCode)
            {
                var mensaje = await response.Content.ReadAsStringAsync();
                throw new Exception($"Error al generar reporte: {mensaje}");
            }

            var bytes = await response.Content.ReadAsByteArrayAsync();
            var base64 = Convert.ToBase64String(bytes);

            await _jsRuntime.InvokeVoidAsync(
                "descargarArchivo",
                "reporte_fidelizacion.pdf",  // nombreArchivo
                "application/pdf",           // tipoContenido
                base64);                     // base64
        }

        public async Task DescargarReporteOcupacionAsync()
        {
            var response = await _httpClient.GetAsync($"{BaseUrl}ocupacion/pdf");

            if (!response.IsSuccessStatusCode)
            {
                var mensaje = await response.Content.ReadAsStringAsync();
                throw new Exception($"Error al generar reporte: {mensaje}");
            }

            var bytes = await response.Content.ReadAsByteArrayAsync();
            var base64 = Convert.ToBase64String(bytes);

            await _jsRuntime.InvokeVoidAsync(
                "descargarArchivo",
                "reporte_ocupacion.pdf",  // nombreArchivo
                "application/pdf",           // tipoContenido
                base64);                     // base64
        }

        public async Task DescargarReporteDeVentasFiltrado(DateTime fechaInicio, DateTime fechaFin)
        {
            var url = $"{BaseUrl}ventas/pdf" +
                      $"?fechaInicio={fechaInicio:yyyy-MM-dd}" +
                      $"&fechaFin={fechaFin:yyyy-MM-dd}";

            var response = await _httpClient.GetAsync(url);

            if (!response.IsSuccessStatusCode)
            {
                var mensaje = await response.Content.ReadAsStringAsync();
                throw new Exception($"Error al generar reporte: {mensaje}");
            }

            var bytes = await response.Content.ReadAsByteArrayAsync();
            var base64 = Convert.ToBase64String(bytes);

            await _jsRuntime.InvokeVoidAsync(
                "descargarArchivo",
                "reporte_ventas.pdf",
                "application/pdf",
                base64);
        }
    }
}
