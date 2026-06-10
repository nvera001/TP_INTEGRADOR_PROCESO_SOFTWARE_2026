package Controlador;

import Modelo.Archivos.LectorTXT;
import Modelo.Fabricas.GeneradorNivel;
import Modelo.Nucleo.Matriz;
import Modelo.Nucleo.Posicion;
import Modelo.Nucleo.Direccion;
import Modelo.Entidades.GameObject;
import Modelo.Entidades.Jugador;
import Modelo.Entidades.CajaSimple;
import Vista.VentanaPrincipal;
import javax.swing.JOptionPane; // Para el cartel de victoria

public class GestorJuego {

    private final String[] rutasNiveles = {
            "/Modelo/Mapas/mapa.txt",
            "/Modelo/Mapas/mapa2.txt"
    };
    private int indiceNivelActual = 0;
    private Matriz matriz;
    private Jugador jugador;
    private VentanaPrincipal ventana;

    private final LectorTXT lector;
    private final GeneradorNivel generador;

    public GestorJuego() {
        this.lector = new LectorTXT();
        this.generador = new GeneradorNivel();
    }

    public void setVentana(VentanaPrincipal ventana) {
        this.ventana = ventana;
    }

    // --- CONTROL DE FLUJO DEL JUEGO ---

    public void iniciarJuego() {
        indiceNivelActual = 0;
        cargarNivel(indiceNivelActual);
    }

    public void reiniciarNivel() {
        System.out.println("Reiniciando nivel actual...");
        cargarNivel(indiceNivelActual);
    }

    public void volverAlMenu() {
        if (ventana != null) {
            ventana.mostrarMenuPrincipal();
        }
    }

    private void cargarNivel(int indice) {
        if (indice >= rutasNiveles.length) {
            JOptionPane.showMessageDialog(ventana, "¡FELICITACIONES! Completaste todos los niveles de TP La Mango.", "¡Victoria Total!", JOptionPane.INFORMATION_MESSAGE);
            volverAlMenu();
            return;
        }

        // Leemos el archivo usando el Classpath portable
        lector.cargarArchivo(rutasNiveles[indice]);
        String datos = lector.getCadena();

        this.matriz = generador.generarMatrizDesdeString(datos);
        this.jugador = matriz.obtenerJugador();

        if (this.jugador == null) {
            JOptionPane.showMessageDialog(ventana, "Error crítico: El mapa no tiene jugador de salida.", "Error", JOptionPane.ERROR_MESSAGE);
            volverAlMenu();
            return;
        }

        if (ventana != null) {
            ventana.mostrarPantallaJuego(this.matriz);
        }
    }

    public void intentarMover(Direccion dir) {
        if (jugador == null || matriz == null) return;

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
            indiceNivelActual++;
            cargarNivel(indiceNivelActual);
        }
    }
    
}