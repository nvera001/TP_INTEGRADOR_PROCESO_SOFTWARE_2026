package App;

import Controlador.GestorJuego;
import Modelo.Archivos.LectorTXT;
import Modelo.Entidades.Jugador;
import Modelo.Fabricas.GeneradorNivel;
import Modelo.Nucleo.Matriz;
import Vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Ejecución segura de interfaces Swing
        SwingUtilities.invokeLater(() -> {

            // 1. Instanciar el lector y cargar el mapa (agregadas las comillas)
            LectorTXT lector = new LectorTXT();
            lector.cargarArchivo("TP_La_Mango/src/Modelo/Mapas/mapa.txt");
            String datosDelMapa = lector.getCadena();

            // 2. Generar el nivel y guardar la Matriz resultante
            GeneradorNivel generador = new GeneradorNivel();
            Matriz tablero = generador.generarMatrizDesdeString(datosDelMapa);

            Jugador jugador = tablero.obtenerJugador();
            // 3. Inicializar el Controlador
            // (Asumiendo que tu GestorJuego necesita conocer el tablero para mover las cosas)
            GestorJuego gestor = new GestorJuego(tablero, jugador);

            // 4. Inicializar la Vista
            // Aquí usamos la firma de tu captura: recibe el gestor (para el teclado) y la matriz (para dibujar)
            VentanaPrincipal ventana = new VentanaPrincipal(gestor, tablero);

            // 5. Mostrar la ventana y pedir el foco para que el KeyListener funcione
            ventana.setVisible(true);
            ventana.requestFocusInWindow();

        });
    }
}