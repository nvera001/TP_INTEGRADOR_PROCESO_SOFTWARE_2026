package Controlador;

import Modelo.Entidades.CajaSimple;
import Modelo.Entidades.GameObject;
import Modelo.Entidades.Jugador;
import Modelo.Nucleo.Direccion;
import Modelo.Nucleo.Matriz;
import Modelo.Nucleo.Posicion;
import Vista.VentanaPrincipal;
import javax.swing.JOptionPane; // Para el cartel de victoria

public class GestorJuego {
    private final Matriz matriz;
    private final Jugador jugador;
    private VentanaPrincipal ventana; 

    public GestorJuego(Matriz matriz, Jugador jugador) {
        this.matriz = matriz;
        this.jugador = jugador;
    }

    public void setVentana(VentanaPrincipal ventana) {
        this.ventana = ventana;
    }

    public void intentarMover(Direccion dir) {
        Posicion posJugador = jugador.getPosicion();
        Posicion proxPosJugador = posJugador.sumar(dir.getDeltaX(), dir.getDeltaY());

        GameObject destino = matriz.obtenerObjetoEn(proxPosJugador);

        // Caso 1: Espacio vacío
        if (destino == null) {
            matriz.moverObjeto(jugador, proxPosJugador);
        } 
        // Caso 2: Hay una caja
        else if (destino instanceof CajaSimple) {
            Posicion proxPosCaja = proxPosJugador.sumar(dir.getDeltaX(), dir.getDeltaY());
            GameObject destinoDeCaja = matriz.obtenerObjetoEn(proxPosCaja);

            // Si detrás de la caja está vacío, empujamos
            if (destinoDeCaja == null) {
                matriz.moverObjeto(destino, proxPosCaja);    // Mueve la caja
                matriz.moverObjeto(jugador, proxPosJugador); // Mueve al jugador
            }
        }
        // Caso 3: Pared (destino instanceof ParedSimple) -> No hace nada

        // Si la ventana ya existe, le avisamos que el modelo cambió para que redibuje
        if (ventana != null) {
            ventana.actualizarPantalla();
        }

        if (matriz.estanTodasLasMetasCubiertas()) {
            // Mostramos un cartel emergente nativo de Swing
            JOptionPane.showMessageDialog(ventana, "¡Ganaste el nivel! Felicitaciones.", "Victoria", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
}