package App;

import Controlador.GestorJuego;
import Vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Instanciamos el motor lógico maestro
            GestorJuego gestor = new GestorJuego();

            // Levantamos la ventana pasándole el gestor
            VentanaPrincipal ventana = new VentanaPrincipal(gestor);

            // Conectamos de vuelta hacia la vista
            gestor.setVentana(ventana);
        });
    }
}