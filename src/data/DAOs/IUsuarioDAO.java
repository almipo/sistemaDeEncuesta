package data.DAOs;

import data.Model.Administrador;
import data.Model.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;


public  interface IUsuarioDAO {
    public void crear(Usuario usuario) throws SQLException ;


    public void modificar(Usuario usuario) ;
    public void eliminar(int id) ;
    public Usuario buscar(int id);
    public ArrayList buscarTodos();
}
