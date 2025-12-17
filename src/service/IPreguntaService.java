package service;

import data.Model.Pregunta;
import java.util.List;

public interface IPreguntaService {
    Pregunta crearPregunta(int encuestaId, String texto) throws ServiceException;
    void modificarPregunta(Pregunta p) throws ServiceException;
    void eliminarPregunta(int idPregunta) throws ServiceException;
    Pregunta buscarPreguntaPorId(int idPregunta) throws ServiceException;
    List<Pregunta> listarPreguntasPorEncuesta(int encuestaId) throws ServiceException;
}