package data.Model;

public class Promotor extends Usuario {


    public Promotor() {
    }

    public Promotor(int id, String nombre, String apellido, int dni, Rol rol) {
        super(id, nombre, apellido, dni, rol);
    }

    public Promotor(String nombre, String apellido, int dni, Rol rol) {
        super(nombre, apellido, dni, rol);
    }
}
