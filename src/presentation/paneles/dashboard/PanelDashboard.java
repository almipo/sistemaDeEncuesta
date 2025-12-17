package presentation.paneles.dashboard;

import data.Model.Encuesta;
import service.IEncuestaService;
import service.IRespuestaUsuarioService;
import service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class PanelDashboard extends JPanel {

    private final IEncuestaService encuestaService = new service.encuestaService.EncuestaService();
    private final IRespuestaUsuarioService respuestaService = new service.encuestaService.RespuestaUsuarioService();



    public PanelDashboard() {


        setLayout(new GridLayout(2, 2, 10, 10)); // 2x2 para 4 encuestas
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cargarEncuestas();
    }

    private void cargarEncuestas() {
        try {
            List<Encuesta> ultimas = encuestaService.obtenerUltimasEncuestas(4);
            for (Encuesta encuesta : ultimas) {
                add(crearPanelEncuesta(encuesta));
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error cargando encuestas: " + e.getMessage());
        }
    }

    private JPanel crearPanelEncuesta(Encuesta encuesta) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(encuesta.getTitulo()));

        JTextArea areaResumen = new JTextArea();
        areaResumen.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaResumen);

        JButton btnGrafico = new JButton("Mostrar Gráfico");
        btnGrafico.addActionListener(e -> {
            try {
                new DialogGrafico(encuesta.getIdEncuesta(), encuesta.getTitulo()).setVisible(true);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        });

        try {
            Map<String, Map<String, Integer>> resumen = respuestaService.obtenerResumenPorEncuesta(encuesta.getIdEncuesta());
            StringBuilder sb = new StringBuilder();

            for (Map.Entry<String, Map<String, Integer>> entry : resumen.entrySet()) {
                String pregunta = entry.getKey();
                Map<String, Integer> conteo = entry.getValue();
                int total = conteo.values().stream().mapToInt(i -> i).sum();

                sb.append(pregunta).append(":\n");
                for (Map.Entry<String, Integer> op : conteo.entrySet()) {
                    double porcentaje = (op.getValue() * 100.0) / total;
                    sb.append("  ").append(op.getKey()).append(": ").append(String.format("%.1f", porcentaje)).append("%\n");
                }
                sb.append("\n");
            }

            areaResumen.setText(sb.toString());

        } catch (ServiceException e) {
            areaResumen.setText("Error al obtener resumen.");
        }

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnGrafico, BorderLayout.SOUTH);
        return panel;
    }
}
