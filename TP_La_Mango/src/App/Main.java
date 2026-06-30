package App;

import Controlador.GestorJuego;
import Vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestorJuego gestor = new GestorJuego();
            VentanaPrincipal ventana = new VentanaPrincipal(gestor);
            gestor.setVentana(ventana);
        });
    }
}