package data.Model;

public class Rol {
    private int id;
    private String rol;



    public Rol() {
    }

    public Rol(int id, String rol) {
        this.id = id;
        this.rol = rol;
    }

    public Rol(String rol) {
        this.rol = rol;
    }



    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setId(int idGenerado) {
    }

    public int getId() {
        return id;
    }

public String toString() {
        return rol;

    }

    public void setId(int id, String rol) {
        this.id = id;
        this.rol = rol;
    }
}
