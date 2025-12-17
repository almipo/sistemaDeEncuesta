package presentation.paneles.administrador.panelesEncuesta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import data.Model.Encuesta;
import data.Model.Pregunta;
import service.IPreguntaCompuestaService;
import service.ServiceException;

public class PanelPreguntas extends JPanel {

    private final Encuesta encuesta;
    private final IPreguntaCompuestaService preguntaCompuestaService;

    private JTextField txtPregunta;
    private JComboBox<String> cmbTipoPregunta;

    private JPanel panelOpciones;
    private DefaultTableModel modeloTablaOpciones;
    private JTable tablaOpciones;
    private JCheckBox chkRespuestaLibre;
    private DefaultListModel<String> modeloListaOpciones;
    private JList<String> listaOpciones;

    // Constantes para tipos de pregunta
    private static final String TIPO_POR_DEFECTO = "Por defecto";
    private static final String TIPO_PERSONALIZADA = "Personalizada";
    private static final String TIPO_TEXTO_LIBRE = "Texto libre";

    public PanelPreguntas(Encuesta encuesta, IPreguntaCompuestaService preguntaCompuestaService) {
        this.encuesta = encuesta;
        this.preguntaCompuestaService = preguntaCompuestaService;

        setLayout(new BorderLayout());

        // Formulario principal
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblPregunta = new JLabel("Texto de la pregunta:");
        txtPregunta = new JTextField(30);

        JLabel lblTipo = new JLabel("Tipo de pregunta:");
        cmbTipoPregunta = new JComboBox<>(new String[]{TIPO_POR_DEFECTO, TIPO_PERSONALIZADA, TIPO_TEXTO_LIBRE});

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblPregunta, gbc);
        gbc.gridx = 1;
        formPanel.add(txtPregunta, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblTipo, gbc);
        gbc.gridx = 1;
        formPanel.add(cmbTipoPregunta, gbc);

        // Panel para opciones personalizadas
        panelOpciones = new JPanel(new BorderLayout());
        String[] columnas = {"#", "Opción"};
        modeloTablaOpciones = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaOpciones = new JTable(modeloTablaOpciones);
        JScrollPane scrollOpciones = new JScrollPane(tablaOpciones);
        JPanel panelAgregarOpcion = new JPanel();
        JTextField txtOpcion = new JTextField(15);
        JButton btnAgregarOpcion = new JButton("Agregar Opción");
        JButton btnEliminarOpcion = new JButton("Eliminar Opción");

        btnAgregarOpcion.addActionListener((ActionEvent e) -> {
            String opcion = txtOpcion.getText().trim();
            if (!opcion.isEmpty()) {
                int numero = modeloTablaOpciones.getRowCount() + 1;
                modeloTablaOpciones.addRow(new Object[]{numero, opcion});
                txtOpcion.setText("");
            }
        });


        btnEliminarOpcion.addActionListener((ActionEvent e) -> {
            int index = tablaOpciones.getSelectedRow();
            if (index >= 0) {
                modeloTablaOpciones.removeRow(index);
                // Recalcular numeración
                for (int i = 0; i < modeloTablaOpciones.getRowCount(); i++) {
                    modeloTablaOpciones.setValueAt(i + 1, i, 0);
                }
            }
        });


        panelAgregarOpcion.add(txtOpcion);
        panelAgregarOpcion.add(btnAgregarOpcion);
        panelAgregarOpcion.add(btnEliminarOpcion);

        chkRespuestaLibre = new JCheckBox("Permitir respuesta libre");

        panelOpciones.add(panelAgregarOpcion, BorderLayout.NORTH);
        panelOpciones.add(scrollOpciones, BorderLayout.CENTER);
        panelOpciones.add(chkRespuestaLibre, BorderLayout.SOUTH);

        // Panel visible sólo si se selecciona tipo personalizada
        panelOpciones.setVisible(false);

        // Evento para mostrar/ocultar panel de opciones
        cmbTipoPregunta.addActionListener(e -> {
            String tipo = (String) cmbTipoPregunta.getSelectedItem();

            switch (tipo) {
                case TIPO_POR_DEFECTO:
                    panelOpciones.setVisible(false);
                    chkRespuestaLibre.setVisible(false);
                    break;
                case TIPO_PERSONALIZADA:
                    panelOpciones.setVisible(true);
                    chkRespuestaLibre.setVisible(false);  // OCULTAR el checkbox acá
                    break;
                case TIPO_TEXTO_LIBRE:
                    panelOpciones.setVisible(false);
                    chkRespuestaLibre.setVisible(true);   // SOLO mostrarlo en texto libre
                    break;
                default:
                    panelOpciones.setVisible(false);
                    chkRespuestaLibre.setVisible(false);
                    break;
            }
        });


        // Botón para crear pregunta
        JButton btnCrearPregunta = new JButton("Crear Pregunta");
        btnCrearPregunta.addActionListener(e -> crearPregunta());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnCrearPregunta);

        // Armado final
        JPanel central = new JPanel(new BorderLayout());
        central.add(formPanel, BorderLayout.NORTH);
        central.add(panelOpciones, BorderLayout.CENTER);
        central.add(panelBoton, BorderLayout.SOUTH);

        add(central, BorderLayout.CENTER);



    }

    private void crearPregunta() {
        String texto = txtPregunta.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el texto de la pregunta.");
            return;
        }

        String tipo = (String) cmbTipoPregunta.getSelectedItem();

        try {
            Pregunta creada;

            switch (tipo) {
                case TIPO_POR_DEFECTO:
                    creada = preguntaCompuestaService.crearConOpcionesPorDefecto(encuesta.getIdEncuesta(), texto);
                    break;
                case TIPO_PERSONALIZADA:
                    List<String> etiquetas = new ArrayList<>();
                    for (int i = 0; i < modeloTablaOpciones.getRowCount(); i++) {
                        etiquetas.add(modeloTablaOpciones.getValueAt(i, 1).toString());
                    }

                    // Mostrar número y valor de cada opción
                    StringBuilder opcionesStr = new StringBuilder();
                    for (int i = 0; i < etiquetas.size(); i++) {
                        opcionesStr.append("Opción ").append(i + 1)
                                .append(": ").append(etiquetas.get(i)).append("\n");
                    }

                    boolean permitirLibre = chkRespuestaLibre.isSelected();
                    creada = preguntaCompuestaService.crearConOpcionesVariables(
                            encuesta.getIdEncuesta(), texto, etiquetas, permitirLibre);
                    JOptionPane.showMessageDialog(this, opcionesStr.toString());
                    break;
                case TIPO_TEXTO_LIBRE:
                    creada = preguntaCompuestaService.crearConOpcionesVariables(
                            encuesta.getIdEncuesta(), texto, new ArrayList<>(), true);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Tipo de pregunta inválido.");
                    return;
            }

            JOptionPane.showMessageDialog(this, "Pregunta creada (ID: " + creada.getIdPregunta() + ")");
            limpiarFormulario();

        } catch (ServiceException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al crear la pregunta: " + ex.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtPregunta.setText("");
        modeloTablaOpciones.setRowCount(0);
        chkRespuestaLibre.setSelected(false);
        cmbTipoPregunta.setSelectedIndex(0);
        panelOpciones.setVisible(false);
    }
}
