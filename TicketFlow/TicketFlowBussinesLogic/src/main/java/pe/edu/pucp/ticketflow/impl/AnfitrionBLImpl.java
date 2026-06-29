package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IAnfitrionBL;
import pe.edu.pucp.ticketflow.Infrastructure.AsyncExecutor;
import pe.edu.pucp.ticketflow.Infrastructure.EmailService;
import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.pago.model.Pago;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.usuario.model.Anfitrion;
import pe.edu.pucp.ticketflow.usuario.model.Cliente;
import pe.edu.pucp.ticketflow.usuario.model.Usuario;

import java.util.List;

public class AnfitrionBLImpl extends UsuarioBLImpl implements IAnfitrionBL {

    private final AnfitrionDAOImpl anfitrionDAO;
    private final EventoDAOImpl eventoDAO;
    private final ClienteDAOImpl clienteDAO;
    private final CompraDAOImpl compraDAO;
    private final PagosDAOImpl pagoDAO;
    private final EmailService emailService;
    public AnfitrionBLImpl() {
        this.anfitrionDAO = new AnfitrionDAOImpl();
        this.eventoDAO = new EventoDAOImpl();
        this.compraDAO = new CompraDAOImpl();
        this.pagoDAO = new PagosDAOImpl();
        this.clienteDAO = new ClienteDAOImpl();
        this.emailService = new EmailService();
    }

    @Override
    public Anfitrion registrarAnfitrion(Anfitrion anfitrion) throws BusinessLogicException {
        try {
            validarDatosBaseAnfitrion(anfitrion);

            return anfitrionDAO.create(anfitrion);

        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al registrar anfitrión: " + e.getMessage());
        }
    }

    @Override
    public Anfitrion buscarAnfitrionPorId(Integer idAnfitrion) throws BusinessLogicException {
        try {
            if (idAnfitrion == null || idAnfitrion <= 0) {
                throw new BusinessLogicException("El ID del anfitrión debe ser válido.");
            }

            Anfitrion anfitrion = anfitrionDAO.read(idAnfitrion);

            if (anfitrion == null) {
                throw new BusinessLogicException("No se encontró un anfitrión con ID: " + idAnfitrion);
            }

            return anfitrion;

        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al buscar anfitrión: " + e.getMessage());
        }
    }

    @Override
    public Anfitrion actualizarAnfitrion(Anfitrion anfitrion, Integer idAnfitrion) throws BusinessLogicException {
        // Este metodo es para el administrador
        try {
            validarDatosBaseAnfitrion(anfitrion);

            return anfitrionDAO.update(anfitrion, idAnfitrion);

        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al actualizar anfitrión: " + e.getMessage());
        }
    }

    @Override
    public void eliminarAnfitrion(Integer idAnfitrion) throws BusinessLogicException {
        try {
            if (idAnfitrion == null || idAnfitrion <= 0) {
                throw new BusinessLogicException("El ID del anfitrión debe ser válido.");
            }
            anfitrionDAO.delete(idAnfitrion);

        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al eliminar anfitrión: " + e.getMessage());
        }
    }

    @Override
    public List<Anfitrion> listarAnfitriones() throws BusinessLogicException {
        try {
            return anfitrionDAO.listAll();
        } catch (Exception e) {
            throw new BusinessLogicException("Error al listar anfitriones: " + e.getMessage());
        }
    }

    @Override
    public Evento crearEvento(Evento evento) throws BusinessLogicException {
        try {
            if (evento == null) {
                throw new BusinessLogicException("El evento no puede ser nulo.");
            }
            if (evento.getIdAnfitrion() <= 0) {
                throw new BusinessLogicException("El evento debe estar asociado a un anfitrión válido.");
            }
            return eventoDAO.create(evento);

        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al crear evento del anfitrión: " + e.getMessage());
        }
    }

    @Override
    public Evento mostrarEvento(Integer idEvento) throws BusinessLogicException {
        try {
            if (idEvento == null || idEvento <= 0) {
                throw new BusinessLogicException("El ID del evento debe ser válido.");
            }
            Evento evento = eventoDAO.read(idEvento);

            if (evento == null) {
                throw new BusinessLogicException("No se encontró un evento con ID: " + idEvento);
            }

            return evento;
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al mostrar evento: " + e.getMessage());
        }
    }

    @Override
    public Evento actualizarEvento(Evento evento, Integer idEvento) throws BusinessLogicException {
        try {
            if (evento == null) {
                throw new BusinessLogicException("El evento no puede ser nulo.");
            }

            if (idEvento == null || idEvento <= 0) {
                throw new BusinessLogicException("El ID del evento debe ser válido.");
            }

            return eventoDAO.update(evento, idEvento);

        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al actualizar evento: " + e.getMessage());
        }
    }

    @Override
    public void eliminarEvento(Integer idEvento) throws BusinessLogicException {
        try {
            if (idEvento == null || idEvento <= 0) {
                throw new BusinessLogicException("El ID del evento debe ser válido.");
            }

            Evento evento =  eventoDAO.read(idEvento);

            eventoDAO.delete(idEvento);

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

        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al eliminar evento: " + e.getMessage());
        }
    }

