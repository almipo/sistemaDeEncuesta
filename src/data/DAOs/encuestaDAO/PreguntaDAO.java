package data.DAOs.encuestaDAO;

import data.DAOs.DAOException;
import data.DAOs.IPreguntaDAO;
import data.Model.Pregunta;
import data.conectionBd.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaDAO implements IPreguntaDAO {

    @Override
    public void crear(Pregunta p) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(
                    "INSERT INTO pregunta (descripcion, idEncuesta) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, p.getPregunta());
            ps.setInt(2, p.getEncuestaId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                p.setIdPregunta(rs.getInt(1));
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error al crear la pregunta", e);
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new DAOException("Error al cerrar el PreparedStatement", e);
            }
            if (con != null) {
                Conexion.cerrarConexion(con);
            }
        }
    }

    @Override
    public void modificar(Pregunta p) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(
                    "UPDATE pregunta SET descripcion = ? WHERE id = ?"
            );
            ps.setString(1, p.getPregunta());
            ps.setInt(2, p.getIdPregunta());
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new DAOException("Pregunta no encontrada para ID " + p.getIdPregunta());
            }
        } catch (SQLException e) {
            throw new DAOException("Error al modificar la pregunta", e);
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new DAOException("Error al cerrar el PreparedStatement", e);
            }
            if (con != null) {
                Conexion.cerrarConexion(con);
            }
        }
    }

    @Override
    public void eliminar(int idPregunta) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement("DELETE FROM pregunta WHERE id = ?");
            ps.setInt(1, idPregunta);
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new DAOException("Pregunta no encontrada para ID " + idPregunta);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar la pregunta", e);
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new DAOException("Error al cerrar el PreparedStatement", e);
            }
            if (con != null) {
                Conexion.cerrarConexion(con);
            }
        }
    }

    @Override
    public Pregunta buscar(int idPregunta) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement("SELECT * FROM pregunta WHERE id = ?");
            ps.setInt(1, idPregunta);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Pregunta p = new Pregunta(
                    idPregunta,
                    rs.getString("descripcion"),
                    rs.getInt("idEncuesta")
            );
            return p;
        } catch (SQLException e) {
            throw new DAOException("Error al buscar la pregunta", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new DAOException("Error al cerrar recursos", e);
            }
            if (con != null) {
                Conexion.cerrarConexion(con);
            }
        }
    }

    @Override
    public List<Pregunta> buscarPorEncuesta(int idEncuesta) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Pregunta> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement("SELECT * FROM pregunta WHERE idEncuesta = ?");
            ps.setInt(1, idEncuesta);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pregunta p = new Pregunta(
                        rs.getInt("id"),
                        rs.getString("descripcion"),
                        idEncuesta
                );
                lista.add(p);
            }
            return lista;
        } catch (SQLException e) {
            throw new DAOException("Error al listar preguntas", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new DAOException("Error al cerrar recursos", e);
            }
            if (con != null) {
                Conexion.cerrarConexion(con);
            }
        }
    }
}
