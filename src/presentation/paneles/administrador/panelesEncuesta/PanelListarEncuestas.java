package presentation.paneles.administrador.panelesEncuesta;

import data.Model.Encuesta;
import data.Model.ResumenRespuestaDTO;
import service.IEncuestaService;
import service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class PanelListarEncuestas extends JPanel {

    private final IEncuestaService encuestaService;
    private final int idAdministrador;
    private JComboBox<Encuesta> comboEncuestas;
    private JTextField desdeField;
    private JTextField hastaField;
    private JButton btnFiltrar;
    private JTable tablaResumen;
    private ResumenEncuestaTableModel tableModel;

    public PanelListarEncuestas(IEncuestaService encuestaService, int idAdministrador) {
        this.encuestaService = encuestaService;
        this.idAdministrador = idAdministrador;
        initUI();
        cargarEncuestas();
    }
    private void initUI() {
        setLayout(new BorderLayout());

        // Panel superior para filtros
        JPanel panelFiltros = new JPanel(new FlowLayout());

        comboEncuestas = new JComboBox<>();
        panelFiltros.add(new JLabel("Encuesta:"));
        panelFiltros.add(comboEncuestas);

        desdeField = new JTextField(10);
        desdeField.setToolTipText("Fecha desde (yyyy-MM-dd)");
        panelFiltros.add(new JLabel("Desde:"));
        panelFiltros.add(desdeField);

        hastaField = new JTextField(10);
        hastaField.setToolTipText("Fecha hasta (yyyy-MM-dd)");
        panelFiltros.add(new JLabel("Hasta:"));
        panelFiltros.add(hastaField);

        btnFiltrar = new JButton("Filtrar");
        panelFiltros.add(btnFiltrar);

        add(panelFiltros, BorderLayout.NORTH);

        // Tabla resumen
        tableModel = new ResumenEncuestaTableModel(new ArrayList<>());
        tablaResumen = new JTable(tableModel);
        add(new JScrollPane(tablaResumen), BorderLayout.CENTER);

        // Acción botón filtrar
        btnFiltrar.addActionListener(e -> filtrarResumen());
    }


    private void cargarEncuestas() {
        try {
            List<Encuesta> lista = encuestaService.listarEncuestasPorAdministrador(idAdministrador);
            DefaultComboBoxModel<Encuesta> model = new DefaultComboBoxModel<>();
            model.addElement(new Encuesta(0, "— Todas las Encuestas —", null));
            for (Encuesta e : lista) {
                model.addElement(e);
            }
            comboEncuestas.setModel(model);
            comboEncuestas.setRenderer((list, value, index, isSelected, cellHasFocus) ->
                    new JLabel(value.getTitulo()));
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, "Error cargando encuestas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarResumen() {
        Encuesta encuestaSeleccionada = (Encuesta) comboEncuestas.getSelectedItem();
        if (encuestaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una encuesta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate desde, hasta;
        try {
            desde = LocalDate.parse(desdeField.getText());
            hasta = LocalDate.parse(hastaField.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fechas inválidas. Use formato yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            List<ResumenRespuestaDTO> resumen;
            System.out.println("Obteniendo resumen para encuesta: " + encuestaSeleccionada.getIdEncuesta() );

            if (encuestaSeleccionada.getIdEncuesta() == 0) {
                resumen = encuestaService.obtenerResumenTodasEncuestas(desde, hasta);

            } else {
                resumen = encuestaService.obtenerResumenEncuesta(encuestaSeleccionada.getIdEncuesta(), desde, hasta);
            }

            tableModel = new ResumenEncuestaTableModel(resumen);
            tablaResumen.setModel(tableModel);
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener resumen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }}

}