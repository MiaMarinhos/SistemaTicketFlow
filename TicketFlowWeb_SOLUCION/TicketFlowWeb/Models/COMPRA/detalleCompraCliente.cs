using System;
using System.Text.Json.Serialization;

namespace TicketFlowWeb.Models.COMPRA
{
    public class detalleCompraCliente
    {
        public int idCompra { get; set; }
        public int entradasCompradas { get; set; }
        public int idCliente { get; set; }
        public int idEvento { get; set; }
        public int idEstado { get; set; }
        public int idpuntoBonus { get; set; }
        public string metodoPago { get; set; } = string.Empty;
        public double montoParcial { get; set; }
        public double montoTotal { get; set; }
        public string fechaCompraS { get; set; } = string.Empty;
        public string horaCompraS { get; set; } = string.Empty;

        // 🌟 Manejo Seguro de Fechas/Horas de la Compra para evitar caídas de tipos
        [JsonPropertyName("fechaCompra")]
        public string? fechaCompraRaw { get; set; }

        [JsonIgnore]
        public DateOnly? fechaCompra => DateOnly.TryParse(fechaCompraRaw, out var d) ? d : null;

        [JsonPropertyName("horaCompra")]
        public string? horaCompraRaw { get; set; }

        [JsonIgnore]
        public TimeOnly? horaCompra => TimeOnly.TryParse(horaCompraRaw?.Split('.')[0], out var t) ? t : null;

        // 🌟 Objetos anidados mapeados al 100%
        public DetalleClienteCompra? cliente { get; set; }
        public DetalleEstadoCompra? estado { get; set; }
        public DetalleCompraEvento? evento { get; set; }
    }

    public class DetalleClienteCompra
    {
        public int idUsuario { get; set; }
        public string nombre { get; set; } = string.Empty;
        public string apellidoPaterno { get; set; } = string.Empty;
        public string apellidoMaterno { get; set; } = string.Empty;
        public string correoElectronico { get; set; } = string.Empty;
        public string contrasena { get; set; } = string.Empty;
        public string dni { get; set; } = string.Empty;
        public int edad { get; set; }
        public int idDistrito { get; set; }
        public string telefono { get; set; } = string.Empty;
        public int puntosBonus { get; set; }
        public string? fechaNacimiento { get; set; }
        public string? fechaRegistration { get; set; }
        public DetalleGeneroCompra? genero { get; set; }
    }

    public class DetalleGeneroCompra
    {
        public int idGenero { get; set; }
    }

    public class DetalleEstadoCompra
    {
        public int idEstadoCompra { get; set; }
        public string estado { get; set; } = string.Empty;
    }

    public class DetalleCompraEvento
    {
        public int idEvento { get; set; }
        public string titulo { get; set; } = string.Empty;
        public string descripcion { get; set; } = string.Empty;
        public int capacidad_entradas { get; set; }
        public double precio { get; set; }
        public string img { get; set; } = string.Empty;
        public string nombre_establecimiento { get; set; } = string.Empty;
        public string ubicacion { get; set; } = string.Empty;
        public int idAnfitrion { get; set; }
        public bool activo { get; set; }
        public int FK_idCategoria_evento { get; set; }
        public int FK_idDistrito { get; set; }
        public int FK_idEstadoEvento { get; set; }
        public int FK_idEstadoPublicacion { get; set; }

        public string? fecha { get; set; } = string.Empty;

        [JsonIgnore]
        public DateOnly? fechaModerna => DateOnly.TryParse(fecha, out var d) ? d : null;

        public string hora_inicio { get; set; } = string.Empty;
        public string hora_fin { get; set; } = string.Empty;

        // Objetos complejos internos del Evento
        public DetalleAnfitrionCompra? anfitrion { get; set; }
        public DetalleCategoriaCompra? categoria { get; set; }
        public DetalleDistritoCompra? distrito { get; set; }
    }

    public class DetalleAnfitrionCompra
    {
        public int idUsuario { get; set; }
        public string nombre { get; set; } = string.Empty;
        public string razonSocial { get; set; } = string.Empty;
        public int edad { get; set; }
        public int idDistrito { get; set; }
    }

    public class DetalleCategoriaCompra
    {
        public int idCategoria_evento { get; set; }
        public string nombre { get; set; } = string.Empty;
        public int dias_para_publicacion { get; set; }
    }

    public class DetalleDistritoCompra
    {
        public int idDistrito { get; set; }
        public string nombre { get; set; } = string.Empty;
        public DetalleRegionCompra? region { get; set; }
    }

    public class DetalleRegionCompra
    {
        public int idRegion { get; set; }
        public string nombre { get; set; } = string.Empty;
    }
}