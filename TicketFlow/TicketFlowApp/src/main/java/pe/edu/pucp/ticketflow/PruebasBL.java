package pe.edu.pucp.ticketflow;


import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.impl.ClienteBLImpl;
import pe.edu.pucp.ticketflow.impl.EventoBLImpl;
import pe.edu.pucp.ticketflow.impl.EventoDAOImpl;
import pe.edu.pucp.ticketflow.impl.UsuarioDAOImpl;
import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;
import pe.edu.pucp.ticketflow.usuario.model.Cliente;
import pe.edu.pucp.ticketflow.usuario.model.EstadoUsuario;
import pe.edu.pucp.ticketflow.usuario.model.TipoUsuario;

import java.awt.*;
import java.sql.Date;
import java.util.List;
public class PruebasBL {
    static void main(){
        try {
            ClienteBLImpl clienteBL = new ClienteBLImpl();

            EstadoUsuario estado = new EstadoUsuario();
            estado.setIdEstadoUsuario(1); // ACTIVO

            TipoUsuario tipo = new TipoUsuario();
            tipo.setIdTipoUsuario(1); // CLIENTE

            Distrito distrito = new Distrito();
            distrito.setIdDistrito(1); // San Miguel

            Cliente cliente = new Cliente();

            System.out.println("=== BUSCANDO CLIENTE DESDE BL ===");
            Cliente encontrado = clienteBL.buscarClientePorId(1);
            System.out.println("ID: " + encontrado.getIdUsuario());
            System.out.println("Nombre: " + encontrado.getNombre());
            System.out.println("Correo: " + encontrado.getCorreoElectronico());
            System.out.println("Puntos: " + encontrado.getPuntosBonus());

            IEventoBL eventoBL= new EventoBLImpl();

            List<Evento> lista = eventoBL.verTodosLosEventos();

            for(Evento e : lista){
                System.out.println(
                        "ID: " + e.getIdEvento() +
                                ", Titulo: " + e.getTitulo() +
                                ", Precio: " + e.getPrecio() +
                                ", Fecha: " + e.getFecha()
                );
            }

           /* UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();
            ClienteBLImpl clienteBL = new ClienteBLImpl();

            EstadoUsuario estado = new EstadoUsuario();
            estado.setIdEstadoUsuario(1); // ACTIVO

            TipoUsuario tipo = new TipoUsuario();
            tipo.setIdTipoUsuario(1); // CLIENTE

            Distrito distrito = new Distrito();
            distrito.setIdDistrito(1); // San Miguel

            Cliente cliente = new Cliente();
            cliente.setDni("88888888");
            cliente.setNombre("Cliente");
            cliente.setApellidoPaterno("Prueba");
            cliente.setApellidoMaterno("BL");
            cliente.setTelefono("977777777");
            cliente.setCorreoElectronico("cliente.bl@test.com");
            cliente.setContrasena("123456");
            cliente.setFechaRegistro(new Date(System.currentTimeMillis()));
            cliente.setFechaNacimiento(Date.valueOf("2004-01-01"));
            cliente.setEstado(estado);
            cliente.setTipo(tipo);
            cliente.setDistrito(distrito);
            cliente.setIdDistrito(1);
            cliente.setPuntosBonus(0);

            System.out.println("=== CREANDO USUARIO PARA CLIENTE ===");
            usuarioDAO.create(cliente);
            System.out.println("Usuario creado con ID: " + cliente.getIdUsuario());

            System.out.println("=== REGISTRANDO CLIENTE DESDE BL ===");
            clienteBL.registrarCliente(cliente);
            System.out.println("Cliente registrado correctamente.");

            System.out.println("=== BUSCANDO CLIENTE DESDE BL ===");
            Cliente encontrado = clienteBL.buscarClientePorId(cliente.getIdUsuario());
            System.out.println("ID: " + encontrado.getIdUsuario());
            System.out.println("Nombre: " + encontrado.getNombre());
            System.out.println("Correo: " + encontrado.getCorreoElectronico());
            System.out.println("Puntos: " + encontrado.getPuntosBonus());

            System.out.println("=== ACTUALIZANDO CLIENTE DESDE BL ===");
            encontrado.setNombre("Cliente BL Actualizado");
            encontrado.setTelefono("966666666");
            encontrado.setPuntosBonus(100);

            clienteBL.actualizarCliente(encontrado, encontrado.getIdUsuario());

            Cliente actualizado = clienteBL.buscarClientePorId(encontrado.getIdUsuario());
            System.out.println("Nombre actualizado: " + actualizado.getNombre());
            System.out.println("Telefono actualizado: " + actualizado.getTelefono());
            System.out.println("Puntos actualizados: " + actualizado.getPuntosBonus());

            System.out.println("=== LISTANDO CLIENTES DESDE BL ===");
            List<Cliente> clientes = clienteBL.listarClientes();

            for (Cliente c : clientes) {
                System.out.println(
                        c.getIdUsuario() + " - " +
                                c.getNombre() + " - " +
                                c.getCorreoElectronico() + " - " +
                                c.getPuntosBonus() + " puntos"
                );
            }

            System.out.println("=== ACCIONES CONCEPTUALES DE CLIENTE ===");
            clienteBL.verPerfil();
            clienteBL.editarPerfil();
            clienteBL.comprarEntradas();
            clienteBL.pagarEntrada();
            clienteBL.descargarEntradas();
            clienteBL.solicitarSerAnfitrion();

            System.out.println("=== ELIMINANDO CLIENTE DESDE BL ===");
            clienteBL.eliminarCliente(cliente.getIdUsuario());
            System.out.println("Cliente eliminado correctamente.");

            System.out.println("=== PRUEBA BL CLIENTE TERMINADA ===");
*/
        } catch (BusinessLogicException e) {
            System.out.println("Error de lógica de negocio: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
