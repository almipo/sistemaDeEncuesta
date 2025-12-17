package service;

import data.Model.RespuestaUsuario;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IRespuestaUsuarioService {
    void registrarRespuesta(RespuestaUsuario ru) throws ServiceException;
    List<RespuestaUsuario> listarRespuestasPorEncuesta(int encuestaId) throws ServiceException;
    Map<Integer, Integer> resumenPorPregunta(int encuestaId, int preguntaId, LocalDate desde, LocalDate hasta) throws ServiceException;

    Map<String, Map<String, Integer>> obtenerResumenPorEncuesta(int idEncuesta) throws ServiceException;
}