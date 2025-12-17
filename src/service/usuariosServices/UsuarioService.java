package service.usuariosServices;

import data.DAOs.DAOException;
import data.DAOs.IUsuarioDAO;
import data.Model.Promotor;
import data.Model.Rol;
import data.Model.RolUsuario;
import data.Model.Usuario;
import service.IUsuarioService;
import service.ServiceException;

import java.security.Provider;
import java.util.ArrayList;

public class UsuarioService implements IUsuarioService {

    private AdministradorService administradorService;
    private PromotorService promotorService;

    public UsuarioService() {
        this.administradorService = new AdministradorService();
        this.promotorService = new PromotorService();
    }
    @Override

    public void crearUsuario(String nombre, String apellido, String dni, String rol) throws ServiceException {
        try {
            if ("Administrador".equals(rol)) {
                administradorService.crearAdministrador(nombre, apellido, dni);
            } else if ("Promotor".equals(rol)) {
                promotorService.crearPromotor(nombre, apellido, dni);
            } else {
                throw new ServiceException("Rol no soportado: " + rol);
            }
        } catch (DAOException e) {
            throw new ServiceException("Error al crear usuario en la base de datos.", e);
        } catch (NumberFormatException e) {
            throw new ServiceException("El DNI debe ser un número válido.", e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al crear usuario.", e);
        }
    }

    @Override
    public Usuario buscarUsuarioPorId(int id) throws ServiceException {
        try {
            Usuario usuario = administradorService.buscarAdministradorPorId(id);
            if (usuario != null) return usuario;

            usuario = promotorService.buscarPromotorPorId(id);
            if (usuario != null) return usuario;

            throw new ServiceException("No se encontró un usuario con el ID: " + id);
        } catch (DAOException e) {
            e.printStackTrace();
            //throw e;
            throw new ServiceException("Error al buscar usuario en la base de datos.", e);
        } catch (Exception e) {
            e.printStackTrace();
            //throw e;
            throw new ServiceException("Ocurrió un error inesperado al buscar usuario .", e);
        }
    }


    @Override
    public Boolean modificarUsuario(int id, String nombre, String apellido, String dni, String rol) throws ServiceException {
        try {
            if ("Administrador".equals(rol)) {
                administradorService.modificarAdministrador(id, nombre, apellido, dni);
            } else if ("Promotor".equals(rol)) {
                promotorService.modificarPromotor(id, nombre, apellido, dni);
            } else {
                throw new ServiceException("Rol no soportado: " + rol);
            }
            return true;
        } catch (DAOException e) {
            throw new ServiceException("Error al modificar usuario en la base de datos.", e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al modificar usuario.", e);
        }
    }


    @Override
    public boolean eliminarUsuario(int id, String rol) throws ServiceException {
        try {
            if ("Administrador".equals(rol)) {
                administradorService.eliminarAdministrador(id);
            } else if ("Promotor".equals(rol)) {
                promotorService.eliminarPromotor(id);
            } else {
                throw new ServiceException("Rol no soportado: " + rol);
            }
            return true;
        } catch (DAOException e) {
            throw new ServiceException("Error al eliminar usuario desde la base de datos.", e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al eliminar usuario.", e);
        }
    }

    @Override
    public ArrayList<Usuario> listarUsuarios(String rol) throws ServiceException {
        try {
            ArrayList<Usuario> usuarios = new ArrayList<>();
            if ("Todos".equals(rol) || "Administrador".equals(rol)) {
                usuarios.addAll(administradorService.listarAdministradores());
            }
            if ("Todos".equals(rol) || "Promotor".equals(rol)) {
                usuarios.addAll(promotorService.listarPromotores());
            }
            return usuarios;
        } catch (DAOException e) {
            throw new ServiceException("Error al listar usuarios desde la base de datos.", e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al listar usuarios.", e);
        }
    }


    @Override
    public RolUsuario obtenerRol(int idUsuario) throws ServiceException {
        try {
            if (administradorService.buscarAdministradorPorId(idUsuario) != null) {
                return RolUsuario.ADMINISTRADOR;
            }
            if (promotorService.buscarPromotorPorId(idUsuario) != null) {
                return RolUsuario.PROMOTOR;
            }
            return RolUsuario.DESCONOCIDO;
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener el rol del usuario", e);
        }
    }
}
