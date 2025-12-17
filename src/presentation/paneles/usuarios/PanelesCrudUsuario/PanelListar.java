package presentation.paneles.usuarios.PanelesCrudUsuario;

import data.Model.Usuario;
import service.IUsuarioService;
import service.ServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelListar extends JPanel {

    public PanelListar(IUsuarioService usuarioService, String filtro) {
        setLayout(new BorderLayout());

        try {
            List<Usuario> usuarios = usuarioService.listarUsuarios(filtro);

            String[] columnas = {"ID", "Nombre", "Apellido", "DNI", "Rol"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
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
                    modelo.addRow(fila);
                }
            }

            JTable tabla = new JTable(modelo);
            add(new JLabel("Listado de usuarios (" + filtro + "):"), BorderLayout.NORTH);
            add(new JScrollPane(tabla), BorderLayout.CENTER);

        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al listar usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado al listar usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
