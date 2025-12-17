package service.encuestaService;


import data.DAOs.DAOException;

import data.DAOs.IEncuestaDAO;
import data.DAOs.encuestaDAO.EncuestaDAO;
import data.Model.Encuesta;
import data.Model.Pregunta;
import data.Model.Respuesta;
import data.Model.ResumenRespuestaDTO;
import service.IEncuestaService;
import service.IOpcionRespuestaService;
import service.IPreguntaService;
import service.ServiceException;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EncuestaService implements IEncuestaService {
    private final IEncuestaDAO dao = new EncuestaDAO();
    private final IPreguntaService preguntaService = new PreguntaService();
    private final IOpcionRespuestaService respuestaService = new OpcionRespuestaService();

    @Override
        public Encuesta crearEncuesta(Encuesta e) throws ServiceException {
            try {
                dao.crear(e);
                return e;
            } catch (DAOException ex) {
                throw new ServiceException("No se pudo crear la encuesta.", ex);
            }
        }

    @Override
    public void modificarEncuesta(Encuesta e) throws ServiceException {
        try {
            dao.modificar(e);
        } catch (DAOException ex) {
            throw new ServiceException("No se pudo modificar la encuesta.", ex);
        }
    }

    @Override
    public void eliminarEncuesta(int idEncuesta) throws ServiceException {
        try {
            dao.eliminar(idEncuesta);
        } catch (DAOException ex) {
            throw new ServiceException("No se pudo eliminar la encuesta.", ex);
        }
    }

    @Override
    public Encuesta buscarEncuestaPorId(int idEncuesta) throws ServiceException {
        try {
            Encuesta e = dao.buscar(idEncuesta);
            if (e == null) return null;


            List<Pregunta> preguntas = preguntaService.listarPreguntasPorEncuesta(idEncuesta);


            for (Pregunta p : preguntas) {
                List<Respuesta> respuestas = respuestaService.listarOpcionesPorPregunta(p.getIdPregunta());
                p.setRespuestas((ArrayList<Respuesta>) respuestas);
            }

            e.setPreguntas(preguntas);

            return e;

        } catch (DAOException ex) {
            throw new ServiceException("Error al buscar encuesta por ID", ex);
        }
    }


    @Override
    public List<Encuesta> listarEncuestasPorAdministrador(int id) throws ServiceException {
        try {
            return dao.listarEncuestasPorAdministrador(id);
        } catch (DAOException ex) {
            throw new ServiceException("Error al listar encuestas.", ex);
        }
    }

    @Override
    public boolean tieneRespuestasAsociadas(int idEncuesta) throws ServiceException {
        try {
            return dao.tieneRespuestasAsociadas(idEncuesta);
        } catch (DAOException ex) {
            throw new ServiceException("Error al verificar respuestas asociadas.", ex);
        }
    }


    @Override
    public List<ResumenRespuestaDTO> obtenerResumenEncuesta(int idEncuesta, LocalDate desde, LocalDate hasta) throws ServiceException {
        try {
            return dao.obtenerResumenEncuesta(idEncuesta, desde, hasta);
        } catch (DAOException ex) {
            throw new ServiceException("Error al obtener resumen de la encuesta.", ex);
        }
    }

    @Override
    public List<ResumenRespuestaDTO> obtenerResumenTodasEncuestas(LocalDate desde, LocalDate hasta) throws ServiceException {
        try {
            return dao.obtenerResumenTodasEncuestas(desde, hasta); 
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener resumen de todas las encuestas", e);
        }
    }

    @Override
    public List<Encuesta> listarTodasEncuestas() throws ServiceException {
        try {
            return dao.buscarTodas();
        } catch (DAOException ex) {
            throw new ServiceException("Error al listar todas las encuestas.", ex);
        }
    }

    @Override
    public List<Encuesta> obtenerUltimasEncuestas(int cantidad) throws ServiceException {
        try {
            return dao.obtenerUltimasEncuestas(cantidad);
        } catch (DAOException ex) {
            throw new ServiceException("Error al obtener las últimas encuestas.", ex);
        }
    }

}