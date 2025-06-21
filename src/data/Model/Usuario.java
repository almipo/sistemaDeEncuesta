package data.Model;

public abstract class Usuario {

    private int id;
    private String nombre;
    private String apellido;
    private int dni;
    private Rol rol;


    public Usuario(){}

    public Usuario(String nombre, String apellido, int dni, Rol rol) {
        this.nombre = nombre;
        this.rol = rol;
        this.apellido = apellido;
        this.dni = dni;
    }
    public Usuario(String nombre, String apellido, int dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
    public Usuario(int id, String nombre, String apellido, int dni, Rol rol) {
        this.nombre = nombre;
        this.rol = rol;
        this.apellido = apellido;
        this.id = id;
        this.dni = dni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }



    @Override
    public String toString() {
        return "{" +
                "id=" + getId() +
                ", nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", rol=" + getRol().getRol() +
                '}';
    }
}
