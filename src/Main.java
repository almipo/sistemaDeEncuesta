
import javax.swing.SwingUtilities;
import presentation.Principal;

public class Main {
    public static void main(String[] args) {
        //para inicializar la base de datos si se borra
       //Conexion.inicializarBaseDeDatos();


//ejecucion del entorno grafico de presentacion
      SwingUtilities.invokeLater(() -> {
            Principal principal = new Principal();
            principal.setVisible(true);
        });

    }
}





















