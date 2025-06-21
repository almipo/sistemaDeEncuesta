package data.DAOs;


import data.Model.Rol;

import data.conectionBd.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RolUsuarioDAO {
    private int id;
    private String descripcion;

    public RolUsuarioDAO(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public RolUsuarioDAO() {


    }


    public void crear(Rol rol) {
        Connection con = null;
        PreparedStatement pStmt = null;
        try {
            con = Conexion.getConexion();
            pStmt = con.prepareStatement("INSERT INTO rolUsuario (descripcion) VALUES (?)");
            pStmt.setString(1,rol.getRol());

            int filasAfectadas = pStmt.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet generatedKeys = pStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idGenerado = generatedKeys.getInt(1);
                    this.id = idGenerado;

                }
                System.out.println("Rol creado correctamente.");
            } else {
                System.out.println("Error al crear el rol.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear el rol", e);
        } finally {
            try {
                if (pStmt != null) {
                    pStmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar el PreparedStatement");
                e.printStackTrace();
            }
            if (con != null) {
                Conexion.cerrarConexion(con);
            }
        }
    }

    public static Rol buscarPorNombre(String nombre){
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConexion();
            pStmt = con.prepareStatement("SELECT * FROM rolusuario WHERE descripcion = ?");
            pStmt.setString(1, nombre);
            rs = pStmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String descripcion = rs.getString("descripcion");
                Rol rol = new Rol(id, descripcion);
                System.out.println("ID: " + rol.getId() + ", Descripción: " + rol.getRol());
                return rol;
            } else {
                System.out.println("No se encontró el rol con descripción: " + nombre);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el rol: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pStmt != null) pStmt.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
            if (con != null) {
                Conexion.cerrarConexion(con);
            }
        }

        return null;
    }

    public void buscarTodosLosRoles(){
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        ArrayList<Rol> roles = new ArrayList<>();

        try {
            con = Conexion.getConexion();
            pStmt = con.prepareStatement("SELECT * FROM rolusuario");
            rs = pStmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String descripcion = rs.getString("descripcion");
                Rol rol = new Rol(id, descripcion);
                roles.add(rol);
            }

            for (Rol rol : roles) {
                System.out.println("ID: " + rol.getId() + ", Descripción: " + rol.getRol());
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar los roles: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pStmt != null) pStmt.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
            if (con != null) {
                Conexion.cerrarConexion(con);
            }
        }

    }

    public void eliminarRol(int id) {
        Connection con = null;
        PreparedStatement pStmt = null;

        try {
            con = Conexion.getConexion();
            pStmt = con.prepareStatement("DELETE FROM rol WHERE id = ?");
            pStmt.setInt(1, id);

            int filasAfectadas = pStmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Rol eliminado correctamente.");
            } else {
                System.out.println("No se encontró el rol con ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el rol: " + e.getMessage());
        } finally {
            try {
                if (pStmt != null) pStmt.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar el PreparedStatement: " + e.getMessage());
            }
            if (con != null) {
                Conexion.cerrarConexion(con);
            }
        }
    }


    public void modificarRol(int id){
        Connection con = null;
        PreparedStatement pStmt = null;
        try {
            con = Conexion.getConexion();
            pStmt = con.prepareStatement("UPDATE rol SET descripcion = ? WHERE id = ?");
            pStmt.setString(1, this.descripcion);
            pStmt.setInt(2, id);

            int filasAfectadas = pStmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Rol modificado correctamente.");
            } else {
                System.out.println("No se encontró el rol con ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el rol: " + e.getMessage());
        } finally {
            try {
                if (pStmt != null) pStmt.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar el PreparedStatement: " + e.getMessage());
            }
            if (con != null) {
                Conexion.cerrarConexion(con);
            }
        }

    }

}
