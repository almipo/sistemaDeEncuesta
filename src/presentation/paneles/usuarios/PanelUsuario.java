package presentation.paneles.usuarios;

import data.Model.Usuario;
import presentation.paneles.usuarios.PanelesCrudUsuario.PanelBuscar;
import presentation.paneles.usuarios.PanelesCrudUsuario.PanelCrear;
import presentation.paneles.usuarios.PanelesCrudUsuario.PanelEliminar;
import presentation.paneles.usuarios.PanelesCrudUsuario.PanelModificar;
import service.IUsuarioService;
import service.ServiceException;
import service.usuariosServices.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class PanelUsuario extends JPanel {
    private JPanel panelCampos;
    private JComboBox<String> comboRolesFiltro;
    private final IUsuarioService usuarioService = new UsuarioService();

    public PanelUsuario() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelCampos = new JPanel();
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
        actualizarCampos("inicio");

        // Panel de botones CRUD
        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton btnCrear = new JButton("Crear");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnListar = new JButton("Listar");

        // Combo para filtrar roles
        comboRolesFiltro = new JComboBox<>(new String[]{"Todos", "Administrador", "Promotor"});

        panelBotones.add(btnCrear);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(new JLabel("Filtrar por rol:"));
        panelBotones.add(comboRolesFiltro);
        panelBotones.add(btnListar);

        // Eventos
        btnCrear.addActionListener((ActionEvent e) -> actualizarCampos("crear"));
        btnBuscar.addActionListener((ActionEvent e) -> actualizarCampos("buscar"));
        btnModificar.addActionListener((ActionEvent e) -> actualizarCampos("modificar"));
        btnEliminar.addActionListener((ActionEvent e) -> actualizarCampos("eliminar"));
        btnListar.addActionListener((ActionEvent e) -> listarUsuarios());

        add(panelCampos, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void actualizarCampos(String accion) {
        panelCampos.removeAll();

        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        switch (accion) {
            case "crear":
                formulario.removeAll();
                formulario.setLayout(new BorderLayout());
                formulario.add(new PanelCrear(usuarioService), BorderLayout.CENTER);
                formulario.revalidate();
                formulario.repaint();

                break;

            case "buscar":
                formulario.removeAll();
                formulario.setLayout(new BorderLayout());
                formulario.add(new PanelBuscar(usuarioService), BorderLayout.CENTER);
                formulario.revalidate();
                formulario.repaint();


                break;

            case "modificar":
                formulario.removeAll();
                formulario.setLayout(new BorderLayout());
                formulario.add(new PanelModificar(usuarioService), BorderLayout.CENTER);
                formulario.revalidate();
                formulario.repaint();
                break;



            case "eliminar":
                formulario.removeAll();
                formulario.setLayout(new BorderLayout());
                formulario.add(new PanelEliminar(usuarioService), BorderLayout.CENTER);
                formulario.revalidate();
                formulario.repaint();
                break;


            default:
                formulario.add(new JLabel("Seleccione una acción para comenzar."));
        }

        panelCampos.add(formulario);
        panelCampos.revalidate();
        panelCampos.repaint();
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void listarUsuarios() {
        panelCampos.removeAll();

        String filtro = (String) comboRolesFiltro.getSelectedItem();


        List<Usuario> usuarios = new ArrayList<>();
        try {
            usuarios = usuarioService.listarUsuarios(filtro);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al listar usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] columnas = {"ID", "Nombre", "Apellido", "DNI", "Rol"};


        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Usuario usuario : usuarios) {
            String descripcionRol = String.valueOf(usuario.getRol());


            if ("Todos".equalsIgnoreCase(filtro) || filtro.equalsIgnoreCase(descripcionRol)) {
                Object[] fila = {
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getDni(),
                        descripcionRol
                };
                modeloTabla.addRow(fila);
            }
        }

        JTable tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.setFillsViewportHeight(true);

        panelCampos.setLayout(new BorderLayout());
        panelCampos.add(new JLabel("Listado de usuarios (" + filtro + "):"), BorderLayout.NORTH);
        panelCampos.add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);

        panelCampos.revalidate();
        panelCampos.repaint();
    }


}
