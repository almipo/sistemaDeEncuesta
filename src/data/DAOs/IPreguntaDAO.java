package data.DAOs;

import data.Model.Pregunta;
import java.util.List;

public interface IPreguntaDAO {

    void crear(Pregunta p) throws DAOException;
    void modificar(Pregunta p) throws DAOException;
    void eliminar(int idPregunta) throws DAOException;
    Pregunta buscar(int idPregunta) throws DAOException;
    List<Pregunta> buscarPorEncuesta(int idEncuesta) throws DAOException;
}
