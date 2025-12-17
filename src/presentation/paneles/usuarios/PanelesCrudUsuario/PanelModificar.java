package presentation.paneles.usuarios.PanelesCrudUsuario;
import data.Model.Usuario;
import service.IUsuarioService;
import service.usuariosServices.UsuarioService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class PanelModificar extends JPanel {
    private IUsuarioService usuarioService;

    private JTextField tfIdModificar = new JTextField(10);
    private JButton btnBuscar = new JButton("Buscar usuario");

    private JPanel panelDatosModificar = new JPanel(new GridBagLayout());

    private JTextField tfNombreModificar = new JTextField(15);
    private JTextField tfApellidoModificar = new JTextField(15);
    private JTextField tfDniModificar = new JTextField(15);
    private JTextField tfRolModificar = new JTextField(15);

    private JButton btnModificar = new JButton("Modificar Usuario");

    private final String[] nombreOriginal = {""};
    private final String[] apellidoOriginal = {""};
    private final String[] dniOriginal = {""};

    public PanelModificar(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;

        setLayout(new BorderLayout());

        // Panel superior con campo ID y botón Buscar
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("ID del usuario:"));
        panelBusqueda.add(tfIdModificar);
        panelBusqueda.add(btnBuscar);

        add(panelBusqueda, BorderLayout.NORTH);
        add(panelDatosModificar, BorderLayout.CENTER);

        tfRolModificar.setEditable(false);
        btnModificar.setEnabled(false);

        // Configurar panelDatosModificar vacío inicialmente
        actualizarPanelDatos(false);

        // DocumentListener para detectar cambios
        Runnable actualizarEstadoBoton = () -> {
            boolean modificado =
                    !tfNombreModificar.getText().trim().equals(nombreOriginal[0]) ||
                            !tfApellidoModificar.getText().trim().equals(apellidoOriginal[0]) ||
                            !tfDniModificar.getText().trim().equals(dniOriginal[0]);
            btnModificar.setEnabled(modificado);
        };

        DocumentListener docListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { actualizarEstadoBoton.run(); }
            public void removeUpdate(DocumentEvent e) { actualizarEstadoBoton.run(); }
            public void changedUpdate(DocumentEvent e) { actualizarEstadoBoton.run(); }
        };

        // Botón Buscar - acción
        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfIdModificar.getText().trim());
                Usuario usuario = usuarioService.buscarUsuarioPorId(id);
                panelDatosModificar.removeAll();

                if (usuario != null) {
                    tfNombreModificar.setText(usuario.getNombre());
                    tfApellidoModificar.setText(usuario.getApellido());
                    tfDniModificar.setText(String.valueOf(usuario.getDni()));
                    tfRolModificar.setText(usuario.getRol().toString());

                    nombreOriginal[0] = usuario.getNombre();
                    apellidoOriginal[0] = usuario.getApellido();
                    dniOriginal[0] = String.valueOf(usuario.getDni());

                    // Remover y agregar listeners para evitar duplicados
                    tfNombreModificar.getDocument().removeDocumentListener(docListener);
                    tfApellidoModificar.getDocument().removeDocumentListener(docListener);
                    tfDniModificar.getDocument().removeDocumentListener(docListener);

                    tfNombreModificar.getDocument().addDocumentListener(docListener);
                    tfApellidoModificar.getDocument().addDocumentListener(docListener);
                    tfDniModificar.getDocument().addDocumentListener(docListener);

                    actualizarPanelDatos(true);
                    btnModificar.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró un usuario con ese ID.");
                    actualizarPanelDatos(false);
                    btnModificar.setEnabled(false);
                }

                panelDatosModificar.revalidate();
                panelDatosModificar.repaint();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al buscar usuario: " + ex.getMessage());
            }
        });

        // Botón Modificar - acción
        btnModificar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfIdModificar.getText().trim());
                String nombre = tfNombreModificar.getText().trim();
                String apellido = tfApellidoModificar.getText().trim();
                String dni = tfDniModificar.getText().trim();
                String rol = tfRolModificar.getText().trim();

                boolean modificado = usuarioService.modificarUsuario(id, nombre, apellido, dni, rol);
                if (modificado) {
                    JOptionPane.showMessageDialog(this, "Usuario modificado correctamente.");

                    nombreOriginal[0] = nombre;
                    apellidoOriginal[0] = apellido;
                    dniOriginal[0] = dni;
                    btnModificar.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró un usuario con ese ID.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al modificar usuario: " + ex.getMessage());
            }
        });
    }

    private void actualizarPanelDatos(boolean mostrarCampos) {
        panelDatosModificar.removeAll();

        if (mostrarCampos) {
            GridBagConstraints gbcDatos = new GridBagConstraints();
            gbcDatos.insets = new Insets(5, 5, 5, 5);
            gbcDatos.anchor = GridBagConstraints.WEST;
            gbcDatos.fill = GridBagConstraints.HORIZONTAL;

            gbcDatos.gridx = 0;
            gbcDatos.gridy = 0;
            panelDatosModificar.add(new JLabel("Nombre:"), gbcDatos);
            gbcDatos.gridx = 1;
            panelDatosModificar.add(tfNombreModificar, gbcDatos);

            gbcDatos.gridx = 0;
            gbcDatos.gridy = 1;
            panelDatosModificar.add(new JLabel("Apellido:"), gbcDatos);
            gbcDatos.gridx = 1;
            panelDatosModificar.add(tfApellidoModificar, gbcDatos);

            gbcDatos.gridx = 0;
            gbcDatos.gridy = 2;
            panelDatosModificar.add(new JLabel("DNI:"), gbcDatos);
            gbcDatos.gridx = 1;
            panelDatosModificar.add(tfDniModificar, gbcDatos);

            gbcDatos.gridx = 0;
            gbcDatos.gridy = 3;
            panelDatosModificar.add(new JLabel("Rol:"), gbcDatos);
            gbcDatos.gridx = 1;
            panelDatosModificar.add(tfRolModificar, gbcDatos);

            gbcDatos.gridx = 0;
            gbcDatos.gridy = 4;
            gbcDatos.gridwidth = 2;
            gbcDatos.anchor = GridBagConstraints.CENTER;
            panelDatosModificar.add(btnModificar, gbcDatos);
        }

        panelDatosModificar.revalidate();
        panelDatosModificar.repaint();
    }
}
