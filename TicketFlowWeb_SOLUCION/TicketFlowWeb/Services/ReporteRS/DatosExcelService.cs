using Microsoft.JSInterop;

namespace TicketFlow.Services.ReporteRS
{
    public class DatosExcelService
    {
        private readonly HttpClient _httpClient;
        private readonly IJSRuntime _jsRuntime;
        private const string BaseUrl = "http://localhost:8080/TicketFlow/api/DatosExcelRS/";

        // Tipo MIME del formato .xlsx
        private const string XlsxType =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        public DatosExcelService(HttpClient httpClient, IJSRuntime jsRuntime)
        {
            _httpClient = httpClient;
            _jsRuntime = jsRuntime;
        }

        public async Task DescargarExcelFidelizacionAsync()
        {
            var response = await _httpClient.GetAsync($"{BaseUrl}fidelizacion/excel");

            if (!response.IsSuccessStatusCode)
            {
                var mensaje = await response.Content.ReadAsStringAsync();
                throw new Exception($"Error al generar excel: {mensaje}");
            }

            var bytes = await response.Content.ReadAsByteArrayAsync();
            var base64 = Convert.ToBase64String(bytes);

            await _jsRuntime.InvokeVoidAsync(
                "descargarArchivo",
                "datos_fidelizacion.xlsx",  // nombreArchivo
                XlsxType,                   // tipoContenido
                base64);                    // base64
        }

        public async Task DescargarExcelOcupacionAsync()
        {
            var response = await _httpClient.GetAsync($"{BaseUrl}ocupacion/excel");

            if (!response.IsSuccessStatusCode)
            {
                var mensaje = await response.Content.ReadAsStringAsync();
                throw new Exception($"Error al generar excel: {mensaje}");
            }

            var bytes = await response.Content.ReadAsByteArrayAsync();
            var base64 = Convert.ToBase64String(bytes);

            await _jsRuntime.InvokeVoidAsync(
                "descargarArchivo",
                "datos_ocupacion.xlsx",  // nombreArchivo
                XlsxType,                // tipoContenido
                base64);                 // base64
        }

        public async Task DescargarExcelDeVentasFiltrado(DateTime fechaInicio, DateTime fechaFin)
        {
            var url = $"{BaseUrl}ventas/excel" +
                      $"?fechaInicio={fechaInicio:yyyy-MM-dd}" +
                      $"&fechaFin={fechaFin:yyyy-MM-dd}";

            var response = await _httpClient.GetAsync(url);

            if (!response.IsSuccessStatusCode)
            {
                var mensaje = await response.Content.ReadAsStringAsync();
                throw new Exception($"Error al generar excel: {mensaje}");
            }

            var bytes = await response.Content.ReadAsByteArrayAsync();
            var base64 = Convert.ToBase64String(bytes);

            await _jsRuntime.InvokeVoidAsync(
                "descargarArchivo",
                "datos_ventas.xlsx",
                XlsxType,
                base64);
        }
    }
}