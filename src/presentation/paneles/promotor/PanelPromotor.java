package presentation.paneles.promotor;

import data.Model.*;
import service.IEncuestaService;
import service.IRespuestaUsuarioService;
import service.ServiceException;
import service.encuestaService.EncuestaService;
import service.IUsuarioService;
import service.encuestaService.RespuestaUsuarioService;
import service.usuariosServices.PromotorService;
import service.usuariosServices.UsuarioService;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PanelPromotor extends JPanel {

    private JTextField txtIdPromotor;
    private JTextField txtIdEncuesta;
    private JPanel panelCentro;

    private final IEncuestaService encuestaService = new EncuestaService();
    private final UsuarioService promotorService = new UsuarioService();
    private final IRespuestaUsuarioService respuestaUsuarioService = new RespuestaUsuarioService();
    private boolean promotorValidado = false;
    private int idPromotorActual = -1;

    public PanelPromotor() {
        setLayout(new BorderLayout());

        // Panel superior: ingreso ID Promotor y búsqueda/listado de encuestas
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.add(new JLabel("ID Promotor:"));
        txtIdPromotor = new JTextField(5);
        panelSuperior.add(txtIdPromotor);

        JButton btnBuscar = new JButton("Buscar Encuesta por ID");
        JButton btnListar = new JButton("Listar Encuestas");

        panelSuperior.add(new JLabel("ID Encuesta:"));
        txtIdEncuesta = new JTextField(5);
        panelSuperior.add(txtIdEncuesta);

        panelSuperior.add(btnBuscar);
        panelSuperior.add(btnListar);

        add(panelSuperior, BorderLayout.NORTH);

        // Panel central: listado o encuesta
        panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        add(new JScrollPane(panelCentro), BorderLayout.CENTER);

        // Listeners con validación de promotor
        btnBuscar.addActionListener((ActionEvent e) -> {
            if (validarPromotor()) buscarEncuestaPorId();
        });
        btnListar.addActionListener((ActionEvent e) -> {
            if (validarPromotor()) listarEncuestas();
        });
    }


    private boolean validarPromotor() {
        String input = txtIdPromotor.getText().trim();
        try {
            int id = Integer.parseInt(input);

            IUsuarioService usuarioService = new UsuarioService();
            RolUsuario rol = usuarioService.obtenerRol(id);

            if (rol == RolUsuario.PROMOTOR) {
                JOptionPane.showMessageDialog(this, "Acceso concedido. Bienvenido Promotor.");
                this.promotorValidado = true;
                this.idPromotorActual = id;
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Este usuario no es un promotor.");
                this.promotorValidado = false;
                return false;
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido. Ingrese un número.");
            return false;
        } catch (ServiceException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al validar usuario: " + ex.getMessage());
            return false;
        }
    }

    private void buscarEncuestaPorId() {
        panelCentro.removeAll();
        String idTexto = txtIdEncuesta.getText();
        try {
            int id = Integer.parseInt(idTexto);
            Encuesta encuesta = encuestaService.buscarEncuestaPorId(id);
            if (encuesta != null) {
                mostrarEncuesta(encuesta);
            } else {
                panelCentro.add(new JLabel("No se encontró la encuesta con ID " + id));
            }
        } catch (NumberFormatException e) {
            panelCentro.add(new JLabel("ID inválido."));
        } catch (ServiceException e) {
            panelCentro.add(new JLabel("Error al buscar encuesta: " + e.getMessage()));
        }
        recargarCentro();
    }

    private void listarEncuestas() {
        panelCentro.removeAll();
        try {
            List<Encuesta> encuestas = encuestaService.listarTodasEncuestas();
            if (encuestas.isEmpty()) {
                panelCentro.add(new JLabel("No hay encuestas disponibles."));
            } else {
                for (Encuesta e : encuestas) {
                    mostrarEncuesta(e);
                }
            }
        } catch (ServiceException e) {
            panelCentro.add(new JLabel("Error al listar encuestas: " + e.getMessage()));
        }
        recargarCentro();
    }

    private void mostrarEncuesta(Encuesta encuesta) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(new JLabel("Encuesta: " + encuesta.getTitulo()), BorderLayout.NORTH);
        panel.add(new JLabel("Fecha: " + encuesta.getFechaDeCreacion()), BorderLayout.CENTER);

        JButton btnResponder = new JButton("Responder");
        btnResponder.addActionListener(e -> abrirPantallaResponder(encuesta));
        panel.add(btnResponder, BorderLayout.EAST);

        panelCentro.add(panel);
    }

    private void abrirPantallaResponder(Encuesta encuesta) {
        try {
            // Asegurate de volver a buscar la encuesta con sus preguntas y respuestas completas
            Encuesta encuestaCompleta = encuestaService.buscarEncuestaPorId(encuesta.getIdEncuesta());

            if (encuestaCompleta.getPreguntas() == null || encuestaCompleta.getPreguntas().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La encuesta no tiene preguntas cargadas.");
                return;
            }

            new DialogResponderEncuesta(encuestaCompleta).setVisible(true);
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar encuesta completa: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    private void recargarCentro() {
        panelCentro.revalidate();
        panelCentro.repaint();
    }

    // --- Clase interna para mostrar preguntas y respuestas ---
    private class DialogResponderEncuesta extends JDialog {

        private Encuesta encuesta;
        private JButton btnGuardar;

        // Para guardar respuestas seleccionadas o textos libres
        // clave: idPregunta, valor: idRespuesta o texto (en este ejemplo usamos Object)
        private java.util.Map<Integer, Object> respuestasMap = new java.util.HashMap<>();

        public DialogResponderEncuesta(Encuesta encuesta) {
            this.encuesta = encuesta;

            setTitle("Responder Encuesta: " + encuesta.getTitulo());
            setModal(true);
            setSize(600, 400);
            setLocationRelativeTo(PanelPromotor.this);

            JPanel panelPreguntas = new JPanel();
            panelPreguntas.setLayout(new BoxLayout(panelPreguntas, BoxLayout.Y_AXIS));

            for (Pregunta p : encuesta.getPreguntas()) {
                JPanel panelPregunta = new JPanel(new BorderLayout());
                panelPregunta.setBorder(BorderFactory.createTitledBorder(p.getPregunta()));

                // Si la pregunta tiene respuestas posibles (lista no vacía)
                if (p.getRespuestas() != null && !p.getRespuestas().isEmpty()) {
                    // Mostrar opciones con radio buttons
                    ButtonGroup grupoOpciones = new ButtonGroup();
                    JPanel panelOpciones = new JPanel(new FlowLayout(FlowLayout.LEFT));

                    for (Respuesta r : p.getRespuestas()) {
                        JRadioButton rbtn = new JRadioButton(r.getValor() + " - " + r.getDescripcion());
                        rbtn.setActionCommand(String.valueOf(r.getValor()));
                        grupoOpciones.add(rbtn);
                        panelOpciones.add(rbtn);

                        // Listener para guardar la respuesta seleccionada
                        rbtn.addActionListener(e -> respuestasMap.put(p.getIdPregunta(), r));
                    }

                    panelPregunta.add(panelOpciones, BorderLayout.CENTER);

                } else {
                    // Si no hay respuestas posibles, es respuesta libre (textarea)
                    JTextArea areaTexto = new JTextArea(3, 50);
                    areaTexto.setLineWrap(true);
                    areaTexto.setWrapStyleWord(true);

                    areaTexto.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                        public void changedUpdate(javax.swing.event.DocumentEvent e) { guardarTexto(); }
                        public void removeUpdate(javax.swing.event.DocumentEvent e) { guardarTexto(); }
                        public void insertUpdate(javax.swing.event.DocumentEvent e) { guardarTexto(); }
                        private void guardarTexto() {
                            respuestasMap.put(p.getIdPregunta(), areaTexto.getText());
                        }
                    });

                    panelPregunta.add(new JScrollPane(areaTexto), BorderLayout.CENTER);
                }

                panelPreguntas.add(panelPregunta);
            }

            btnGuardar = new JButton("Guardar Respuestas");
            btnGuardar.addActionListener(e -> guardarRespuestas());

            JScrollPane scrollPane = new JScrollPane(panelPreguntas);

            getContentPane().setLayout(new BorderLayout());
            getContentPane().add(scrollPane, BorderLayout.CENTER);
            getContentPane().add(btnGuardar, BorderLayout.SOUTH);
        }

        private void guardarRespuestas() {
            try {
                for (Map.Entry<Integer, Object> entry : respuestasMap.entrySet()) {
                    int idPregunta = entry.getKey();
                    Object valor = entry.getValue();

                    RespuestaUsuario ru = new RespuestaUsuario();
                    ru.setUsuarioId(idPromotorActual);
                    ru.setPreguntaId(idPregunta);
                    ru.setFechaRealizacion(LocalDate.now());

                    // PONE el id de encuesta también (muy importante)
                    ru.setEncuestaId(encuesta.getIdEncuesta());

                    if (valor instanceof Respuesta) {
                        Respuesta r = (Respuesta) valor;
                        ru.setRespuestaId(r.getIdOpcion());
                        ru.setRespuestaLibre(null);
                    } else if (valor instanceof String) {
                        ru.setRespuestaLibre((String) valor);
                        ru.setRespuestaId(null);
                    } else {
                        continue; // respuesta vacía o inválida
                    }

                    respuestaUsuarioService.registrarRespuesta(ru);
                }

                JOptionPane.showMessageDialog(this, "Respuestas guardadas correctamente.");
                dispose();

            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar respuestas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }


    }
}
