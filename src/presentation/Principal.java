package presentation;
    import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import presentation.paneles.*;
    import presentation.paneles.usuarios.PanelUsuario;


public class Principal extends JFrame implements ActionListener {

        private JPanel panelDerecho;

        public Principal() {
            setTitle("Sistema de Encuestas");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // Panel de navegación izquierdo
            PanelNavegacion panelIzquierdo = new PanelNavegacion(this);

            // Panel dinámico derecho
            panelDerecho = new JPanel(new BorderLayout());

            // Agregamos los paneles
            add(panelIzquierdo, BorderLayout.WEST);
            add(panelDerecho, BorderLayout.CENTER);

            setVisible(true);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();
            panelDerecho.removeAll();

            switch (comando) {
                case "USUARIO":
                    panelDerecho.add(new PanelUsuario(), BorderLayout.CENTER);
                    break;
                case "ADMIN":
                    panelDerecho.add(new JLabel("Panel Administrador (a implementar)"), BorderLayout.CENTER);
                    break;
                case "PROMOTOR":
                    panelDerecho.add(new JLabel("Panel Promotor (a implementar)"), BorderLayout.CENTER);
                    break;
            }

            panelDerecho.revalidate();
            panelDerecho.repaint();
        }




}
