package data.Model;



import java.time.LocalDate;

public class RespuestaUsuario {
    private int idRespuesta;
    private LocalDate fechaRealizacion;
    private int usuarioId;
    private int encuestaId;
    private int preguntaId;
    private Integer respuestaId;
    private String respuestaLibre;

    public RespuestaUsuario() { }


    public RespuestaUsuario(LocalDate fecha, int usuarioId, int encuestaId, int preguntaId, Integer respuestaId) {
        this.fechaRealizacion = fecha;
        this.usuarioId        = usuarioId;
        this.encuestaId       = encuestaId;
        this.preguntaId       = preguntaId;
        this.respuestaId      = respuestaId;
    }


    public RespuestaUsuario(LocalDate fecha, int usuarioId, int encuestaId, int preguntaId, String textoLibre) {
        this(fecha, usuarioId, encuestaId, preguntaId, 0);
        this.respuestaLibre = textoLibre;
    }

    public int getEncuestaId() {
        return encuestaId;
    }

    public void setEncuestaId(int encuestaId) {
        this.encuestaId = encuestaId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getRespuestaLibre() {
        return respuestaLibre;
    }

    public void setRespuestaLibre(String respuestaLibre) {
        this.respuestaLibre = respuestaLibre;
    }

    public Integer getRespuestaId() {
        return respuestaId;
    }

    public void setRespuestaId(Integer respuestaId) {
        this.respuestaId = respuestaId;
    }

    public int getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(int preguntaId) {
        this.preguntaId = preguntaId;
    }

    public LocalDate getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(LocalDate fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }
}
