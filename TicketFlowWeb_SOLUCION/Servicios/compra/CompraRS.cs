using System;
using System.Collections.Generic;
using System.Net.Http.Json;
using System.Text;

using Models.compra;

namespace Servicios.compra
{
    public class CompraRS
    {
        private readonly HttpClient _http;
        private readonly string _baseUrl = "CompraRS";

        public CompraRS(HttpClient http)
        {
            _http = http;
        }

        public async Task<Compra> crearUsuario(Compra compra)
        {
            var response = await _http.PostAsJsonAsync($"{_baseUrl}/crear", compra);
            if (response.IsSuccessStatusCode)
            {
                return compra; // Éxito
            }
            return null; // Fallo
        }
    }
}
