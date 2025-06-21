package service;

import data.Model.Usuario;
import java.util.ArrayList;

public class UsuarioService {

    private AdministradorService administradorService;
    private PromotorService promotorService;

    public UsuarioService() {
        this.administradorService = new AdministradorService();
        this.promotorService = new PromotorService();
    }

    public void crearUsuario(String nombre, String apellido, String dni, String rol) throws ServiceException {
        if ("Administrador".equals(rol)) {
            administradorService.crearAdministrador(nombre, apellido, dni);
        } else if ("Promotor".equals(rol)) {
            promotorService.crearPromotor(nombre, apellido, dni);
        } else {
            throw new ServiceException("Rol no soportado: " + rol);
        }
    }

    public Usuario buscarUsuarioPorId(int id) {
        Usuario usuario = administradorService.buscarAdministradorPorId(id);
        if (usuario != null) return usuario;

        usuario = promotorService.buscarPromotorPorId(id);
        return usuario;
    }

    public Boolean modificarUsuario(int id, String nombre, String apellido, String dni, String rol) throws ServiceException {
        if ("Administrador".equals(rol)) {
            administradorService.modificarAdministrador(id, nombre, apellido, dni);
        } else if ("Promotor".equals(rol)) {
            promotorService.modificarPromotor(id, nombre, apellido, dni);
        } else {
            throw new ServiceException("Rol no soportado: " + rol);
        }
        return true;
    }

    public boolean eliminarUsuario(int id, String rol) throws ServiceException {
        if ("Administrador".equals(rol)) {
            administradorService.eliminarAdministrador(id);
        } else if ("Promotor".equals(rol)) {
            promotorService.eliminarPromotor(id);
        } else {
            throw new ServiceException("Rol no soportado: " + rol);
        }
        return true;
    }

    public ArrayList<Usuario> listarUsuarios(String rol) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        if ("Todos".equals(rol) || "Administrador".equals(rol)) {
            usuarios.addAll(administradorService.listarAdministradores());
        }
        if ("Todos".equals(rol) || "Promotor".equals(rol)) {
            usuarios.addAll(promotorService.listarPromotores());
        }
        return usuarios;
    }
}
