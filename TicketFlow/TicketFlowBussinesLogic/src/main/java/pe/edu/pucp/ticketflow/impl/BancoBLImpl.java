package pe.edu.pucp.ticketflow.impl;
import pe.edu.pucp.ticketflow.IBancoBL;
import pe.edu.pucp.ticketflow.IBancoDAO;
import pe.edu.pucp.ticketflow.banco.model.Banco;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;

import java.util.List;
public class BancoBLImpl implements IBancoBL{

    private final IBancoDAO bancoDAO;

    public BancoBLImpl() {
        this.bancoDAO = new BancoDAOImpl();
    }

    @Override
    public Banco registrarBanco(Banco banco) throws BusinessLogicException {
        try {
            validarBanco(banco);
            return bancoDAO.create(banco);
        } catch (Exception ex) {
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException) ex;
            }
            throw new BusinessLogicException(ex);
        }
    }

    @Override
    public Banco buscarBancoPorId(Integer id) throws BusinessLogicException {
        try {
            validarId(id);

            Banco banco = bancoDAO.read(id);

            if (banco == null) {
                throw new BusinessLogicException("No existe un banco con el ID indicado.");
            }

            return banco;

        } catch (Exception ex) {
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException) ex;
            }
            throw new BusinessLogicException(ex);
        }
    }

    @Override
    public Banco actualizarBanco(Banco banco, Integer id) throws BusinessLogicException {
        try {
            validarId(id);
            validarBanco(banco);

            Banco bancoExistente = bancoDAO.read(id);

            if (bancoExistente == null) {
                throw new BusinessLogicException("No se puede actualizar. El banco no existe.");
            }

            return bancoDAO.update(banco, id);

        } catch (Exception ex) {
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException) ex;
            }
            throw new BusinessLogicException(ex);
        }
    }

    @Override
    public void eliminarBanco(Integer id) throws BusinessLogicException {
        try {
            validarId(id);

            Banco bancoExistente = bancoDAO.read(id);

            if (bancoExistente == null) {
                throw new BusinessLogicException("No se puede eliminar. El banco no existe.");
            }

            bancoDAO.delete(id);

        } catch (Exception ex) {
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException) ex;
            }
            throw new BusinessLogicException(ex);
        }
    }

    @Override
    public List<Banco> listarBancos() throws BusinessLogicException {
        try {
            return bancoDAO.listAll();
        } catch (Exception ex) {
            throw new BusinessLogicException(ex);
        }
    }

    private void validarBanco(Banco banco) throws BusinessLogicException {
        if (banco == null) {
            throw new BusinessLogicException("El banco no puede ser nulo.");
        }

        if (banco.getNombre_largo() == null || banco.getNombre_largo().trim().isEmpty()) {
            throw new BusinessLogicException("El nombre largo del banco es obligatorio.");
        }

        if (banco.getNombre_corto() == null || banco.getNombre_corto().trim().isEmpty()) {
            throw new BusinessLogicException("El nombre corto del banco es obligatorio.");
        }

        if (banco.getNombre_largo().length() > 45) {
            throw new BusinessLogicException("El nombre largo no puede superar 45 caracteres.");
        }

        if (banco.getNombre_corto().length() > 45) {
            throw new BusinessLogicException("El nombre corto no puede superar 45 caracteres.");
        }
    }

    private void validarId(Integer id) throws BusinessLogicException {
        if (id == null || id <= 0) {
            throw new BusinessLogicException("El ID del banco no es válido.");
        }
    }

}
