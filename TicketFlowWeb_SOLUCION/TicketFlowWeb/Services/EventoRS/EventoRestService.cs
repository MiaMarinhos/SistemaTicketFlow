using System.Net.Http.Headers;
using System.Text.Json;
using TicketFlowWeb.Models;
using TicketFlowWeb.Models.DTO;

namespace TicketFlowWeb.Services.EventoRS
{
    public class EventoRestService
    {
        private readonly HttpClient _httpClient;

        private static readonly JsonSerializerOptions JsonOptions = new()
        {
            PropertyNameCaseInsensitive = true
        };

        public EventoRestService(HttpClient http)
        {
            _httpClient = http;

            _httpClient.DefaultRequestHeaders.Accept.Clear();
            _httpClient.DefaultRequestHeaders.Accept.Add(
                new MediaTypeWithQualityHeaderValue("application/json"));
        }

        public async Task<List<EventoDTO>> ListarEventosAsync()
        {
            try
            {
                var lista = await _httpClient.GetFromJsonAsync<List<EventoDTO>>(
                    "EventoRS",
                    JsonOptions
                );

                return lista ?? new List<EventoDTO>();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error obteniendo eventos: {ex.Message}");
                return new List<EventoDTO>();
            }
        }
        public async Task<EventoDTO> BuscarEventoAsync(int id)
        {
            try
            {
                var evento = await _httpClient.GetFromJsonAsync<EventoDTO>(
                    $"EventoRS/{id}",
                    JsonOptions
                );

                return evento ?? new EventoDTO();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error obteniendo evento: {ex.Message}");
                return new EventoDTO();
            }


        }
    }
}