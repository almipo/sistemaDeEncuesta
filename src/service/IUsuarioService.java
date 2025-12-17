package service;
import java.util.ArrayList;

import data.Model.RolUsuario;
import data.Model.Usuario;

public interface IUsuarioService {
public void crearUsuario(String nombre, String apellido, String dni, String rol) throws ServiceException;
public Usuario buscarUsuarioPorId(int id) throws ServiceException;
public Boolean modificarUsuario(int id, String nombre, String apellido, String dni, String rol) throws ServiceException;
public boolean eliminarUsuario(int id, String rol) throws ServiceException;
public ArrayList<Usuario> listarUsuarios(String rol) throws ServiceException;
public RolUsuario obtenerRol(int id) throws ServiceException;


}
