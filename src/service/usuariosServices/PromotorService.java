package service.usuariosServices;

import data.DAOs.DAOException;
import data.DAOs.IUsuarioDAO;
import data.DAOs.usuariosDAO.PromotorDAO;
import data.Model.Administrador;
import data.Model.Promotor;
import data.Model.Rol;
import service.ServiceException;

import java.util.ArrayList;

public class PromotorService {

    private IUsuarioDAO promotorDAO;

    public PromotorService() {
        this.promotorDAO = new PromotorDAO();
    }

    public void crearPromotor(String nombre, String apellido, String dniStr)throws ServiceException    {
        try {
            int dni = Integer.parseInt(dniStr);
            Rol rol = new Rol(2, "Promotor");
            Promotor promotor = new Promotor(nombre, apellido, dni, rol);
            promotorDAO.crear(promotor);
        } catch (NumberFormatException e) {
            throw new ServiceException("El DNI debe ser un número.");
        } catch (DAOException e) {
            throw new ServiceException("Error al crear el promotor.", e);
        }
    }

    public Promotor buscarPromotorPorId(int id) throws ServiceException  {
        try {
            return (Promotor) promotorDAO.buscar(id);
        } catch (DAOException e) {
            e.printStackTrace();
            //throw e;
            throw new ServiceException("Error al buscar el Promotor."+ id , e);
        }
    }

    public void modificarPromotor(int idStr, String nombre, String apellido, String dniStr) throws ServiceException   {
        try {
            int dni = Integer.parseInt(dniStr);
            Rol rol = new Rol(2, "Promotor");
            Promotor promotor = new Promotor(idStr, nombre, apellido, dni, rol);
            promotorDAO.modificar(promotor);
        } catch (NumberFormatException e) {
            throw new ServiceException("El DNI debe ser un número.");
        } catch (DAOException e) {
            throw new ServiceException("Error al modificar el promoro.", e);
        }
    }

    public void eliminarPromotor(int id) throws ServiceException  {
        try {
            promotorDAO.eliminar(id);
        } catch (DAOException e) {
            throw new ServiceException("Error al eliminar el promotor con ID: " + id, e);
        }
    }

    public ArrayList<Promotor> listarPromotores() throws ServiceException  {
        try {
            return promotorDAO.buscarTodos();
        } catch (DAOException e) {
            throw new ServiceException("Error al listar promotores.", e);
        }
    }
}
