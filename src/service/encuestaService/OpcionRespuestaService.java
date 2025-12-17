package service.encuestaService;


import data.DAOs.DAOException;

import data.DAOs.IOpcionRespuestaDAO;
import data.DAOs.encuestaDAO.OpcionRespuestaDAO;
import data.Model.Respuesta;
import service.IOpcionRespuestaService;
import service.ServiceException;

import java.util.List;

public class OpcionRespuestaService implements IOpcionRespuestaService {
    private final IOpcionRespuestaDAO dao = new OpcionRespuestaDAO();

    @Override
    public Respuesta crearOpcion(int preguntaId, int valor, String descripcion) throws ServiceException {
        try {
            Respuesta o = new Respuesta(valor, descripcion);
            o.setPreguntaId(preguntaId);
            dao.crear(o);
            return o;
        } catch (DAOException ex) {
            throw new ServiceException("No se pudo crear la opción de respuesta.", ex);
        }
    }

    @Override
    public void modificarOpcion(Respuesta o) throws ServiceException {
        try {
            dao.modificar(o);
        } catch (DAOException ex) {
            throw new ServiceException("No se pudo modificar la opción de respuesta.", ex);
        }
    }

    @Override
    public void eliminarOpcion(int idOpcion) throws ServiceException {
        try {
            dao.eliminar(idOpcion);
        } catch (DAOException ex) {
            throw new ServiceException("No se pudo eliminar la opción de respuesta.", ex);
        }
    }

    @Override
    public Respuesta buscarOpcionPorId(int idOpcion) throws ServiceException {
        try {
            return dao.buscar(idOpcion);
        } catch (DAOException ex) {
            throw new ServiceException("Error al buscar la opción de respuesta.", ex);
        }
    }

    @Override
    public List<Respuesta> listarOpcionesPorPregunta(int preguntaId) throws ServiceException {
        try {
            return dao.buscarPorPregunta(preguntaId);
        } catch (DAOException ex) {
            throw new ServiceException("Error al listar opciones de respuesta.", ex);
        }
    }
}
