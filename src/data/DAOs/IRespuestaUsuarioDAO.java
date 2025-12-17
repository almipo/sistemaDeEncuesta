package data.DAOs;


import data.Model.RespuestaUsuario;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface IRespuestaUsuarioDAO {

    void registrar(RespuestaUsuario respuestaUsuario) throws DAOException;

    List<RespuestaUsuario> buscarPorEncuesta(int idEncuesta) throws DAOException;

    Map<Integer, Integer> resumenPorPregunta(int idEncuesta, int idPregunta, LocalDate desde, LocalDate hasta) throws DAOException;
    Map<String, Map<String, Integer>> obtenerResumenPorEncuesta(int idEncuesta) throws DAOException;
}
