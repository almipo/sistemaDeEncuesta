package presentation.paneles.usuarios.PanelesCrudUsuario;
import data.Model.Usuario;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class PanelBuscar extends JPanel {
    private JTextField tfBuscarId = new JTextField(15);
    private JButton btnEjecutarBuscar = new JButton("Buscar Usuario");

    private UsuarioService usuarioService;
    private JPanel resultadoPanel = new JPanel();

    public PanelBuscar(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        // Campo y botón buscar
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Buscar por ID:"), gbc);

        gbc.gridx = 1;
        add(tfBuscarId, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnEjecutarBuscar, gbc);

        // Panel donde se mostrarán los resultados
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        resultadoPanel.setLayout(new GridLayout(0,1));
        add(resultadoPanel, gbc);

        btnEjecutarBuscar.addActionListener(e -> buscarUsuario());
    }

    private void buscarUsuario() {
        resultadoPanel.removeAll();

        try {
            int id = Integer.parseInt(tfBuscarId.getText().trim());
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);

            if (usuario != null) {
                resultadoPanel.add(new JLabel("Usuario encontrado:"));
                resultadoPanel.add(new JLabel("ID: " + usuario.getId()));
                resultadoPanel.add(new JLabel("Nombre: " + usuario.getNombre()));
                resultadoPanel.add(new JLabel("Apellido: " + usuario.getApellido()));
                resultadoPanel.add(new JLabel("DNI: " + usuario.getDni()));
                resultadoPanel.add(new JLabel("Rol: " + usuario.getRol()));
            } else {
                resultadoPanel.add(new JLabel("No se encontró un usuario con ese ID."));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar usuario: " + ex.getMessage());
        }

        resultadoPanel.revalidate();
        resultadoPanel.repaint();
    }
}
