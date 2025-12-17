package presentation.paneles.administrador.panelesEncuesta;
import service.IEncuestaService;
import service.ServiceException;

import javax.swing.*;
import java.awt.*;

public class PanelEliminarEncuesta extends JPanel {

    private final IEncuestaService encuestaService;

    private JTextField txtIdEncuesta;
    private JButton btnEliminar;

    public PanelEliminarEncuesta(IEncuestaService encuestaService) {
        this.encuestaService = encuestaService;
        initUI();
    }

    private void initUI() {
        setLayout(new FlowLayout());

        add(new JLabel("ID Encuesta:"));

        txtIdEncuesta = new JTextField(10);
        add(txtIdEncuesta);

        btnEliminar = new JButton("Eliminar Encuesta");
        add(btnEliminar);

        btnEliminar.addActionListener(e -> eliminarEncuesta());
    }

    private void eliminarEncuesta() {
        String idStr = txtIdEncuesta.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el ID de la encuesta.");
            return;
        }

        int idEncuesta;
        try {
            idEncuesta = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
            return;
        }

        try {
           /*if (encuestaService.tieneRespuestasAsociadas(idEncuesta)) {
                JOptionPane.showMessageDialog(this, "No se puede eliminar la encuesta porque tiene respuestas asociadas.");
                return;
            }*/

            encuestaService.eliminarEncuesta(idEncuesta);
            JOptionPane.showMessageDialog(this, "Encuesta eliminada correctamente.");
            txtIdEncuesta.setText("");

        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar la encuesta: " + ex.getMessage());
        }
    }
}
