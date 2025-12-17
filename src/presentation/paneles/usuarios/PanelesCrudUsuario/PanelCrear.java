package presentation.paneles.usuarios.PanelesCrudUsuario;
import service.IUsuarioService;
import service.usuariosServices.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class PanelCrear extends JPanel {
    private JTextField tfNombreCrear = new JTextField(15);
    private JTextField tfApellidoCrear = new JTextField(15);
    private JTextField tfDniCrear = new JTextField(15);
    private JComboBox<String> cbRolCrear = new JComboBox<>(new String[]{"Administrador", "Promotor"});
    private JButton btnEjecutarCrear = new JButton("Crear Usuario");

    private IUsuarioService usuarioService;

    public PanelCrear(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        agregarCampo("Nombre:", tfNombreCrear, gbc, 0);
        agregarCampo("Apellido:", tfApellidoCrear, gbc, 1);
        agregarCampo("DNI:", tfDniCrear, gbc, 2);
        agregarCampo("Rol:", cbRolCrear, gbc, 3);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnEjecutarCrear, gbc);

        btnEjecutarCrear.addActionListener(e -> crearUsuario());
    }

    private void agregarCampo(String label, JComponent campo, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel(label), gbc);
        gbc.gridx = 1;
        add(campo, gbc);
    }

    private void crearUsuario() {
        try {
            String nombre = tfNombreCrear.getText().trim();
            String apellido = tfApellidoCrear.getText().trim();
            String dni = tfDniCrear.getText().trim();
            String rol = (String) cbRolCrear.getSelectedItem();

            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || rol == null) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            usuarioService.crearUsuario(nombre, apellido, dni, rol);
            JOptionPane.showMessageDialog(this, rol + " creado correctamente.");

            tfNombreCrear.setText("");
            tfApellidoCrear.setText("");
            tfDniCrear.setText("");
        } catch (UnsupportedOperationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Atención", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
