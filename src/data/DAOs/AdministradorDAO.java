package data.DAOs;


import data.Model.Rol;
import data.Model.Usuario;
import data.Model.Administrador;

import java.sql.*;
import java.util.ArrayList;
import data.conectionBd.Conexion;
import java.sql.Connection;

public class AdministradorDAO implements IUsuarioDAO {


    public void verificarAdministrador(Usuario usuario){
        if(!(usuario instanceof Administrador) ) {
            throw new IllegalArgumentException("El usuario debe ser una instancia de Administrador");
        }

    }

    @Override
    public  void crear(Usuario usuario) {

            verificarAdministrador(usuario);
        Connection con= null;
        PreparedStatement pStmt = null;



        try {
            con = Conexion.getConexion();
            pStmt = con.prepareStatement("INSERT INTO usuario (nombre, apellido, dni, idrol) VALUES (?, ?, ?, ?)");
            pStmt.setString(1, usuario.getNombre());
            pStmt.setString(2, usuario.getApellido());
            pStmt.setInt(3, usuario.getDni());
            pStmt.setInt(4, usuario.getRol().getId());

             pStmt.executeUpdate();
            System.out.println("Administrador creado correctamente.");


        } catch (SQLException e) {
            throw new DAOException("Error al crear el administrador", e);
        } finally {
            try {
                if (pStmt != null) {
                    pStmt.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Error al cerrar el PreparedStatement", e);
            }
            if(con!= null) {
                Conexion.cerrarConexion(con);
            }
        }

    }

    @Override
    public void modificar(Usuario usuario) {
            verificarAdministrador(usuario);
        Connection con = null;
        PreparedStatement pStmt = null;



            try {
                con = Conexion.getConexion();

                pStmt = con.prepareStatement("UPDATE usuario SET nombre = ?, apellido = ?, dni = ? WHERE id = ? AND idrol = (SELECT id FROM rolUsuario WHERE descripcion = 'Administrador')");
                pStmt.setString(1, usuario.getNombre());
                pStmt.setString(2, usuario.getApellido());
                pStmt.setInt(3, usuario.getDni());
                pStmt.setInt(4, usuario.getId());


                int filasAfectadas = pStmt.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Administrador modificado correctamente.");
                } else {
                    System.out.println("No se encontró un administrador con el ID especificado.");
                }
            } catch (SQLException e) {
                throw new DAOException("Error al modificar el administrador", e);
            } finally {
                try {
                    if (pStmt != null) {
                        pStmt.close();
                    }
                } catch (SQLException e) {

                    throw new DAOException("Error al cerrar el PreparedStatement", e);
                }
                if (con != null) {
                    Conexion.cerrarConexion(con);
                }
            }

    }


    @Override
    public void eliminar(int id){
            Connection con = null;
            PreparedStatement pStmt = null;
            PreparedStatement pStmtID = null;
            try {
                con = Conexion.getConexion();
                pStmt = con.prepareStatement("DELETE FROM usuario WHERE id = ? AND idrol = (select id from rolUsuario where descripcion = 'Administrador')");
                pStmt.setInt(1, id);


                int filasAfectadas = pStmt.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("Administrador eliminado correctamente.");
                } else {
                    System.out.println("No se encontró un administrador con el ID especificado.");
                }
            } catch (SQLException e) {
                throw new DAOException("Error al eliminar el administrador", e);
            } finally {
                try {
                    if (pStmt != null) {
                        pStmt.close();
                    }
                } catch (SQLException e) {
                    throw new DAOException("Error al cerrar el PreparedStatement", e);
                }
                if (con != null) {
                    Conexion.cerrarConexion(con);
                }
            }


    }





    @Override
    public Usuario buscar(int idBuscado)  {
        Connection con = null;
        PreparedStatement pStmt = null;

        try {
            con = Conexion.getConexion();

            pStmt = con.prepareStatement("SELECT * FROM usuario WHERE id = ? and idrol = (SELECT id FROM rolusuario WHERE descripcion = 'Administrador')");
            pStmt.setInt(1, idBuscado);
            ResultSet rs = pStmt.executeQuery();

           if(rs.next()) {

                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int dni = rs.getInt("dni");
                int idRol = rs.getInt("idrol");
                Rol rol = new Rol(idRol, "Administrador");



                Administrador administrador = new Administrador(id, nombre, apellido, dni, rol);
               try{

                   if (rs != null) {
                       rs.close();
                   }
               } catch (SQLException e) {

                   e.printStackTrace();
                   throw new DAOException("Error al cerrar el ResultSet",e);
               }
                return administrador;
            }
           else{
               return null;
           }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar el administrador", e);
        } finally {
            try {
                if (pStmt != null) {
                    pStmt.close();
                }
            } catch (SQLException e) {

                throw new DAOException("Error al cerrar el PreparedStatement",e );
            }

            if(con!= null) {
                Conexion.cerrarConexion(con);
            }}
    }







    @Override
    public  ArrayList<Administrador> buscarTodos() {
        Connection con = null;
        PreparedStatement pStmt = null;
        ArrayList<Administrador> administradores = new ArrayList<>();

        try {
            con = Conexion.getConexion();
            pStmt = con.prepareStatement("SELECT * FROM usuario WHERE  idrol = (SELECT id FROM rolusuario WHERE descripcion = 'Administrador')");
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int dni = rs.getInt("dni");
                int idrol = rs.getInt("idrol");
                Rol rol = new Rol(idrol, "Administrador");

                Administrador administrador = new Administrador(id, nombre, apellido, dni, rol);

                administradores.add(administrador);

                System.out.println("Administrador encontrado: " + nombre + " " + apellido);
            }

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {

                throw new DAOException("Error al cerrar el ResultSet");
            }
            return administradores;
        }
        catch (SQLException e) {
            throw new DAOException("Error al buscar administradores", e);
        } finally {
            try {
                if (pStmt != null) {
                    pStmt.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Error al cerrar el PreparedStatement");
            }

            if (con != null) {
                Conexion.cerrarConexion(con);
            }
        }

    }



}