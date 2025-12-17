package service;

import data.Model.Pregunta;
import data.Model.Respuesta;

import java.util.List;

public interface IPreguntaCompuestaService {

    Pregunta crearConOpcionesPorDefecto(int encuestaId, String texto) throws ServiceException;

    Pregunta crearConOpcionesVariables(int encuestaId,
                                       String texto,
                                       List<String> etiquetas,
                                       boolean permitirLibre) throws ServiceException;
}
