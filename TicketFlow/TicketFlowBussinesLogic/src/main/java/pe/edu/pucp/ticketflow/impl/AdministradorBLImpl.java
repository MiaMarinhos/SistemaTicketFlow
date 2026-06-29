package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.*;
import pe.edu.pucp.ticketflow.Infrastructure.AsyncExecutor;
import pe.edu.pucp.ticketflow.Infrastructure.EmailService;
import pe.edu.pucp.ticketflow.administrador.model.Administrador;
import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.evento.model.categoria_evento;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.pago.model.Pago;
import pe.edu.pucp.ticketflow.solicitud.model.Solicitud;
import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;
import pe.edu.pucp.ticketflow.usuario.model.Cliente;
import pe.edu.pucp.ticketflow.usuario.model.EstadoUsuario;
import pe.edu.pucp.ticketflow.usuario.model.Genero;
import pe.edu.pucp.ticketflow.usuario.model.Usuario;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class AdministradorBLImpl implements IAdministradorBL {

    private final IAdministradorDAO administradorDAO;
    private final IUsuarioDAO usuarioDAO;
    private final IEventoDAO eventoDAO;
    private final ISolicitudesDAO solicitudesDAO;
    private final IPagosDAO pagosDAO;
    private final ICompraDAO compraDAO;
    private final IReporteDAO reportesDAO;
    private final IGeneroDAO generoDAO;
    private final IDistritoDAO distritoDAO;
    private final ICategoriaEventoDAO categoriaEventoDAO;
    private final IClienteDAO clienteDAO;
    private final EmailService emailService;

    public AdministradorBLImpl() {
        this.categoriaEventoDAO = new CategoriaEventoDAOImpl();
        this.generoDAO = new GeneroDAOImpl();
        this.distritoDAO = new DistritoDAOImpl();
        this.administradorDAO = new AdministradorDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
        this.eventoDAO = new EventoDAOImpl();
        this.solicitudesDAO = new SolicitudesDAOImpl();
        this.pagosDAO = new PagosDAOImpl();
        this.compraDAO = new CompraDAOImpl();
        this.reportesDAO = new ReportesDAOImpl();
        this.clienteDAO = new ClienteDAOImpl();
        this.emailService = new EmailService();
    }

    @Override
    public Administrador registrarAdministrador(Administrador administrador) throws BusinessLogicException {
        try {
            validarAdministrador(administrador);
            return administradorDAO.create(administrador);
        } catch (Exception ex) {
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException) ex;
            }
            throw new BusinessLogicException(ex);
        }
    }

    @Override
    public Administrador buscarAdministradorPorId(Integer id) throws BusinessLogicException {
        try {
            validarId(id);

            Administrador administrador = administradorDAO.read(id);

            if (administrador == null) {
                throw new BusinessLogicException("No existe un administrador con el ID indicado.");
            }

            return administrador;

        } catch (Exception ex) {
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException) ex;
            }
            throw new BusinessLogicException(ex);
        }
    }

    @Override
    public Administrador actualizarAdministrador(Administrador administrador, Integer id) throws BusinessLogicException {
        try {
            validarId(id);
            validarAdministrador(administrador);

            Administrador administradorExistente = administradorDAO.read(id);

            if (administradorExistente == null) {
                throw new BusinessLogicException("No se puede actualizar. El administrador no existe.");
            }

            return administradorDAO.update(administrador, id);

        } catch (Exception ex) {
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException) ex;
            }
            throw new BusinessLogicException(ex);
        }
    }

    @Override
    public void eliminarAdministrador(Integer id) throws BusinessLogicException {
        try {
            validarId(id);

            Administrador administradorExistente = administradorDAO.read(id);

            if (administradorExistente == null) {
                throw new BusinessLogicException("No se puede eliminar. El administrador no existe.");
            }

            administradorDAO.delete(id);

        } catch (Exception ex) {
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException) ex;
            }
            throw new BusinessLogicException(ex);
        }
    }

    @Override
    public List<Administrador> listarAdministradores() throws BusinessLogicException {
        try {
            return administradorDAO.listAll();
        } catch (Exception ex) {
            throw new BusinessLogicException(ex);
        }
    }
    //GESTION DE USUARIOS
    @Override
    public List<Usuario> listarUsuarios() throws BusinessLogicException {
        return usuarioDAO.listAll();
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer id) throws BusinessLogicException {
        if (id == null || id <= 0) {
            throw new BusinessLogicException("Debe seleccionar un usuario válido.");
        }

        Usuario usuario = usuarioDAO.read(id);

        if (usuario == null) {
            throw new BusinessLogicException("No existe un usuario con el ID indicado.");
        }

        return usuario;
    }

    @Override
    public List<Usuario> buscarUsuario(String nombre)  throws BusinessLogicException{

        if(nombre == null || nombre.trim().isEmpty()){
            return usuarioDAO.listAll();
        }

        return usuarioDAO.buscarPorNombre(nombre.trim());
    }

    @Override
    public List<Usuario> filtrarUsuariosPorTipo(Integer idTipoUsuario)  throws BusinessLogicException{

        if (idTipoUsuario == null || idTipoUsuario == 0) {
            return usuarioDAO.listAll();
        }

        return usuarioDAO.filtrarPorTipo(idTipoUsuario);
    }

    @Override
    public List<Usuario> filtrarUsuariosPorEstado(Integer idEstado)  throws BusinessLogicException{

        if (idEstado == null || idEstado == 0) {
            return usuarioDAO.listAll();
        }

        return usuarioDAO.filtrarPorEstado(idEstado);
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) throws BusinessLogicException {
        try {
            validarDatosUsuario(usuario);

            if (usuario.getGenero() == null || usuario.getGenero().getIdGenero() <= 0) {
                throw new BusinessLogicException("Debe seleccionar un género.");
            }

            if (usuario.getDistrito() == null || usuario.getDistrito().getIdDistrito() <= 0) {
                throw new BusinessLogicException("Debe seleccionar un distrito.");
            }

            if (usuario.getTipo() == null || usuario.getTipo().getIdTipoUsuario() <= 0) {
                throw new BusinessLogicException("Debe seleccionar un tipo de usuario.");
            }

            EstadoUsuario estado = new EstadoUsuario();
            estado.setIdEstadoUsuario(1); // ACTIVO
            usuario.setEstado(estado);

            usuario.setFechaRegistro(
                    java.sql.Date.valueOf(java.time.LocalDate.now())
            );

            return usuarioDAO.create(usuario);

        } catch (Exception ex) {
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException) ex;
            } else {
                throw new BusinessLogicException(ex);
            }
        }
    }

    @Override
    public Usuario editarUsuario(Usuario usuario) throws BusinessLogicException {

        validarDatosUsuario(usuario);

        if ( usuario.getIdUsuario() <= 0) {
            throw new RuntimeException("Debe seleccionar un usuario válido para editar.");
        }

        return usuarioDAO.update(usuario, usuario.getIdUsuario());
    }

    @Override
    public Usuario bloquearUsuario(Integer idUsuario) throws BusinessLogicException {

        if (idUsuario <= 0) {
            throw new RuntimeException("Debe seleccionar un usuario válido.");
        }

        return usuarioDAO.bloquearUsuario(idUsuario);
    }

    @Override
    public Usuario desbloquearUsuario(Integer idUsuario) throws BusinessLogicException {

        if (idUsuario <= 0) {
            throw new RuntimeException("Debe seleccionar un usuario válido.");
        }

        return usuarioDAO.desbloquearUsuario(idUsuario);
    }

    //LISTAR DISTRITOS Y GENEROS
    @Override
    public List<Genero> listarGeneros() throws BusinessLogicException {
        try {
            return generoDAO.listAll();
        } catch (Exception e) {
            throw new BusinessLogicException(e);
        }
    }

    @Override
    public List<Distrito> listarDistritos() throws BusinessLogicException {
        try {
            return distritoDAO.listAll();
        } catch (Exception e) {
            throw new BusinessLogicException(e);
        }
    }

    //GESTION DE EVENTOS
    @Override
    public List<Evento> listarEventos()  throws BusinessLogicException{
        return eventoDAO.listAll();
    }

    @Override
    public List<Evento> buscarEvento(String titulo) throws BusinessLogicException {

        if (titulo == null || titulo.trim().isEmpty()) {
            return eventoDAO.listAll();
        }

        return eventoDAO.buscarPorTitulo(titulo.trim());
    }

    @Override
    public List<Evento> filtrarEventosPorEstado(Integer idEstadoEvento) throws BusinessLogicException {

        if (idEstadoEvento == 0) {
            return eventoDAO.listAll();
        }

        return eventoDAO.filtrarPorEstado(idEstadoEvento);
    }

    @Override
    public Evento detalleEvento(Integer idEvento) throws BusinessLogicException {

        if ( idEvento <= 0) {
            throw new BusinessLogicException("Debe seleccionar un evento válido.");
        }

        Evento evento = eventoDAO.read(idEvento);

        if (evento == null) {
            throw new BusinessLogicException("No se encontró el evento seleccionado.");
        }

        return evento;
    }

    @Override
    public Evento registrarEvento(Evento evento) throws BusinessLogicException {

        validarDatosEvento(evento);

        return eventoDAO.create(evento);
    }

    @Override
    public Evento editarEvento(Evento evento) throws BusinessLogicException {

        validarDatosEvento(evento);

        if (evento.getIdEvento() <= 0) {
            throw new BusinessLogicException("Debe seleccionar un evento válido para editar.");
        }

        return eventoDAO.update(evento, evento.getIdEvento());
    }

    @Override
    public Evento aprobarEvento(Integer idEvento)throws BusinessLogicException {

        if (idEvento <= 0) {
            throw new BusinessLogicException("Debe seleccionar un evento válido.");
        }

        return eventoDAO.aprobarEvento(idEvento);
    }

    @Override
    public Evento rechazarEvento(Integer idEvento)throws BusinessLogicException {

        if (idEvento <= 0) {
            throw new BusinessLogicException("Debe seleccionar un evento válido.");
        }

        return eventoDAO.rechazarEvento(idEvento);
    }

    @Override
    public Evento eliminarEvento(Integer idEvento) throws BusinessLogicException {

        if (idEvento <= 0) {
            throw new BusinessLogicException("Debe seleccionar un evento válido.");
        }

        try{
            Evento evento=eventoDAO.eliminarEvento(idEvento);

            AsyncExecutor.ejecutar(()->{
                        List<Compra> compras = compraDAO.ListarComprasDeEvento(idEvento);
                        for(Compra compra : compras) {
                            try{
                                Cliente cliente = clienteDAO.read(compra.getIdCliente());
                                String htmlCancelacion = """
                                            <html>
                                            <body style="font-family: Arial; background:#f4f6f8; padding:20px;">
                                            
                                                <div style="max-width:600px; margin:auto; background:white; border-radius:10px; overflow:hidden;">
                                            
                                                    <div style="background:#e74c3c; padding:20px; text-align:center;">
                                                        <h2 style="color:white; margin:0;">❌ Evento Cancelado</h2>
                                                        <p style="color:#fceaea;">Lamentamos informarte esta noticia.</p>
                                                    </div>
                                            
                                                    <div style="padding:25px;">
                                            
                                                        <p>Hola <b>%s %s</b>,</p>
                                            
                                                        <p>
                                                            Te informamos que el siguiente evento ha sido
                                                            <b>cancelado por el organizador</b>.
                                                        </p>
                                            
                                                        <div style="background:#ecf0f1; padding:15px; border-radius:8px;">
                                            
                                                            <p><b>🎟 Evento:</b> %s</p>
                                                            <p><b>📅 Fecha del evento:</b> %s</p>
                                                            <p><b>🕒 Hora:</b> %s</p>
                                                            <p><b>📍 Lugar:</b> %s</p>
                                            
                                                        </div>
                                            
                                                        <p style="margin-top:20px; color:#555;">
                                                            Sabemos que esta situación puede resultar inconveniente y
                                                            lamentamos cualquier molestia ocasionada.
                                                        </p>
                                            
                                                        <p style="color:#555;">
                                                            Si realizaste una compra para este evento,
                                                            <b>el reembolso será procesado automáticamente</b>
                                                            utilizando el mismo método de pago empleado en la compra.
                                                        </p>
                                            
                                                        <p style="color:#555;">
                                                            Si tienes alguna consulta, nuestro equipo de soporte estará
                                                            encantado de ayudarte.
                                                        </p>
                                            
                                                    </div>
                                            
                                                    <div style="background:#f1f1f1; text-align:center; padding:10px;">
                                                        <small>© TicketFlow - Gracias por tu comprensión.</small>
                                                    </div>
                                            
                                                </div>
                                            
                                            </body>
                                            </html>
                                            """.formatted(
                                        cliente.getNombre(),
                                        cliente.getApellidoPaterno(),
                                        evento.getTitulo(),
                                        evento.getFecha(),
                                        evento.getHora_inicio(),
                                        evento.getNombre_establecimiento()
                                );

                                emailService.enviarCorreo(cliente.getCorreoElectronico(),"TICKETFLOW | AVISO: EVENTO CANCELADO",htmlCancelacion);
                            }
                            catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    }
            );

            return evento;

        }
        catch (Exception e){
            throw new BusinessLogicException("Error en eliminar Evento");
        }
    }

    @Override
    public List<categoria_evento> listarCategoriasEvento() throws BusinessLogicException {
        try {
            return categoriaEventoDAO.listAll();
        } catch (Exception e) {
            throw new BusinessLogicException(e);
        }
    }

    //GESTION DE SOLICITUDES
    @Override
    public List<Solicitud> listarSolicitudes() throws BusinessLogicException {
        return solicitudesDAO.listAll();
    }

    @Override
    public List<Solicitud> buscarSolicitud(String nombre) throws BusinessLogicException {

        if (nombre == null || nombre.trim().isEmpty()) {
            return solicitudesDAO.listAll();
        }

        return solicitudesDAO.buscarPorNombre(nombre.trim());
    }

    @Override
    public List<Solicitud> filtrarSolicitudesPorEstado(Integer idEstado) throws BusinessLogicException {

        if (idEstado == 0) {
            return solicitudesDAO.listAll();
        }

        return solicitudesDAO.filtrarPorEstado(idEstado);
    }

    @Override
    public Solicitud detalleSolicitud(Integer idSolicitud)
            throws BusinessLogicException {

        if (idSolicitud <= 0) {
            throw new BusinessLogicException("Debe seleccionar una solicitud válida.");
        }

        Solicitud solicitud = solicitudesDAO.read(idSolicitud);

        if (solicitud == null) {
            throw new BusinessLogicException("No se encontró la solicitud seleccionada.");
        }

        return solicitud;
    }

    @Override
    public Solicitud editarSolicitud(Solicitud solicitud) throws BusinessLogicException {

        validarDatosSolicitud(solicitud);

        if (solicitud.getIdSolicitudes() <= 0) {
            throw new BusinessLogicException(
                    "Debe seleccionar una solicitud válida para editar."
            );
        }

        return solicitudesDAO.update(
                solicitud,
                solicitud.getIdSolicitudes()
        );
    }

    @Override
    public Solicitud aprobarSolicitud(Integer idSolicitud) throws BusinessLogicException {

        if (idSolicitud == null || idSolicitud <= 0) {
            throw new BusinessLogicException("Debe seleccionar una solicitud válida.");
        }

        return solicitudesDAO.aprobarSolicitud(idSolicitud);
    }

    @Override
    public Solicitud rechazarSolicitud(Integer idSolicitud) throws BusinessLogicException {

        if (idSolicitud == null || idSolicitud <= 0) {
            throw new BusinessLogicException(
                    "Debe seleccionar una solicitud válida."
            );
        }

        return solicitudesDAO.rechazarSolicitud(idSolicitud);
    }

    @Override
    public void eliminarSolicitud(Integer idSolicitud) throws BusinessLogicException {

        if (idSolicitud == null || idSolicitud <= 0) {
            throw new BusinessLogicException(
                    "Debe seleccionar una solicitud válida."
            );
        }

        Solicitud solicitud = solicitudesDAO.read(idSolicitud);

        if (solicitud == null) {
            throw new BusinessLogicException(
                    "La solicitud no existe."
            );
        }

        solicitudesDAO.delete(idSolicitud);
    }

    //Gestion de Pagos
    @Override
    public List<Pago> listarPagos() throws BusinessLogicException {
        pagosDAO.generarPagosEventosFinalizados();
        return pagosDAO.listAllByAdmin();
    }

    @Override
    public List<Pago> buscarPago(String nombre) throws BusinessLogicException {

        if (nombre == null || nombre.trim().isEmpty()) {
            return pagosDAO.listAll();
        }

        return pagosDAO.buscarPorUsuario(nombre.trim());
    }

    @Override
    public List<Pago> filtrarPagosPorEstado(Integer idEstado) throws BusinessLogicException {

        if (idEstado == null || idEstado == 0) {
            return pagosDAO.listAllByAdmin();
        }

        return pagosDAO.filtrarPorEstado(idEstado);
    }

    @Override
    public List<Pago> filtrarPagosPorFecha(String fecha) throws BusinessLogicException {

        try {
            if (fecha == null || fecha.isBlank()) {
                return pagosDAO.listAllByAdmin();
            }

            LocalDate fechaConvertida = LocalDate.parse(fecha);

            return pagosDAO.filtrarPorFecha(fechaConvertida);

        } catch (Exception e) {
            throw new BusinessLogicException(
                    "Error al filtrar pagos por fecha: " + e.getMessage()
            );
        }
    }

    @Override
    public Pago detallePago(Integer idPago) throws BusinessLogicException {

        if (idPago == null || idPago <= 0) {
            throw new BusinessLogicException("Debe seleccionar un pago válido.");
        }

        Pago pago = pagosDAO.read(idPago);

        if (pago == null) {
            throw new BusinessLogicException("No se encontró el pago seleccionado.");
        }

        return pago;
    }

    @Override
    public Pago confirmarTransferenciaPago(Integer idPago, String comprobante) throws BusinessLogicException {

        if (idPago == null || idPago <= 0) {
            throw new BusinessLogicException("Debe seleccionar un pago válido.");
        }

        if (comprobante == null || comprobante.trim().isEmpty()) {
            throw new BusinessLogicException("Debe registrar el comprobante de la transferencia.");
        }

        String comprobanteLimpio = comprobante.trim();

        if (!comprobanteLimpio.toLowerCase().contains(".jpg")
                && !comprobanteLimpio.toLowerCase().contains(".jpeg")) {
            throw new BusinessLogicException("El comprobante debe ser una imagen JPG.");
        }

        Pago pago = pagosDAO.read(idPago);

        if (pago == null) {
            throw new BusinessLogicException("No se encontró el pago seleccionado.");
        }

        if (pago.getEstado() != null
                && pago.getEstado().getEstado() != null
                && pago.getEstado().getEstado().equalsIgnoreCase("PAGADO")) {
            throw new BusinessLogicException("Este pago ya fue confirmado.");
        }

        if (pago.getEvento() == null
                || pago.getEvento().getEstadoEvento() == null
                || pago.getEvento().getEstadoEvento().getEstado() == null) {
            throw new BusinessLogicException("No se pudo validar el estado del evento.");
        }

        String estadoEvento = pago.getEvento().getEstadoEvento().getEstado();

        if (!estadoEvento.equalsIgnoreCase("FINALIZADO")) {
            throw new BusinessLogicException("Solo se puede confirmar la transferencia de eventos finalizados.");
        }

        pagosDAO.confirmarTransferencia(idPago, comprobanteLimpio);

        return pagosDAO.read(idPago);
    }

    @Override
    public int generarPagosEventosFinalizados() throws BusinessLogicException {
        return pagosDAO.generarPagosEventosFinalizados();
    }

    //GESTION DE COMPRAS
    @Override
    public List<Compra> listarCompras() throws BusinessLogicException {
        return compraDAO.listAll();
    }

    @Override
    public List<Compra> buscarCompra(String nombre) throws BusinessLogicException {

        if (nombre == null || nombre.trim().isEmpty()) {
            return compraDAO.listAll();
        }

        return compraDAO.buscarPorUsuario(nombre.trim());
    }

    @Override
    public List<Compra> filtrarComprasPorEstado(Integer idEstado) throws BusinessLogicException {

        if (idEstado == null || idEstado == 0) {
            return compraDAO.listAll();
        }

        return compraDAO.filtrarPorEstado(idEstado);
    }

    @Override
    public Compra detalleCompra(Integer idCompra) throws BusinessLogicException {

        if (idCompra == null || idCompra <= 0) {
            throw new BusinessLogicException("Debe seleccionar una compra válida.");
        }

        Compra compra = compraDAO.read(idCompra);

        if (compra == null) {
            throw new BusinessLogicException("No se encontró la compra seleccionada.");
        }

        return compra;
    }

    //GESTION DE REPORTES
    @Override
    public List<Object[]> generarReporteVentas(Date fechaInicio, Date fechaFin, Integer idCategoria)
            throws BusinessLogicException {

        if (fechaInicio == null || fechaFin == null) {
            throw new BusinessLogicException("Debe seleccionar un rango de fechas.");
        }

        if (fechaInicio.after(fechaFin)) {
            throw new BusinessLogicException("La fecha de inicio no puede ser mayor que la fecha fin.");
        }

        if (idCategoria == null) {
            idCategoria = 0;
        }

        return reportesDAO.reporteVentas(fechaInicio, fechaFin, idCategoria);
    }

    @Override
    public List<Object[]> generarReporteFidelizacion()
            throws BusinessLogicException {

        return reportesDAO.reporteFidelizacion();
    }

    @Override
    public List<Object[]> generarReporteOcupacionEventos()
            throws BusinessLogicException {

        return reportesDAO.reporteOcupacionEventos();
    }



    private void validarAdministrador(Administrador administrador) throws BusinessLogicException {
        if (administrador == null) {
            throw new BusinessLogicException("El administrador no puede ser nulo.");
        }

//        if (administrador.getIdAdministrador() <= 0) {
//            throw new BusinessLogicException("El ID del administrador debe ser mayor a 0.");
//        }
//
//        if (administrador.getCodigo() == null || administrador.getCodigo().trim().isEmpty()) {
//            throw new BusinessLogicException("El código del administrador es obligatorio.");
//        }
//
//        if (administrador.getNombre() == null || administrador.getNombre().trim().isEmpty()) {
//            throw new BusinessLogicException("El nombre del administrador es obligatorio.");
//        }
//
//        if (administrador.getApellidoPaterno() == null || administrador.getApellidoPaterno().trim().isEmpty()) {
//            throw new BusinessLogicException("El apellido paterno del administrador es obligatorio.");
//        }
//
//        if (administrador.getApellidoMaterno() == null || administrador.getApellidoMaterno().trim().isEmpty()) {
//            throw new BusinessLogicException("El apellido materno del administrador es obligatorio.");
//        }
//
//        if (administrador.getDni() == null || administrador.getDni().trim().isEmpty()) {
//            throw new BusinessLogicException("El DNI del administrador es obligatorio.");
//        }
//
//        if (administrador.getContrasena() == null || administrador.getContrasena().trim().isEmpty()) {
//            throw new BusinessLogicException("La contraseña del administrador es obligatoria.");
//        }
//
//        if (administrador.getCodigo().length() > 45 ||
//                administrador.getNombre().length() > 45 ||
//                administrador.getApellidoPaterno().length() > 45 ||
//                administrador.getApellidoMaterno().length() > 45 ||
//                administrador.getDni().length() > 45 ||
//                administrador.getContrasena().length() > 45) {
//            throw new BusinessLogicException("Los campos del administrador no pueden superar 45 caracteres.");
//        }
    }

    private void validarDatosUsuario(Usuario usuario) {

        if (usuario == null) {
            throw new RuntimeException("Debe ingresar los datos del usuario.");
        }

        if (usuario.getDni() == null || usuario.getDni().trim().isEmpty()) {
            throw new RuntimeException("El DNI es obligatorio.");
        }

        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio.");
        }

        if (usuario.getApellidoPaterno() == null || usuario.getApellidoPaterno().trim().isEmpty()) {
            throw new RuntimeException("El apellido paterno es obligatorio.");
        }

        if (usuario.getApellidoMaterno() == null || usuario.getApellidoMaterno().trim().isEmpty()) {
            throw new RuntimeException("El apellido materno es obligatorio.");
        }

        if (usuario.getTelefono() == null || usuario.getTelefono().trim().isEmpty()) {
            throw new RuntimeException("El teléfono es obligatorio.");
        }

        if (usuario.getCorreoElectronico() == null || usuario.getCorreoElectronico().trim().isEmpty()) {
            throw new RuntimeException("El correo electrónico es obligatorio.");
        }

        if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
            throw new RuntimeException("La contraseña es obligatoria.");
        }
    }

    private void validarDatosEvento(Evento evento) throws BusinessLogicException {

        if (evento == null) {
            throw new BusinessLogicException("Debe ingresar los datos del evento.");
        }

        if (evento.getTitulo() == null || evento.getTitulo().trim().isEmpty()) {
            throw new BusinessLogicException("El título del evento es obligatorio.");
        }

        if (evento.getCapacidad_entradas() <= 0) {
            throw new BusinessLogicException("La capacidad debe ser mayor a cero.");
        }

        if (evento.getFecha() == null) {
            throw new BusinessLogicException("La fecha del evento es obligatoria.");
        }

        if (evento.getHora_inicio() == null) {
            throw new BusinessLogicException("La hora de inicio es obligatoria.");
        }

        if (evento.getHora_fin() == null) {
            throw new BusinessLogicException("La hora de fin es obligatoria.");
        }

        if (evento.getPrecio() < 0) {
            throw new BusinessLogicException("El precio no puede ser negativo.");
        }

        if (evento.getFK_idCategoria_evento() <= 0) {
            throw new BusinessLogicException("Debe seleccionar una categoría.");
        }

        if (evento.getFK_idDistrito() <= 0) {
            throw new BusinessLogicException("Debe seleccionar un distrito.");
        }

        if (evento.getIdAnfitrion() <= 0) {
            throw new BusinessLogicException("Debe seleccionar un anfitrión.");
        }
    }

    private void validarDatosSolicitud(Solicitud solicitud)
            throws BusinessLogicException {

        if (solicitud == null) {
            throw new BusinessLogicException(
                    "Debe ingresar los datos de la solicitud."
            );
        }

        if (solicitud.getTelefonoContacto() == null ||
                solicitud.getTelefonoContacto().trim().isEmpty()) {

            throw new BusinessLogicException(
                    "El teléfono de contacto es obligatorio."
            );
        }

        if (solicitud.getCorreoContacto() == null ||
                solicitud.getCorreoContacto().trim().isEmpty()) {

            throw new BusinessLogicException(
                    "El correo de contacto es obligatorio."
            );
        }

        if (solicitud.getMotivo() == null ||
                solicitud.getMotivo().trim().isEmpty()) {

            throw new BusinessLogicException(
                    "El motivo de la solicitud es obligatorio."
            );
        }
    }

    private void validarId(Integer id) throws BusinessLogicException {
        if (id == null || id <= 0) {
            throw new BusinessLogicException("El ID del administrador no es válido.");
        }
    }

}
