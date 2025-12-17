package data.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Encuesta {
   private int idEncuesta;
   private int idAdministrador;
   private String titulo;
   private LocalDate fechaDeCreacion;
   private List<Pregunta> preguntas = new ArrayList<>();

   public Encuesta(){}

    public Encuesta(int idEncuesta, String titulo, LocalDate fechaDeCreacion) {
        this.idEncuesta = idEncuesta;
        this.titulo = titulo;
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public Encuesta(int idEncuesta, String titulo, int idAdministrador, LocalDate fechaDeCreacion) {
        this.idEncuesta = idEncuesta;
        this.titulo = titulo;
        this.idAdministrador= idAdministrador;
        this.fechaDeCreacion = fechaDeCreacion;
    }
    public Encuesta(String titulo, int idAdministrador) {
        this.titulo = titulo;
        this.idAdministrador = idAdministrador;
    }


    public LocalDate getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(LocalDate fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = (preguntas != null) ? preguntas : new ArrayList<>();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Encuesta ID: ").append(idEncuesta).append("\n");
        sb.append("Título: ").append(titulo).append("\n");
        sb.append("Preguntas:\n");

        if (preguntas != null) {
            for (Pregunta p : preguntas) {
                sb.append(p.toString()).append("\n");
            }
        } else {
            sb.append("No hay preguntas.\n");
        }

        return sb.toString();
    }


}
