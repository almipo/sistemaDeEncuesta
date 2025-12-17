package data.DAOs.usuariosDAO;



import data.DAOs.DAOException;
import data.DAOs.IUsuarioDAO;
import data.Model.Promotor;
import data.Model.Rol;
import data.Model.Usuario;
import data.conectionBd.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PromotorDAO implements IUsuarioDAO {


    public void verificarPromotor(Usuario usuario){
        if(!(usuario instanceof Promotor) ) {
            throw new IllegalArgumentException("El usuario debe ser una instancia de Promotor");
        }

    }

    @Override
    public  void crear(Usuario usuario) {

        verificarPromotor(usuario);
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
                System.out.println("Promotor creado correctamente.");


            } catch (SQLException e) {
                throw new DAOException("Error al crear el Promotor", e);
            } finally {
                try {
                    if (pStmt != null) {
                        pStmt.close();
                    }
                } catch (SQLException e) {
                    throw new DAOException("Error al cerrar el PreparedStatement",e);
                    }
                if(con!= null) {
                    Conexion.cerrarConexion(con);
                }
            }

    }


    @Override
    public void modificar(Usuario usuario) {
        Connection con = null;
        PreparedStatement pStmt = null;



            try {
                con = Conexion.getConexion();

                pStmt = con.prepareStatement("UPDATE usuario SET nombre = ?, apellido = ?, dni = ? WHERE id = ? AND idrol = (SELECT id FROM rolUsuario WHERE descripcion = 'Promotor')");
                pStmt.setString(1, usuario.getNombre());
                pStmt.setString(2, usuario.getApellido());
                pStmt.setInt(3, usuario.getDni());
                pStmt.setInt(4, usuario.getId());


                int filasAfectadas = pStmt.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Promotor modificado correctamente.");
                } else {
                    System.out.println("No se encontró un Promotor con el ID especificado.");
                }
            } catch (SQLException e) {
                throw new DAOException("Error al modificar el Promotor", e);
            } finally {
                try {
                    if (pStmt != null) {
                        pStmt.close();
                    }
                } catch (SQLException e) {
                    throw new DAOException("Error al cerrar el PreparedStatement",e);

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
            pStmt = con.prepareStatement("DELETE FROM usuario WHERE id = ? AND idrol = (select id from rolUsuario where descripcion = 'Promotor')");
            pStmt.setInt(1, id);


            int filasAfectadas = pStmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Promotor eliminado correctamente.");
            } else {
                System.out.println("No se encontró un promotor con el ID especificado.");
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

            pStmt = con.prepareStatement("SELECT * FROM usuario WHERE id = ? and idrol = (SELECT id FROM rolusuario WHERE descripcion = 'Promotor')");
            pStmt.setInt(1, idBuscado);
            ResultSet rs = pStmt.executeQuery();

            if(rs.next()) {

                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int dni = rs.getInt("dni");
                int idRol = rs.getInt("idrol");
                Rol rol = new Rol(idRol, "Promotor");



                Promotor promotor = new Promotor(id, nombre, apellido, dni, rol);
                try{

                    if (rs != null) {
                        rs.close();
                    }
                } catch (SQLException e) {

                    throw new DAOException("Error al cerrar el ResultSet",e);

                }
                return promotor;
            }
            else{
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar el promotor", e);
        } finally {
            try {
                if (pStmt != null) {
                    pStmt.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Error al cerrar el PreparedStatement",e);

            }

            if(con!= null) {
                Conexion.cerrarConexion(con);
            }}
    }







    @Override
    public  ArrayList<Promotor> buscarTodos() {
        Connection con = null;
        PreparedStatement pStmt = null;
        ArrayList<Promotor>promotores= new ArrayList<>();

        try {
            con = Conexion.getConexion();
            pStmt = con.prepareStatement("SELECT * FROM usuario WHERE  idrol = (SELECT id FROM rolusuario WHERE descripcion = 'Promotor')");
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int dni = rs.getInt("dni");
                int idrol = rs.getInt("idrol");
                Rol rol = new Rol(idrol, "Promotor");

                Promotor promotor= new Promotor(id, nombre, apellido, dni, rol);

                promotores.add(promotor);

            }

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {

                throw new DAOException("Error al cerrar el ResultSet",e);

            }
            return promotores;
        }
        catch (SQLException e) {
            throw new DAOException("Error al buscar Promotorores", e);
        } finally {
            try {
                if (pStmt != null) {
                    pStmt.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Error al cerrar el PreparedStatement",e);

            }

            if (con != null) {
                Conexion.cerrarConexion(con);
            }
        }

    }



}