package service.encuestaService;


import data.DAOs.DAOException;
import data.DAOs.IRespuestaUsuarioDAO;
import data.DAOs.encuestaDAO.RespuestaUsuarioDAO;
import data.Model.RespuestaUsuario;
import service.IRespuestaUsuarioService;
import service.ServiceException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class RespuestaUsuarioService implements IRespuestaUsuarioService {
    private final IRespuestaUsuarioDAO dao = new RespuestaUsuarioDAO();

    @Override
    public void registrarRespuesta(RespuestaUsuario ru) throws ServiceException {
        try {
            dao.registrar(ru);
        } catch (DAOException ex) {
            throw new ServiceException("No se pudo registrar la respuesta del usuario.", ex);
        }
    }

    @Override
    public List<RespuestaUsuario> listarRespuestasPorEncuesta(int encuestaId) throws ServiceException {
        try {
            return dao.buscarPorEncuesta(encuestaId);
        } catch (DAOException ex) {
            throw new ServiceException("Error al listar respuestas de la encuesta.", ex);
        }
    }

    @Override
    public Map<Integer,Integer> resumenPorPregunta(int encuestaId,
                                                   int preguntaId,
                                                   LocalDate desde,
                                                   LocalDate hasta) throws ServiceException {
        try {
            return dao.resumenPorPregunta(encuestaId, preguntaId, desde, hasta);
        } catch (DAOException ex) {
            throw new ServiceException("Error al obtener resumen de respuestas.", ex);
        }
    }

    @Override
    public Map<String, Map<String, Integer>> obtenerResumenPorEncuesta(int idEncuesta) throws ServiceException {
        try {
            return dao.obtenerResumenPorEncuesta(idEncuesta);
        } catch (DAOException ex) {
            throw new ServiceException("Error al obtener resumen por encuesta.", ex);
        }
    }
}
