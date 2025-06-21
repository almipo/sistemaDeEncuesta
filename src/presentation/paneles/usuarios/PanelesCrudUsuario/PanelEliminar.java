package presentation.paneles.usuarios.PanelesCrudUsuario;
import data.Model.Usuario;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class PanelEliminar extends JPanel {

    private final UsuarioService usuarioService; // lo recibes para usar el servicio

    private JTextField tfIdEliminar;
    private JLabel lblDatosEliminar;
    private JButton btnEjecutarEliminar;
    private Usuario usuarioEncontrado;

    public PanelEliminar(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Campo ID
        tfIdEliminar = new JTextField(15);
        agregarCampo(this, gbc, 0, "ID del usuario a eliminar:", tfIdEliminar);

        // Botón buscar
        JButton btnBuscarEliminar = new JButton("Buscar");
        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(btnBuscarEliminar, gbc);

        // Label datos usuario
        lblDatosEliminar = new JLabel("Datos del usuario aparecerán aquí");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        this.add(lblDatosEliminar, gbc);

        // Botón eliminar
        btnEjecutarEliminar = new JButton("Eliminar Usuario");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        btnEjecutarEliminar.setEnabled(false);
        this.add(btnEjecutarEliminar, gbc);

        // Acción botón buscar
        btnBuscarEliminar.addActionListener(e -> buscarUsuario());

        // Acción botón eliminar
        btnEjecutarEliminar.addActionListener(e -> eliminarUsuario());
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int y, String texto, JTextField campo) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        panel.add(new JLabel(texto), gbc);
        gbc.gridx = 1;
        panel.add(campo, gbc);
    }

    private void buscarUsuario() {
        try {
            int id = Integer.parseInt(tfIdEliminar.getText().trim());
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);
            if (usuario != null) {
                usuarioEncontrado = usuario;
                lblDatosEliminar.setText("<html>ID: " + usuario.getId() +
                        "<br>Nombre: " + usuario.getNombre() +
                        "<br>Apellido: " + usuario.getApellido() +
                        "<br>DNI: " + usuario.getDni() +
                        "<br>Rol: " + usuario.getRol() +
                        "</html>");
                btnEjecutarEliminar.setEnabled(true);
            } else {
                usuarioEncontrado = null;
                lblDatosEliminar.setText("No se encontró un usuario con ese ID.");
                btnEjecutarEliminar.setEnabled(false);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
            usuarioEncontrado = null;
            btnEjecutarEliminar.setEnabled(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar usuario: " + ex.getMessage());
            usuarioEncontrado = null;
            btnEjecutarEliminar.setEnabled(false);
        }
    }

    private void eliminarUsuario() {
        if (usuarioEncontrado == null) {
            JOptionPane.showMessageDialog(this, "Primero debe buscar un usuario válido.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de eliminar este usuario?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean eliminado = usuarioService.eliminarUsuario(usuarioEncontrado.getId(),
                        String.valueOf(usuarioEncontrado.getRol()));
                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
                    lblDatosEliminar.setText("Datos del usuario aparecerán aquí");
                    btnEjecutarEliminar.setEnabled(false);
                    tfIdEliminar.setText("");
                    usuarioEncontrado = null;
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró un usuario con ese ID.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar usuario: " + ex.getMessage());
            }
        }
    }
}
