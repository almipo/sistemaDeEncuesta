package presentation.paneles.administrador.panelesEncuesta;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import data.Model.Encuesta;
import service.IEncuestaService;
import service.IPreguntaCompuestaService;
import service.IPreguntaService;
import service.IOpcionRespuestaService;
import service.ServiceException;
import service.encuestaService.PreguntaCompuestaService;
import service.encuestaService.PreguntaService;
import service.encuestaService.OpcionRespuestaService;

public class PanelCrearEncuesta extends JPanel {

    private JTextField txtTitulo;
    private JButton btnCrear;
    private int idAdministrador;
    private IEncuestaService encuestaService;

    public PanelCrearEncuesta(int idAdministrador, IEncuestaService encuestaService) {
        this.idAdministrador = idAdministrador;
        this.encuestaService = encuestaService;
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Título de la Encuesta:");
        txtTitulo = new JTextField(25);
        btnCrear = new JButton("Crear Encuesta");

        JPanel panelForm = new JPanel();
        panelForm.add(lblTitulo);
        panelForm.add(txtTitulo);
        panelForm.add(btnCrear);

        add(panelForm, BorderLayout.CENTER);

        btnCrear.addActionListener(e -> crearEncuesta());
    }

    private void crearEncuesta() {
        String titulo = txtTitulo.getText().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título no puede estar vacío.");
            return;
        }

        try {
            // Crear el objeto Encuesta
            Encuesta encuesta = new Encuesta();
            encuesta.setTitulo(titulo);
            encuesta.setFechaDeCreacion(LocalDate.now());
            encuesta.setIdAdministrador(idAdministrador);
            System.out.println("Creando encuesta con título: " + titulo + " y ID de administrador: " + idAdministrador);

            // Guardar la encuesta en la base de datos
            Encuesta encuestaCreada = encuestaService.crearEncuesta(encuesta);
            JOptionPane.showMessageDialog(this, "Encuesta creada con ID: " + encuestaCreada.getIdEncuesta());

            // Crear dependencias necesarias
            IPreguntaService preguntaService = new PreguntaService();
            IOpcionRespuestaService opcionService = new OpcionRespuestaService();
            IPreguntaCompuestaService preguntaCompuestaService =
                    new PreguntaCompuestaService(preguntaService, opcionService);

            // Abrir ventana para agregar preguntas
            PanelPreguntas panelPreguntas = new PanelPreguntas(encuestaCreada, preguntaCompuestaService);

            JFrame framePreguntas = new JFrame("Agregar Preguntas");
            framePreguntas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            framePreguntas.setContentPane(panelPreguntas);
            framePreguntas.setSize(600, 500);
            framePreguntas.setLocationRelativeTo(null);
            framePreguntas.setVisible(true);

            txtTitulo.setText(""); // limpiar campo

        } catch (ServiceException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al crear la encuesta: " + ex.getMessage());
        }
    }
}
