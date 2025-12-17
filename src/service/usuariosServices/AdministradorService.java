package service.usuariosServices;

import data.DAOs.DAOException;
import data.DAOs.usuariosDAO.AdministradorDAO;
import data.DAOs.IUsuarioDAO;
import data.Model.Administrador;
import data.Model.Rol;

import service.ServiceException;

import java.util.ArrayList;

public class AdministradorService  {

    private IUsuarioDAO administradorDAO;

    public AdministradorService() {
        this.administradorDAO = new AdministradorDAO();
    }


    public void crearAdministrador(String nombre, String apellido, String dniStr) throws ServiceException {
        try {
            int dni = Integer.parseInt(dniStr);
            Rol rol = new Rol(1, "Administrador");
            Administrador admin = new Administrador(nombre, apellido, dni, rol);
            administradorDAO.crear(admin);
        } catch (NumberFormatException e) {
            throw new ServiceException("El DNI debe ser un número.");
        } catch (DAOException e) {
            throw new ServiceException("Error al crear el administrador.", e);
        }
    }

    public Administrador buscarAdministradorPorId(int id) throws ServiceException {
        try {
            return (Administrador) administradorDAO.buscar(id);
        } catch (DAOException e) {
            e.printStackTrace();
            //throw e;
            throw new ServiceException("Error al buscar el administrador."+ id , e);
        }
    }


    public void modificarAdministrador(int idStr, String nombre, String apellido, String dniStr) throws ServiceException {
        try {
            int dni = Integer.parseInt(dniStr);
            Rol rol = new Rol(1, "Administrador");
            Administrador admin = new Administrador(idStr, nombre, apellido, dni, rol);
            administradorDAO.modificar(admin);
        } catch (NumberFormatException e) {
            throw new ServiceException("El DNI debe ser un número.");
        } catch (DAOException e) {
            throw new ServiceException("Error al modificar el administrador.", e);
        }
    }


    public void eliminarAdministrador(int id) throws ServiceException {
        try {
            administradorDAO.eliminar(id);
        } catch (DAOException e) {
            throw new ServiceException("Error al eliminar el administrador con ID: " + id, e);
        }
    }


    public ArrayList<Administrador> listarAdministradores() throws ServiceException {
        try {
            return administradorDAO.buscarTodos();
        } catch (DAOException e) {
            throw new ServiceException("Error al listar administradores.", e);
        }
    }

}
