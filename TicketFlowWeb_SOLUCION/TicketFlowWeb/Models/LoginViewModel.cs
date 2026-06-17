using System.ComponentModel.DataAnnotations;


namespace TicketFlowWeb.Models
{
    public class LoginViewModel
    {
        public string Usuario { get; set; } = string.Empty;

        public string Password { get; set; } = string.Empty;

        public string Rol { get; set; } = string.Empty;
    }
}
