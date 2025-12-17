package service;

import data.Model.Respuesta;
import java.util.List;

public interface IOpcionRespuestaService {
    Respuesta crearOpcion(int preguntaId, int valor, String descripcion) throws ServiceException;
    void modificarOpcion(Respuesta o) throws ServiceException;
    void eliminarOpcion(int idOpcion) throws ServiceException;
    Respuesta buscarOpcionPorId(int idOpcion) throws ServiceException;
    List<Respuesta> listarOpcionesPorPregunta(int preguntaId) throws ServiceException;

}