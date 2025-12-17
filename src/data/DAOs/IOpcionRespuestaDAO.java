package data.DAOs;

import data.Model.Respuesta;
import java.util.List;

public interface IOpcionRespuestaDAO {

    void crear(Respuesta r) throws DAOException;
    void modificar(Respuesta r) throws DAOException;
    void eliminar(int idOpcion) throws DAOException;
    Respuesta buscar(int idOpcion) throws DAOException;
    List<Respuesta> buscarPorPregunta(int idPregunta) throws DAOException;
}
