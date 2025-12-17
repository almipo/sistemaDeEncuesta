package data.DAOs;

import data.Model.Encuesta;
import data.Model.Pregunta;
import data.Model.Respuesta;
import data.Model.ResumenRespuestaDTO;

import java.time.LocalDate;
import java.util.List;


public interface IEncuestaDAO {
 void crear(Encuesta e) throws DAOException;
 void eliminar(int idEncuesta)throws DAOException;
 void modificar(Encuesta e)throws DAOException;
 Encuesta buscar(int idEncuesta)throws DAOException;
 List<Encuesta> buscarTodas() throws DAOException;;
 List<Encuesta> buscarPorFecha(LocalDate desde, LocalDate hasta) throws DAOException;
 boolean tieneRespuestasAsociadas(int idEncuesta) throws DAOException;
 List<ResumenRespuestaDTO> obtenerResumenEncuesta(int idEncuesta, LocalDate desde, LocalDate hasta) throws DAOException;
 List<ResumenRespuestaDTO> obtenerResumenTodasEncuestas(LocalDate desde, LocalDate hasta) throws DAOException;
  List<Pregunta> obtenerPreguntasPorEncuesta(int idEncuesta) throws DAOException;
 List<Respuesta> obtenerRespuestasPorPregunta(int idPregunta) throws DAOException;
 List<Encuesta> listarEncuestasPorAdministrador(int id) throws DAOException;
    List<Encuesta> obtenerUltimasEncuestas(int cantidad) throws DAOException;

}
