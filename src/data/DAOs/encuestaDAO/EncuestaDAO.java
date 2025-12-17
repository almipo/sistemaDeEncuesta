package data.DAOs.encuestaDAO;

import data.DAOs.DAOException;
import data.DAOs.IEncuestaDAO;
import data.Model.Encuesta;
import data.Model.Pregunta;
import data.Model.Respuesta;
import data.Model.ResumenRespuestaDTO;
import data.conectionBd.Conexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EncuestaDAO implements IEncuestaDAO {

    @Override
    public void crear(Encuesta e) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(
                    "INSERT INTO encuesta (titulo, fechaCreacion, idAdministrador) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, e.getTitulo());

            ps.setDate(2, Date.valueOf(e.getFechaDeCreacion()));
            System.out.println("titulo dao"+ e.getTitulo());
            ps.setInt(3, e.getIdAdministrador());
            System.out.println("id dao"+ e.getIdAdministrador());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                e.setIdEncuesta(rs.getInt(1));
            }
            rs.close();
        } catch (SQLException ex) {
            throw new DAOException("Error al crear la encuesta", ex);
        } finally {
            try { if (ps != null) ps.close(); }
            catch (SQLException ex) { throw new DAOException("Error cerrando PreparedStatement", ex); }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }

    @Override
    public void modificar(Encuesta e) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(
                    "UPDATE encuesta SET titulo = ?, fechaCreacion = ?, idAdministrador = ? WHERE id = ?"
            );
            ps.setString(1, e.getTitulo());
            ps.setDate(2, Date.valueOf(e.getFechaDeCreacion()));
            ps.setInt(3, e.getIdAdministrador());
            ps.setInt(4, e.getIdEncuesta());
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new DAOException("Encuesta no encontrada con ID " + e.getIdEncuesta());
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al modificar la encuesta", ex);
        } finally {
            try { if (ps != null) ps.close(); }
            catch (SQLException ex) { throw new DAOException("Error cerrando PreparedStatement", ex); }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }

    @Override
    public void eliminar(int idEncuesta) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement("DELETE FROM encuesta WHERE id = ?");
            ps.setInt(1, idEncuesta);
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new DAOException("Encuesta no encontrada con ID " + idEncuesta);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al eliminar la encuesta", ex);
        } finally {
            try { if (ps != null) ps.close(); }
            catch (SQLException ex) { throw new DAOException("Error cerrando PreparedStatement", ex); }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }

    @Override
    public Encuesta buscar(int idEncuesta) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement("SELECT * FROM encuesta WHERE id = ?");
            ps.setInt(1, idEncuesta);
            rs = ps.executeQuery();
            if (!rs.next()) return null;

            String titulo = rs.getString("titulo");
            LocalDate fecha = rs.getDate("fechaCreacion").toLocalDate();
            int admId = rs.getInt("idAdministrador");

            Encuesta e = new Encuesta(idEncuesta, titulo, fecha);
            e.setIdAdministrador(admId);

            // 1. Obtener preguntas
            List<Pregunta> preguntas = obtenerPreguntasPorEncuesta(idEncuesta); // Método a implementar
            e.setPreguntas(preguntas);

            // 2. Para cada pregunta, obtener respuestas posibles
            for (Pregunta p : preguntas) {
                List<Respuesta> respuestas = obtenerRespuestasPorPregunta(p.getIdPregunta()); // Método a implementar
                p.setRespuestas((ArrayList<Respuesta>) respuestas);
            }

            return e;

        } catch (SQLException ex) {
            throw new DAOException("Error al buscar la encuesta", ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                throw new DAOException("Error cerrando recursos", ex);
            }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }


    @Override
    public List<Encuesta> buscarTodas() throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Encuesta> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement("SELECT * FROM encuesta");
            rs = ps.executeQuery();
            while (rs.next()) {
                int id       = rs.getInt("id");
                String titulo= rs.getString("titulo");
                LocalDate fecha = rs.getDate("fechaCreacion").toLocalDate();
                Encuesta e   = new Encuesta(id, titulo, fecha);
                e.setIdAdministrador(rs.getInt("idAdministrador"));
                lista.add(e);
            }
            return lista;
        } catch (SQLException ex) {
            throw new DAOException("Error al listar encuestas", ex);
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); }
            catch (SQLException ex) { throw new DAOException("Error cerrando recursos", ex); }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }

    @Override
    public boolean tieneRespuestasAsociadas(int idEncuesta) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getConexion();
            String sql = "SELECT COUNT(*) FROM respuestausuarios where idencuesta  = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idEncuesta);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException ex) {
            throw new DAOException("Error al verificar respuestas asociadas", ex);
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); }
            catch (SQLException ex) { throw new DAOException("Error cerrando recursos", ex); }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }
    @Override
    public List<Encuesta> buscarPorFecha(LocalDate desde, LocalDate hasta) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Encuesta> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement("SELECT * FROM encuesta WHERE fechaCreacion BETWEEN ? AND ?");
            ps.setDate(1, Date.valueOf(desde));
            ps.setDate(2, Date.valueOf(hasta));
            rs = ps.executeQuery();
            while (rs.next()) {
                int id       = rs.getInt("id");
                String titulo= rs.getString("titulo");
                LocalDate fecha = rs.getDate("fechaCreacion").toLocalDate();
                Encuesta e   = new Encuesta(id, titulo, fecha);
                e.setIdAdministrador(rs.getInt("idAdministrador"));
                lista.add(e);
            }
            return lista;
        } catch (SQLException ex) {
            throw new DAOException("Error al buscar encuestas por fecha", ex);
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); }
            catch (SQLException ex) { throw new DAOException("Error cerrando recursos", ex); }
            if (con != null) Conexion.cerrarConexion(con);
        }
    }



    @Override
    public List<ResumenRespuestaDTO> obtenerResumenEncuesta(int idEncuesta, LocalDate desde, LocalDate hasta) throws DAOException {
        List<ResumenRespuestaDTO> resumen = new ArrayList<>();
        String sql = "SELECT e.id AS ID, " +
                "       e.titulo AS tituloEncuesta, " +
                "       p.descripcion AS pregunta, " +
                "       r.valor AS valorAsignado, " +
                "       r.descripcion AS respuesta, " +
                "       COUNT(ru.idRespuesta) AS cantidad " +
                "FROM encuesta e " +
                "JOIN pregunta p ON p.idEncuesta = e.id " +
                "JOIN respuestasposibles r ON p.id = r.idPregunta " +
                "JOIN respuestausuarios ru ON r.id = ru.idRespuesta AND ru.fechaRealizacion BETWEEN ? AND ? " +
                "WHERE e.id = ? " +
                "GROUP BY e.id, e.titulo, p.descripcion, r.valor, r.descripcion " +
                "ORDER BY e.id, p.descripcion, r.valor";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(desde));
            ps.setDate(2, java.sql.Date.valueOf(hasta));
            ps.setInt(3, idEncuesta);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String titulo = rs.getString("tituloEncuesta");
                    String pregunta = rs.getString("pregunta");
                    String respuesta = rs.getString("respuesta");
                    int valorAsignado = rs.getInt("valorAsignado");
                    int cantidad = rs.getInt("cantidad");

                    resumen.add(new ResumenRespuestaDTO(id, titulo, pregunta, respuesta, valorAsignado, cantidad));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al obtener resumen de la encuesta", ex);
        }

        return resumen;
    }

    @Override
    public List<ResumenRespuestaDTO> obtenerResumenTodasEncuestas(LocalDate desde, LocalDate hasta) throws DAOException {
        List<ResumenRespuestaDTO> resumen = new ArrayList<>();

        String sql = "SELECT e.id AS idEncuesta, " +
                "e.titulo AS tituloEncuesta, " +
                "p.descripcion AS pregunta, " +
                "r.valor AS valorAsignado, " +
                "r.descripcion AS respuesta, " +
                "COUNT(ru.idRespuesta) AS cantidad " +
                "FROM encuesta e " +
                "JOIN pregunta p ON p.idEncuesta = e.id " +
                "JOIN respuestasposibles r ON p.id = r.idPregunta " +
                "JOIN respuestausuarios ru ON r.id = ru.idRespuesta AND ru.fechaRealizacion BETWEEN ? AND ? " +
                "GROUP BY e.id, e.titulo, p.descripcion, r.valor, r.descripcion " +
                "ORDER BY e.id, p.descripcion, r.valor";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(desde));
            ps.setDate(2, Date.valueOf(hasta));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ResumenRespuestaDTO dto = new ResumenRespuestaDTO(
                            rs.getInt("idEncuesta"),
                            rs.getString("tituloEncuesta"),
                            rs.getString("pregunta"),
                            rs.getString("respuesta"),
                            rs.getInt("valorAsignado"),
                            rs.getInt("cantidad")
                    );
                    resumen.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener resumen de todas las encuestas", e);
        }

        return resumen;
    }
    @Override
    public List<Pregunta> obtenerPreguntasPorEncuesta(int idEncuesta) throws DAOException {
        List<Pregunta> preguntas = new ArrayList<>();
        String sql = "SELECT id, descripcion FROM pregunta WHERE idEncuesta = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEncuesta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pregunta p = new Pregunta();
                    p.setIdPregunta(rs.getInt("id"));
                    p.setPregunta(rs.getString("descripcion"));
                    preguntas.add(p);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener preguntas por encuesta", e);
        }
        return preguntas;
    }

    @Override
    public List<Respuesta> obtenerRespuestasPorPregunta(int idPregunta) throws DAOException {
        List<Respuesta> respuestas = new ArrayList<>();
        String sql = "SELECT id, descripcion, valor FROM respuestasposibles WHERE idPregunta = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPregunta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Respuesta r = new Respuesta();
                    r.setIdOpcion(rs.getInt("id"));
                    r.setDescripcion(rs.getString("descripcion"));
                    r.setValor(rs.getInt("valor"));
                    respuestas.add(r);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener respuestas por pregunta", e);
        }
        return respuestas;
    }

    @Override
    public List<Encuesta> listarEncuestasPorAdministrador(int idAdministrador)  throws DAOException {
        List<Encuesta> encuestas = new ArrayList<>();
        String sql = "SELECT * FROM encuesta WHERE idAdministrador = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAdministrador);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Encuesta e = new Encuesta();
                    e.setIdEncuesta(rs.getInt("id"));
                    e.setTitulo(rs.getString("titulo"));
                    e.setFechaDeCreacion(rs.getDate("fechaCreacion").toLocalDate());
                    e.setIdAdministrador(idAdministrador);
                    encuestas.add(e);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar encuestas por administrador", e);
        }
        return encuestas;
    }

    @Override
    public List<Encuesta> obtenerUltimasEncuestas(int cantidad) throws DAOException {
        List<Encuesta> encuestas = new ArrayList<>();
        String sql = "SELECT * FROM encuesta ORDER BY fechaCreacion DESC LIMIT ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cantidad);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Encuesta e = new Encuesta();
                    e.setIdEncuesta(rs.getInt("id"));
                    e.setTitulo(rs.getString("titulo"));
                    e.setFechaDeCreacion(rs.getDate("fechaCreacion").toLocalDate());
                    e.setIdAdministrador(rs.getInt("idAdministrador"));
                    encuestas.add(e);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener las últimas encuestas", e);
        }
        return encuestas;
    }

}
