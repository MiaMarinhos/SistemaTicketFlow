using MySql.Data.MySqlClient;
using System.Net;
using TicketFlowDBManager;
using TicketFlowModel.Usuario;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace TicketFlowDAO
{
    public class UsuarioDAOImpl : IUsuarioDAO
    {
        public Usuario ObtenerPorUsuario(int id, string rol)
        {
            Usuario? usuario = null;

            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            conexion.Open();

            string sql = @"
                SELECT 
                    u.idUsuario,
                    u.dni,
                    u.correo_electronico,
                    u.contrasena,
                    u.nombre,
                    u.apellido_paterno,
                    u.apellido_materno,
                    t.idTipo_usuario,
                    t.nombre AS nombre_rol
                FROM usuario u, tipo_usuario t 
                WHERE u.idUsuario = @id AND t.nombre = @rol;";

            using MySqlCommand comando = new MySqlCommand(sql, conexion);
            comando.Parameters.AddWithValue("@id", id);
            comando.Parameters.AddWithValue("@rol", rol);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {

                TipoUsuario tipo = new TipoUsuario();

                tipo.idTipoUsuario = reader.GetInt32("idTipo_usuario");
                tipo.tipoUsuario = reader.GetString("nombre_rol");

                if (tipo.tipoUsuario == "CLIENTE")
                {
                    usuario = new Cliente();
                }
                else if (tipo.tipoUsuario == "ANFITRION")
                {
                    usuario = new Anfitrion();
                }
                else usuario = new Admin();

                mapearUsuario(reader, usuario, tipo);

            }

            return usuario;
        }

        private void mapearUsuario(MySqlDataReader reader, Usuario user, TipoUsuario tipo)
        {
            user.idUsuario = reader.GetInt32("idUsuario");
            user.dni = reader.GetString("dni");
            user.correoElectronico = reader.GetString("correo_electronico");
            user.contrasena = reader.GetString("contrasena");
            user.nombre = reader.GetString("nombre");
            user.apellidoPaterno = reader.GetString("apellido_paterno");
            user.apellidoMaterno = reader.GetString("apellido_materno");
            user.tipo = tipo;
        }

        public int ComprobarTipoUsuario(string correo, string rol)
        {
            int idUsuario = -1;
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            conexion.Open();

            string sql = @"
                SELECT 
                    u.idUsuario
                FROM usuario u, usuario_x_tipo x, tipo_usuario t 
                WHERE u.correo_electronico = @correo AND t.nombre = @rol
                    AND u.idUsuario = x.idUsuario AND t.idTipo_usuario = x.idTipo_usuario;";

            using MySqlCommand comando = new MySqlCommand(sql, conexion);
            comando.Parameters.AddWithValue("@correo", correo);
            comando.Parameters.AddWithValue("@rol", rol);
            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                idUsuario = reader.GetInt32("idUsuario");
            }

            return idUsuario;
        }
    }
}