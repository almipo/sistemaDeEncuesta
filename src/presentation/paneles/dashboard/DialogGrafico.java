package presentation.paneles.dashboard;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import service.IRespuestaUsuarioService;
import service.ServiceException;
import service.encuestaService.RespuestaUsuarioService;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Map;

public class DialogGrafico extends JDialog {
    private Map<String, Map<String, Integer>> resumen;
    private DefaultPieDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private JComboBox<String> comboPreguntas;
    private IRespuestaUsuarioService respuestaUsuario = new RespuestaUsuarioService();

    public DialogGrafico(int idEncuesta, String tituloEncuesta) throws ServiceException {


        resumen = respuestaUsuario.obtenerResumenPorEncuesta(idEncuesta);

        comboPreguntas = new JComboBox<>(resumen.keySet().toArray(new String[0]));
        comboPreguntas.addActionListener(e -> actualizarGrafico((String) comboPreguntas.getSelectedItem()));

        dataset = new DefaultPieDataset();
        chart = ChartFactory.createPieChart("", dataset, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} votos ({2})", new DecimalFormat("0"), new DecimalFormat("0.0%")));

        chartPanel = new ChartPanel(chart);

        setLayout(new BorderLayout());
        add(comboPreguntas, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);

        comboPreguntas.setSelectedIndex(0);

        pack();
        setLocationRelativeTo(null);
        setModal(true);
        setVisible(true);
    }

    private void actualizarGrafico(String pregunta) {
        dataset.clear();
        Map<String, Integer> datos = resumen.get(pregunta);
        datos.forEach(dataset::setValue);
        chart.setTitle(pregunta);
    }
}
