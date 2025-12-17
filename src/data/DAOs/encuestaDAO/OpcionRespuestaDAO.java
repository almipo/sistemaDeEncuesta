package data.DAOs.encuestaDAO;


import data.DAOs.DAOException;
import data.DAOs.IOpcionRespuestaDAO;
import data.Model.Respuesta;
import data.conectionBd.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OpcionRespuestaDAO implements IOpcionRespuestaDAO {

    @Override
    public void crear(Respuesta r) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(
                    "INSERT INTO respuestasPosibles (valor, descripcion, idPregunta) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, r.getValor());
            ps.setString(2, r.getDescripcion());
            ps.setInt(3, r.getPreguntaId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                r.setIdOpcion(rs.getInt(1));
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error al crear opción de respuesta", e);
        } finally {
            try { if (ps != null) ps.close(); }
            catch (SQLException e) { throw new DAOException("Error cerrando PreparedStatement", e); }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }

    @Override
    public void modificar(Respuesta r) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(
                    "UPDATE respuestasPosibles SET valor = ?, descripcion = ? WHERE id = ?"
            );
            ps.setInt(1, r.getValor());
            ps.setString(2, r.getDescripcion());
            ps.setInt(3, r.getIdOpcion());
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new DAOException("Opción de respuesta no encontrada: ID " + r.getIdOpcion());
            }
        } catch (SQLException e) {
            throw new DAOException("Error al modificar opción de respuesta", e);
        } finally {
            try { if (ps != null) ps.close(); }
            catch (SQLException e) { throw new DAOException("Error cerrando PreparedStatement", e); }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }

    @Override
    public void eliminar(int idOpcion) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement("DELETE FROM respuestasPosibles WHERE id = ?");
            ps.setInt(1, idOpcion);
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new DAOException("Opción de respuesta no encontrada: ID " + idOpcion);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar opción de respuesta", e);
        } finally {
            try { if (ps != null) ps.close(); }
            catch (SQLException e) { throw new DAOException("Error cerrando PreparedStatement", e); }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }

    @Override
    public Respuesta buscar(int idOpcion) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement("SELECT * FROM respuestasPosibles WHERE id = ?");
            ps.setInt(1, idOpcion);
            rs = ps.executeQuery();
            if (!rs.next()) return null;
            Respuesta r = new Respuesta(
                    idOpcion,
                    rs.getString("descripcion"),
                    rs.getInt("valor")
            );
            r.setPreguntaId(rs.getInt("idPregunta"));
            return r;
        } catch (SQLException e) {
            throw new DAOException("Error al buscar opción de respuesta", e);
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); }
            catch (SQLException e) { throw new DAOException("Error cerrando recursos", e); }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }

    @Override
    public List<Respuesta> buscarPorPregunta(int idPregunta) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Respuesta> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement("SELECT * FROM respuestasPosibles WHERE idPregunta = ?");
            ps.setInt(1, idPregunta);
            rs = ps.executeQuery();
            while (rs.next()) {
                Respuesta r = new Respuesta(
                        rs.getInt("valor"),
                        rs.getString("descripcion")
                );
                r.setIdOpcion(rs.getInt("id"));
                r.setPreguntaId(idPregunta);
                lista.add(r);
            }
            return lista;
        } catch (SQLException e) {
            throw new DAOException("Error al listar opciones de respuesta", e);
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); }
            catch (SQLException e) { throw new DAOException("Error cerrando recursos", e); }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }
}
