package presentation.paneles.administrador;

import javax.swing.*;
import java.awt.*;

import data.Model.RolUsuario;
import presentation.paneles.administrador.panelesEncuesta.PanelEliminarEncuesta;
import service.*;
import service.encuestaService.EncuestaService;
import service.encuestaService.OpcionRespuestaService;
import service.encuestaService.PreguntaService;
import service.usuariosServices.UsuarioService;
import presentation.paneles.administrador.panelesEncuesta.PanelCrearEncuesta;
import presentation.paneles.administrador.panelesEncuesta.PanelModificarEncuesta;
import presentation.paneles.administrador.panelesEncuesta.PanelListarEncuestas;

public class PanelAdministrador extends JPanel {

    private JTextField txtIdUsuario;
    private JButton btnValidar;
    private JPanel panelOpciones;
    private IUsuarioService usuarioService;
    private IEncuestaService encuestaService;
    private boolean administradorValidado = false;
    private IPreguntaService preguntaService = new PreguntaService();
  private IOpcionRespuestaService opcionRespuestaService =    new OpcionRespuestaService();
    private int idAdministrador;
private IPreguntaCompuestaService preguntaCompuesta ;

    public PanelAdministrador() {
        this.usuarioService = new UsuarioService();
        this.encuestaService = new EncuestaService();

        setLayout(new BorderLayout());

        // Panel de ingreso ID
        JPanel panelIngreso = new JPanel();
        panelIngreso.add(new JLabel("Ingrese ID de Administrador:"));
        txtIdUsuario = new JTextField(10);
        btnValidar = new JButton("Validar");
        panelIngreso.add(txtIdUsuario);
        panelIngreso.add(btnValidar);
        add(panelIngreso, BorderLayout.NORTH);

        // Panel opciones (inicialmente oculto)
        panelOpciones = new JPanel(new FlowLayout());
        panelOpciones.setVisible(false);

        JButton btnCrear = new JButton("Crear Encuesta");
        JButton btnModificar = new JButton("Modificar Encuesta");
        JButton btnEliminar = new JButton("Eliminar Encuesta");
        JButton btnVer = new JButton("Ver Encuestas");

        panelOpciones.add(btnCrear);
        panelOpciones.add(btnModificar);
        panelOpciones.add(btnEliminar);
        panelOpciones.add(btnVer);

        add(panelOpciones, BorderLayout.SOUTH);

        // Listener validar
        btnValidar.addActionListener(e -> validarAdministrador());

        // Listener crear encuesta: abre panelcrearencuesta en nuevo JFrame
        btnCrear.addActionListener(e -> abrirPanelCrearEncuesta());
        btnModificar.addActionListener(e -> abrirPanelModificarEncuesta());
        btnEliminar.addActionListener(e -> abrirPanelEliminarEncuesta());
        btnVer.addActionListener(e -> abrirPanelListarEncuestas());


    }

    private void validarAdministrador() {
        String input = txtIdUsuario.getText().trim();
        try {
            int id = Integer.parseInt(input);
            RolUsuario esAdmin = usuarioService.obtenerRol(id);

            if (esAdmin == RolUsuario.ADMINISTRADOR) {
                JOptionPane.showMessageDialog(this, "Acceso concedido. Bienvenido Administrador.");
                this.idAdministrador = id;
                this.administradorValidado=true;
                panelOpciones.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Este usuario no es un administrador.");
                panelOpciones.setVisible(false);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido. Ingrese un número.");
        } catch (ServiceException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al validar usuario: " + ex.getMessage());
        }
    }

    private void abrirPanelCrearEncuesta() {
        if (!administradorValidado) {
            JOptionPane.showMessageDialog(this, "Primero valide un administrador válido.");
            return;
        }

        JFrame frameCrearEncuesta = new JFrame("Crear Nueva Encuesta");
        frameCrearEncuesta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameCrearEncuesta.setSize(500, 150);
        frameCrearEncuesta.setLocationRelativeTo(null);

        // Pasamos idAdministrador y el servicio encuestaService
        PanelCrearEncuesta panelCrearEncuesta = new PanelCrearEncuesta(idAdministrador, encuestaService);
        frameCrearEncuesta.setContentPane(panelCrearEncuesta);

        frameCrearEncuesta.setVisible(true);
    }
    private void abrirPanelModificarEncuesta() {
        if (!administradorValidado) {
            JOptionPane.showMessageDialog(null, "Debe validar al administrador primero");
            return;
        }

        JFrame frameModificarEncuesta = new JFrame("Modificar Encuesta");
        frameModificarEncuesta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameModificarEncuesta.setSize(600, 300);
        frameModificarEncuesta.setLocationRelativeTo(null);

        PanelModificarEncuesta panelModificar = new PanelModificarEncuesta( encuestaService, preguntaService, opcionRespuestaService);
        frameModificarEncuesta.setContentPane(panelModificar);

        frameModificarEncuesta.setVisible(true);
    }

    private void abrirPanelEliminarEncuesta() {
       if (!administradorValidado) {
           JOptionPane.showMessageDialog(this, "Primero valide un administrador válido.");
           return;
       }

       JFrame frameEliminarEncuesta = new JFrame("Eliminar Encuesta");
       frameEliminarEncuesta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       frameEliminarEncuesta.setSize(400, 120);
       frameEliminarEncuesta.setLocationRelativeTo(null);

       PanelEliminarEncuesta panelEliminar = new PanelEliminarEncuesta(encuestaService);
       frameEliminarEncuesta.setContentPane(panelEliminar);

       frameEliminarEncuesta.setVisible(true);
   }

    private void abrirPanelListarEncuestas() {
        if (!administradorValidado) {
            JOptionPane.showMessageDialog(this, "Primero valide un administrador válido.");
            return;
        }

        JFrame frameListarEncuestas = new JFrame("Resumen de Encuestas");
        frameListarEncuestas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameListarEncuestas.setSize(700, 500);
        frameListarEncuestas.setLocationRelativeTo(null);

        PanelListarEncuestas panelResumen = new PanelListarEncuestas(encuestaService, idAdministrador);
        frameListarEncuestas.setContentPane(panelResumen);

        frameListarEncuestas.setVisible(true);
    }

}