    @Override
    public List<Evento> verTodosLosEventos() throws BusinessLogicException {
        try {
            return eventoDAO.listAll();
        } catch (Exception e) {
            throw new BusinessLogicException("Error al listar eventos: " + e.getMessage());
        }
    }

    @Override
    public List<Evento> verEventosPorAnfitrion(Integer idAnfitrion) throws BusinessLogicException {
        try {
            if (idAnfitrion == null || idAnfitrion <= 0) {
                throw new BusinessLogicException("El ID del anfitrión debe ser válido.");
            }
            // Llamamos al DAO que ya creaste maravillosamente
            return eventoDAO.listarEventosPorAnfitrion(idAnfitrion);
        } catch (Exception e) {
            throw new BusinessLogicException("Error al listar los eventos del anfitrión: " + e.getMessage());
        }
    }

    @Override
    public void editarPerfilAnfitrion(Anfitrion anfitrion) throws BusinessLogicException {
        //  Este metodo es para el usuario final
        try {
            validarDatosBaseAnfitrion(anfitrion);

            anfitrionDAO.update(anfitrion, anfitrion.getIdUsuario());

            System.out.println("Perfil del anfitrión con ID " + anfitrion.getIdUsuario() + " actualizado con éxito.");

        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al editar el perfil del anfitrión: " + e.getMessage());
        }
    }

    @Override
    public List<Compra> verComprasDeSusEventos(Integer idAnfitrion) throws BusinessLogicException {
        try {
            if (idAnfitrion == null || idAnfitrion <= 0) {
                throw new BusinessLogicException("El ID del anfitrión debe ser válido.");
            }
            return compraDAO.listarComprasPorAnfitrion(idAnfitrion);
        } catch (Exception e) {
            throw new BusinessLogicException("Error al visualizar las compras: " + e.getMessage());
        }
    }

    @Override
    public List<Pago> verPagosDeSusEventos(Integer idAnfitrion) throws BusinessLogicException {
        try {
            if (idAnfitrion == null || idAnfitrion <= 0) {
                throw new BusinessLogicException("El ID del anfitrión debe ser válido.");
            }
            return pagoDAO.listarPagosPorAnfitrion(idAnfitrion);
        } catch (Exception e) {
            throw new BusinessLogicException("Error al visualizar los pagos: " + e.getMessage());
        }
    }

    // --- MÓDULO DE VALIDACIONES REUTILIZABLES ---
    private void validarDatosBaseAnfitrion(Anfitrion anfitrion) throws BusinessLogicException {
        // 1. Validaciones comunes para todos (Admin, Usuario, Registro)
        if (anfitrion == null) {
            throw new BusinessLogicException("El anfitrión no puede ser nulo.");
        }
        if (anfitrion.getIdUsuario() <= 0) {
            throw new BusinessLogicException("El ID del anfitrión debe ser válido.");
        }
        if (anfitrion.getRazonSocial() == null || anfitrion.getRazonSocial().isBlank()) {
            throw new BusinessLogicException("La razón social del anfitrión es obligatoria.");
        }
        if (anfitrion.getCuentaBancaria() == null || anfitrion.getCuentaBancaria().isBlank()) {
            throw new BusinessLogicException("La cuenta bancaria del anfitrión es obligatoria.");
        }
        // 2. Validaciones más estrictas (Ej: para registros nuevos o actualizaciones de Admin)
        if (anfitrion.getBanco() == null || anfitrion.getBanco().getId() <= 0) {
            throw new BusinessLogicException("El anfitrión debe tener un banco válido.");
        }
        if (anfitrion.getRuc() == null || anfitrion.getRuc().isBlank()) {
            throw new BusinessLogicException("El RUC del anfitrión es obligatorio.");
        }
    }

    @Override
    public void actualizarDatosEmpresa(Integer idAnfitrion, String razonSocial, String ruc, String cuentaBancaria, Integer idBanco) throws BusinessLogicException {
        try {
            if (idAnfitrion == null || idAnfitrion <= 0) {
                throw new BusinessLogicException("El ID del anfitrión debe ser válido.");
            }

            if (razonSocial == null || razonSocial.isBlank()) {
                throw new BusinessLogicException("La razón social es obligatoria.");
            }

            if (ruc == null || ruc.isBlank()) {
                throw new BusinessLogicException("El RUC es obligatorio.");
            }

            if (cuentaBancaria == null || cuentaBancaria.isBlank()) {
                throw new BusinessLogicException("La cuenta bancaria es obligatoria.");
            }

            if (idBanco == null || idBanco <= 0) {
                throw new BusinessLogicException("El banco es obligatorio.");
            }

            anfitrionDAO.actualizarDatosEmpresa(idAnfitrion, razonSocial, ruc, cuentaBancaria, idBanco);

        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al actualizar datos de empresa: " + e.getMessage());
        }
    }

}