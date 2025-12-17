package data.DAOs.encuestaDAO;


import data.DAOs.DAOException;
import data.DAOs.IRespuestaUsuarioDAO;
import data.Model.RespuestaUsuario;
import data.conectionBd.Conexion;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class RespuestaUsuarioDAO implements IRespuestaUsuarioDAO {

    @Override
    public void registrar(RespuestaUsuario ru) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(
                    "INSERT INTO respuestaUsuarios " +
                            "(fechaRealizacion, idUsuario, idEncuesta, idPregunta, idRespuesta, textoLibre) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setDate(1, Date.valueOf(ru.getFechaRealizacion()));
            ps.setInt(2, ru.getUsuarioId());
            ps.setInt(3, ru.getEncuestaId());
            ps.setInt(4, ru.getPreguntaId());
            // si la respuesta es numérica ponemos idRespuesta, sino 0
            if (ru.getRespuestaId() != null) {
                ps.setInt(5, ru.getRespuestaId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            ps.setString(6, ru.getRespuestaLibre());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                ru.setIdRespuesta(rs.getInt(1));
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error al registrar respuesta de usuario", e);
        } finally {
            try { if (ps != null) ps.close(); }
            catch (SQLException e) { throw new DAOException("Error al cerrar PreparedStatement", e); }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }

    @Override
    public List<RespuestaUsuario> buscarPorEncuesta(int idEncuesta) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<RespuestaUsuario> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(
                    "SELECT * FROM respuestaUsuarios WHERE idEncuesta = ?"
            );
            ps.setInt(1, idEncuesta);
            rs = ps.executeQuery();
            while (rs.next()) {
                RespuestaUsuario ru = new RespuestaUsuario();
                ru.setIdRespuesta(rs.getInt("id"));
                ru.setFechaRealizacion(rs.getDate("fechaRealizacion").toLocalDate());
                ru.setUsuarioId(rs.getInt("idUsuario"));
                ru.setEncuestaId(idEncuesta);
                ru.setPreguntaId(rs.getInt("idPregunta"));
                ru.setRespuestaId(rs.getInt("idRespuesta"));
                ru.setRespuestaLibre(rs.getString("textoLibre"));
                lista.add(ru);
            }
            return lista;
        } catch (SQLException e) {
            throw new DAOException("Error al buscar respuestas de encuesta", e);
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); }
            catch (SQLException e) { throw new DAOException("Error al cerrar recursos", e); }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }

    @Override
    public Map<Integer,Integer> resumenPorPregunta(int idEncuesta,
                                                   int idPregunta,
                                                   LocalDate desde,
                                                   LocalDate hasta) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<Integer,Integer> contador = new HashMap<>();
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(
                    "SELECT idRespuesta, COUNT(*) AS total " +
                            "FROM respuestaUsuarios " +
                            "WHERE idEncuesta = ? AND idPregunta = ? " +
                            "  AND fechaRealizacion BETWEEN ? AND ? " +
                            "  AND idRespuesta IS NOT NULL " +
                            "GROUP BY idRespuesta"
            );
            ps.setInt(1, idEncuesta);
            ps.setInt(2, idPregunta);
            ps.setDate(3, Date.valueOf(desde));
            ps.setDate(4, Date.valueOf(hasta));
            rs = ps.executeQuery();
            while (rs.next()) {
                int valor = rs.getInt("idRespuesta");
                int tot   = rs.getInt("total");
                contador.put(valor, tot);
            }
            return contador;
        } catch (SQLException e) {
            throw new DAOException("Error al obtener resumen de respuestas", e);
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); }
            catch (SQLException e) { throw new DAOException("Error al cerrar recursos", e); }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }

    @Override
    public Map<String, Map<String, Integer>> obtenerResumenPorEncuesta(int idEncuesta) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Map<String, Integer>> resumen = new HashMap<>();
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(
                    "SELECT p.descripcion AS pregunta, o.descripcion AS opcion, COUNT(*) AS total " +
                            "FROM respuestaUsuarios ru " +
                            "JOIN pregunta AS p ON ru.idPregunta = p.id " +
                            "JOIN respuestasposibles AS o ON ru.idRespuesta = o.id " +
                            "WHERE ru.idEncuesta = ? " +
                            "GROUP BY p.descripcion, o.descripcion"
            );
            ps.setInt(1, idEncuesta);
            rs = ps.executeQuery();
            while (rs.next()) {
                String pregunta = rs.getString("pregunta");
                String opcion = rs.getString("opcion");
                int total = rs.getInt("total");

                resumen.putIfAbsent(pregunta, new HashMap<>());
                resumen.get(pregunta).put(opcion, total);
            }
            return resumen;
        } catch (SQLException e) {
            throw new DAOException("Error al obtener resumen por encuesta", e);
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); }
            catch (SQLException e) { throw new DAOException("Error al cerrar recursos", e); }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }

}
