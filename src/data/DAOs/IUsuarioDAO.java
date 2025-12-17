package data.DAOs;
import data.Model.Usuario;
import java.util.ArrayList;


public  interface IUsuarioDAO {
    public void crear(Usuario usuario) throws DAOException ;
    public void modificar(Usuario usuario) throws DAOException ;
    public void eliminar(int id) throws DAOException ;
    public Usuario buscar(int id) throws DAOException ;
    public ArrayList buscarTodos() throws DAOException ;

}
