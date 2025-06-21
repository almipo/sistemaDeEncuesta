package service;

import data.DAOs.AdministradorDAO;
import data.Model.Administrador;
import data.Model.Rol;
import data.Model.Usuario;
import java.util.ArrayList;

public class AdministradorService {

    private AdministradorDAO administradorDAO;

    public AdministradorService() {
        this.administradorDAO = new AdministradorDAO();
    }

    public void crearAdministrador(String nombre, String apellido, String dniStr) {
        try {
            int dni = Integer.parseInt(dniStr);
            Rol rol = new Rol(1, "Administrador");
            Administrador admin = new Administrador(nombre, apellido, dni, rol);
            administradorDAO.crear(admin);
        } catch (NumberFormatException e) {
            throw new RuntimeException("El DNI debe ser un número.");
        }
    }

    public Administrador buscarAdministradorPorId(int idStr) {
        try {
            int id =idStr;
            return (Administrador) administradorDAO.buscar(id);
        } catch (NumberFormatException e) {
            throw new RuntimeException("El ID debe ser un número.");
        }
    }

    public void modificarAdministrador(int idStr, String nombre, String apellido, String dniStr) {
        try {
            int id = idStr;
            int dni = Integer.parseInt(dniStr);
            Rol rol = new Rol(1, "Administrador");
            Administrador admin = new Administrador(id, nombre, apellido, dni, rol);
            administradorDAO.modificar(admin);
        } catch (NumberFormatException e) {
            throw new RuntimeException("ID y DNI deben ser números.");
        }
    }

    public void eliminarAdministrador(int id) {
        try {

            administradorDAO.eliminar(id);
        } catch (NumberFormatException e) {
            throw new RuntimeException("El ID debe ser un número.");
        }
    }

    public ArrayList<Administrador> listarAdministradores() {
        return administradorDAO.buscarTodos();
    }
}
