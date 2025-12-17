package presentation.paneles.administrador.panelesEncuesta;

import data.Model.Encuesta;
import data.Model.Pregunta;
import data.Model.Respuesta;
import service.IEncuestaService;
import service.IPreguntaService;
import service.IOpcionRespuestaService;
import service.ServiceException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class PanelModificarEncuesta extends JPanel {

    private final IEncuestaService encuestaService;
    private final IPreguntaService preguntaService;
    private final IOpcionRespuestaService opcionRespuestaService;

    private Encuesta encuestaActual;

    private JTextField txtTitulo;
    private JTable tablaPreguntas;
    private JTable tablaRespuestas;
    private DefaultTableModel modeloPreguntas;
    private DefaultTableModel modeloRespuestas;

    private JButton btnGuardarCambios;
    private JButton btnAgregarPregunta, btnEliminarPregunta;
    private JButton btnAgregarRespuesta, btnEliminarRespuesta;

    public PanelModificarEncuesta(IEncuestaService encuestaService,
                                  IPreguntaService preguntaService,
                                  IOpcionRespuestaService opcionRespuestaService) {
        this.encuestaService = encuestaService;
        this.preguntaService = preguntaService;
        this.opcionRespuestaService = opcionRespuestaService;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Búsqueda
        JPanel panelBuscar = new JPanel();
        JTextField txtIdEncuesta = new JTextField(5);
        JButton btnBuscar = new JButton("Buscar Encuesta");
        panelBuscar.add(new JLabel("ID Encuesta:"));
        panelBuscar.add(txtIdEncuesta);
        panelBuscar.add(btnBuscar);
        add(panelBuscar, BorderLayout.NORTH);

        // Título y guardar
        JPanel panelTitulo = new JPanel();
        txtTitulo = new JTextField(20);
        btnGuardarCambios = new JButton("Guardar Cambios");
        panelTitulo.add(new JLabel("Título:"));
        panelTitulo.add(txtTitulo);
        panelTitulo.add(btnGuardarCambios);
        add(panelTitulo, BorderLayout.CENTER);

        // Tabla preguntas
        modeloPreguntas = new DefaultTableModel(new String[]{"ID", "Pregunta", "Tipo"}, 0) {
            public boolean isCellEditable(int row, int col) {
                return col == 1 || col == 2;
            }
        };
        tablaPreguntas = new JTable(modeloPreguntas);
        JScrollPane scrollPreguntas = new JScrollPane(tablaPreguntas);

        // Tabla respuestas
        modeloRespuestas = new DefaultTableModel(new String[]{"ID", "Texto"}, 0) {
            public boolean isCellEditable(int row, int col) {
                return col == 1;
            }
        };
        tablaRespuestas = new JTable(modeloRespuestas);
        JScrollPane scrollRespuestas = new JScrollPane(tablaRespuestas);

        JPanel panelTablas = new JPanel(new GridLayout(1, 2));
        panelTablas.add(scrollPreguntas);
        panelTablas.add(scrollRespuestas);
        add(panelTablas, BorderLayout.SOUTH);

        // Botones preguntas y respuestas
        JPanel panelBotones = new JPanel(new GridLayout(2, 1));

        JPanel panelPreguntas = new JPanel();
        btnAgregarPregunta = new JButton("Agregar Pregunta");
        btnEliminarPregunta = new JButton("Eliminar Pregunta");
        panelPreguntas.add(btnAgregarPregunta);
        panelPreguntas.add(btnEliminarPregunta);

        JPanel panelRespuestas = new JPanel();
        btnAgregarRespuesta = new JButton("Agregar Respuesta");
        btnEliminarRespuesta = new JButton("Eliminar Respuesta");
        panelRespuestas.add(btnAgregarRespuesta);
        panelRespuestas.add(btnEliminarRespuesta);

        panelBotones.add(panelPreguntas);
        panelBotones.add(panelRespuestas);

        add(panelBotones, BorderLayout.EAST);

        // Acciones
        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtIdEncuesta.getText().trim());
                cargarEncuesta(id);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        });

        btnAgregarPregunta.addActionListener(e -> {
            modeloPreguntas.addRow(new Object[]{0, "Nueva pregunta", "OPCION_MULTIPLE"});
        });

        btnEliminarPregunta.addActionListener(e -> {
            int fila = tablaPreguntas.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modeloPreguntas.getValueAt(fila, 0);
                if (id != 0) {
                    try {
                        preguntaService.eliminarPregunta(id);
                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
                    }
                }
                modeloPreguntas.removeRow(fila);
                modeloRespuestas.setRowCount(0);
            }
        });

        btnAgregarRespuesta.addActionListener(e -> {
            int filaPregunta = tablaPreguntas.getSelectedRow();
            if (filaPregunta >= 0) {
                modeloRespuestas.addRow(new Object[]{0, "Nueva respuesta"});
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná una pregunta.");
            }
        });

        btnEliminarRespuesta.addActionListener(e -> {
            int fila = tablaRespuestas.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modeloRespuestas.getValueAt(fila, 0);
                if (id != 0) {
                    try {
                        opcionRespuestaService.eliminarOpcion(id);
                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(this, "Error al eliminar opción: " + ex.getMessage());
                    }
                }
                modeloRespuestas.removeRow(fila);
            }
        });

        tablaPreguntas.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                cargarRespuestasPreguntaSeleccionada();
            }
        });

        btnGuardarCambios.addActionListener(e -> guardarCambios());
    }

    private void cargarEncuesta(int idEncuesta) {
        try {
            encuestaActual = encuestaService.buscarEncuestaPorId(idEncuesta);
            if (encuestaActual == null) {
                JOptionPane.showMessageDialog(this, "Encuesta no encontrada.");
                return;
            }

            txtTitulo.setText(encuestaActual.getTitulo());
            modeloPreguntas.setRowCount(0);
            modeloRespuestas.setRowCount(0);

            for (Pregunta p : encuestaActual.getPreguntas()) {
                modeloPreguntas.addRow(new Object[]{p.getIdPregunta(), p.getPregunta()});
            }

            boolean tieneRespuestas = encuestaService.tieneRespuestasAsociadas(idEncuesta);
            txtTitulo.setEditable(true);
            tablaPreguntas.setEnabled(!tieneRespuestas);
            tablaRespuestas.setEnabled(!tieneRespuestas);
            btnAgregarPregunta.setEnabled(!tieneRespuestas);
            btnEliminarPregunta.setEnabled(!tieneRespuestas);
            btnAgregarRespuesta.setEnabled(!tieneRespuestas);
            btnEliminarRespuesta.setEnabled(!tieneRespuestas);

            if (tieneRespuestas) {
                JOptionPane.showMessageDialog(this, "Solo se puede modificar el título porque ya tiene respuestas.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar encuesta: " + ex.getMessage());
        }
    }

    private void cargarRespuestasPreguntaSeleccionada() {
        int fila = tablaPreguntas.getSelectedRow();
        if (fila < 0) return;

        modeloRespuestas.setRowCount(0);
        int idPregunta = (int) modeloPreguntas.getValueAt(fila, 0);
        if (idPregunta == 0) return; // pregunta nueva

        try {
            List<Respuesta> respuestas = opcionRespuestaService.listarOpcionesPorPregunta(idPregunta);
            for (Respuesta r : respuestas) {
                modeloRespuestas.addRow(new Object[]{r.getIdOpcion(), r.getDescripcion()});
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar respuestas: " + e.getMessage());
        }
    }

    private void guardarCambios() {
        if (encuestaActual == null) return;

        try {
            encuestaActual.setTitulo(txtTitulo.getText().trim());
            encuestaService.modificarEncuesta(encuestaActual);

            boolean tieneRespuestas = encuestaService.tieneRespuestasAsociadas(encuestaActual.getIdEncuesta());
            if (!tieneRespuestas) {
                // Guardar preguntas
                for (int i =1 ; i < modeloPreguntas.getRowCount(); i++) {
                    int id = (int) modeloPreguntas.getValueAt(i, 0);
                    String texto = (String) modeloPreguntas.getValueAt(i, 1);
                    String tipo = (String) modeloPreguntas.getValueAt(i, 2);

                    Pregunta p = new Pregunta(id, texto, encuestaActual.getIdEncuesta());

                    if (id == 0) {
                        preguntaService.crearPregunta(p.getEncuestaId(), p.getPregunta());
                        modeloPreguntas.setValueAt(p.getIdPregunta(), i, 0);
                        id = p.getIdPregunta();
                    } else {
                        preguntaService.modificarPregunta(p);
                    }


                    if (i == tablaPreguntas.getSelectedRow()) {
                        for (int j = 0; j < modeloRespuestas.getRowCount(); j++) {
                            int idOpcion = (int) modeloRespuestas.getValueAt(j, 0);
                            String textoOpcion = (String) modeloRespuestas.getValueAt(j, 1);
                            int valor = j + 1;

                            if (idOpcion == 0) {
                                Respuesta nueva = opcionRespuestaService.crearOpcion(p.getIdPregunta(), valor, textoOpcion);
                                modeloRespuestas.setValueAt(nueva.getIdOpcion(), j, 0);
                            } else {
                                Respuesta r = new Respuesta(valor, textoOpcion, idOpcion);
                                opcionRespuestaService.modificarOpcion(r);
                            }
                        }
                    }
                }
            }

            JOptionPane.showMessageDialog(this, "Cambios guardados correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
