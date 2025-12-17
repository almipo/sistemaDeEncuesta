package data.Model;

public final class ResumenRespuestaDTO {
    private int encuestaId;
    private String titulo;
    private String pregunta;
    private String respuesta;
    private int valorAsignado;
    private int cantidad;



    public ResumenRespuestaDTO(int encuestaId, String titulo, String pregunta, String respuesta, int valorAsignado, int cantidad) {
        this.encuestaId = encuestaId;
        this.titulo = titulo;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.valorAsignado = valorAsignado;
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getValorAsignado() {
        return valorAsignado;
    }

    public void setValorAsignado(int valorAsignado) {
        this.valorAsignado = valorAsignado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public int getEncuestaId() {
        return encuestaId;
    }

    public void setEncuestaId(int encuestaId) {
        this.encuestaId = encuestaId;
    }
}