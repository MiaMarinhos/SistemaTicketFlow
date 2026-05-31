using System;
using System.Collections.Generic;
using System.Text;
using TicketFlowModel.Ubicacion;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace TicketFlowModel.Usuario
{
    public abstract class Usuario
    {
        public int idUsuario { get; set; }
        public string dni {  get; set; }
        public string nombre { get; set; }
        public string apellidoPaterno { get; set; }
        public string apellidoMaterno { get; set; }
        public string telefono { get; set; }
        public int edad {  get; set; }
        public Genero genero { get; set; }
        public string correoElectronico { get; set; }
        public string contrasena { get; set; }
        public Date fechaRegistro { get; set; }
        public Date fechaNacimiento { get; set; }
        public EstadoUsuario estado { get; set; }
        public Distrito distrito { get; set; }
        public TipoUsuario tipo { get; set; }

        public Usuario() { }

        public Usuario(int idUsuario,string dni,string nombre,string apellidoPaterno,string apellidoMaterno ,string telefono,
            int edad, string correoElectronico,string contrasena,Date fechaRegistro,Date fechaNacimiento,EstadoUsuario estado,
            Distrito distrito, TipoUsuario tipo)
        {
            this.idUsuario = idUsuario;
            this.dni = dni;
            this.nombre = nombre;
            this.apellidoPaterno = apellidoPaterno;
            this.apellidoMaterno = apellidoMaterno;
            this.telefono = telefono;
            this.correoElectronico = correoElectronico;
            this.contrasena = contrasena;
            this.fechaRegistro = fechaRegistro;
            this.fechaNacimiento = fechaNacimiento;
        }
        public Usuario(int idUsuario, string dni, string correoElectronico, string contrasena,
            string nombre, string apellidoPaterno, string apellidoMaterno, TipoUsuario tipo)
        {
            this.idUsuario = idUsuario;
            this.dni = dni;
            this.nombre = nombre;
            this.apellidoPaterno = apellidoPaterno;
            this.apellidoMaterno = apellidoMaterno;
            this.correoElectronico = correoElectronico;
            this.contrasena = contrasena;
        }

    }
}
