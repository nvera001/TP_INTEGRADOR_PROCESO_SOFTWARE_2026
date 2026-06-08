package App;

import Controlador.GestorJuego;
import Modelo.Entidades.*;
import Modelo.Nucleo.Matriz;
import Modelo.Nucleo.Posicion;
import Vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Ejecución segura de interfaces Swing
        SwingUtilities.invokeLater(() -> {
            Matriz mapa = new Matriz(5, 5);

            // Armamos el escenario de prueba
            mapa.colocarObjeto(new ParedSimple(new Posicion(0, 0)));
            mapa.colocarObjeto(new ParedSimple(new Posicion(1, 0)));
            mapa.colocarObjeto(new ParedSimple(new Posicion(4, 4)));
            
            Jugador jugador = new Jugador(new Posicion(2, 2));
            mapa.colocarObjeto(jugador);
            mapa.colocarObjeto(new CajaSimple(new Posicion(3, 2)));

            // Instanciamos el controlador
            GestorJuego gestor = new GestorJuego(mapa, jugador);

            // Levantamos la vista
            VentanaPrincipal ventana = new VentanaPrincipal(gestor, mapa);
            gestor.setVentana(ventana); // Conectamos controlador -> vista
            
            ventana.setVisible(true);
        });
    }
}