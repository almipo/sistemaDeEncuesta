package service.encuestaService;


import data.DAOs.DAOException;
import data.DAOs.IPreguntaDAO;

import data.DAOs.encuestaDAO.PreguntaDAO;
import data.Model.Pregunta;
import service.IPreguntaService;
import service.ServiceException;

import java.util.List;

public class PreguntaService implements IPreguntaService {
    private final IPreguntaDAO dao = new PreguntaDAO();

    @Override
    public Pregunta crearPregunta(int encuestaId, String texto) throws ServiceException {
        try {
            Pregunta p = new Pregunta(0, texto, encuestaId);
            dao.crear(p);
            return p;
        } catch (DAOException ex) {
            throw new ServiceException("No se pudo crear la pregunta.", ex);
        }
    }

    @Override
    public void modificarPregunta(Pregunta p) throws ServiceException {
        try {
            dao.modificar(p);
        } catch (DAOException ex) {
            throw new ServiceException("No se pudo modificar la pregunta.", ex);
        }
    }

    @Override
    public void eliminarPregunta(int idPregunta) throws ServiceException {
        try {
            dao.eliminar(idPregunta);
        } catch (DAOException ex) {
            throw new ServiceException("No se pudo eliminar la pregunta.", ex);
        }
    }

    @Override
    public Pregunta buscarPreguntaPorId(int idPregunta) throws ServiceException {
        try {
            return dao.buscar(idPregunta);
        } catch (DAOException ex) {
            throw new ServiceException("Error al buscar la pregunta.", ex);
        }
    }

    @Override
    public List<Pregunta> listarPreguntasPorEncuesta(int encuestaId) throws ServiceException {
        try {
            return dao.buscarPorEncuesta(encuestaId);
        } catch (DAOException ex) {
            throw new ServiceException("Error al listar preguntas de la encuesta.", ex);
        }
    }
}
