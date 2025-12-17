package service;

import data.Model.Encuesta;
import data.Model.ResumenRespuestaDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface IEncuestaService {
    Encuesta crearEncuesta(Encuesta e) throws ServiceException;
    void modificarEncuesta(Encuesta e) throws ServiceException;
    void eliminarEncuesta(int idEncuesta) throws ServiceException;
    Encuesta buscarEncuestaPorId(int idEncuesta) throws ServiceException;
    List<Encuesta> listarTodasEncuestas() throws ServiceException;
    List<Encuesta> listarEncuestasPorAdministrador(int id) throws ServiceException;
    public boolean tieneRespuestasAsociadas(int idEncuesta) throws ServiceException;
    List<ResumenRespuestaDTO> obtenerResumenEncuesta(int idEncuesta, LocalDate desde, LocalDate hasta) throws ServiceException;
    List<ResumenRespuestaDTO> obtenerResumenTodasEncuestas(LocalDate desde, LocalDate hasta) throws ServiceException;

    List<Encuesta> obtenerUltimasEncuestas(int i) throws ServiceException;
}