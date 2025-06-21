package service;

import data.DAOs.PromotorDAO;
import data.Model.Promotor;
import data.Model.Rol;
import java.util.ArrayList;

public class PromotorService {

    private PromotorDAO promotorDAO;

    public PromotorService() {
        this.promotorDAO = new PromotorDAO();
    }

    public void crearPromotor(String nombre, String apellido, String dniStr) {
        try {
            int dni = Integer.parseInt(dniStr);
            Rol rol = new Rol(2, "Promotor"); // ID 2 asumido para Promotor
            Promotor promotor = new Promotor(nombre, apellido, dni, rol);
            promotorDAO.crear(promotor);
        } catch (NumberFormatException e) {
            throw new RuntimeException("El DNI debe ser un número.");
        }
    }

    public Promotor buscarPromotorPorId(int id) {
        return (Promotor) promotorDAO.buscar(id);
    }

    public void modificarPromotor(int id, String nombre, String apellido, String dniStr) {
        try {
            int dni = Integer.parseInt(dniStr);
            Rol rol = new Rol(2, "Promotor");
            Promotor promotor = new Promotor(id, nombre, apellido, dni, rol);
            promotorDAO.modificar(promotor);
        } catch (NumberFormatException e) {
            throw new RuntimeException("ID y DNI deben ser números.");
        }
    }

    public void eliminarPromotor(int id) {
        promotorDAO.eliminar(id);
    }

    public ArrayList<Promotor> listarPromotores() {
        return promotorDAO.buscarTodos();
    }
}
