package data.Model;

public class Respuesta {
    private int idOpcion;
    private int preguntaId;
    private int valor;
    private String descripcion;

    public Respuesta(int idOpcion, String descripcion,int preguntaId) {
        this.idOpcion = idOpcion;
        this.descripcion = descripcion;
        this.preguntaId = preguntaId;
    }

    public Respuesta(){}

    public Respuesta( String descripcion, int preguntaId){
        this.preguntaId = preguntaId;
        this.descripcion = descripcion;
    }
    public Respuesta(int valor, String descripcion) {
        this.valor = valor;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(int idOpcion) {
        this.idOpcion = idOpcion;
    }

    public int getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(int preguntaId) {
        this.preguntaId = preguntaId;
    }
}
