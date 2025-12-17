package presentation.paneles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelNavegacion extends JPanel {
    public JButton btnAdmin, btnPromotor, btnUsuario, btnDashboard;
    private JButton botonSeleccionado = null;

    public PanelNavegacion(ActionListener listener) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        btnAdmin = crearBoton("Administrador");
        btnPromotor = crearBoton("Promotor");
        btnUsuario = crearBoton("Usuario");
        btnDashboard = crearBoton("Dashboard");


        btnAdmin.setActionCommand("ADMIN");
        btnPromotor.setActionCommand("PROMOTOR");
        btnUsuario.setActionCommand("USUARIO");
        btnDashboard.setActionCommand("DASHBOARD");

        add(btnAdmin);
        add(Box.createRigidArea(new Dimension(0, 30))); // Espacio entre botones
        add(btnPromotor);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(btnUsuario);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(btnDashboard);

        // Enlazamos todos los botones al mismo listener externo
        btnAdmin.addActionListener(listener);
        btnPromotor.addActionListener(listener);
        btnUsuario.addActionListener(listener);
        btnDashboard.addActionListener(listener);

    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(150, 40));
        boton.setFocusPainted(false);
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
resaltarBoton(boton);
            }


        });

                botonSeleccionado = boton;

        return boton;
    }





    private void resaltarBoton(JButton botonNuevo) {
        if (botonSeleccionado != null) {

            botonSeleccionado.setBackground(Color.WHITE);
            botonSeleccionado.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }

        // Estilo del botón seleccionado
        botonNuevo.setBackground(Color.gray);
        botonNuevo.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        botonSeleccionado = botonNuevo;
    }
}




