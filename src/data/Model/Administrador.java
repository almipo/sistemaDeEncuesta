package data.Model;

public class Administrador extends Usuario {



    public Administrador (int id, String nombre, String apellido, int dni, Rol rol) {
        super(id, nombre, apellido, dni, rol);
    }
    public Administrador ( String nombre, String apellido, int dni, Rol rol) {
        super( nombre, apellido, dni, rol);
    }
    public Administrador(){

    }
    public Administrador ( String nombre, String apellido, int dni) {
        super( nombre, apellido, dni);
    }
    }









