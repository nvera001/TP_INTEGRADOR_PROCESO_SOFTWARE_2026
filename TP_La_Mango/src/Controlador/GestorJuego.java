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
import Vista.GestorAudio;
import Vista.DialogoVictoria;

public class GestorJuego {

    private final String[] rutasNiveles = {
            "/Modelo/Mapas/mapa.txt",
            "/Modelo/Mapas/mapa2.txt",
            "/Modelo/Mapas/mapa3.txt"
    };
    private int indiceNivelActual = 0;
    private Matriz matriz;
    private Jugador jugador;
    private VentanaPrincipal ventana;

    private final LectorTXT lector;
    private final GeneradorNivel generador;

    private int movimientosNivel = 0;
    private int empujesNivel = 0;
    private final GestorAudio audio;

    private int segundosTranscurridos = 0;
    private javax.swing.Timer timerReloj;

    public GestorJuego() {
        this.lector = new LectorTXT();
        this.generador = new GeneradorNivel();
        this.audio = new GestorAudio(); // Inicializamos el audio
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
            volverAlMenu();
            return;
        }

        // Reseteamos estadísticas físicas
        this.movimientosNivel = 0;
        this.empujesNivel = 0;

        // REINICIAR EL RELOJ DESDE CERO
        this.segundosTranscurridos = 0;
        if (timerReloj != null) {
            timerReloj.stop(); // Frena el timer del nivel anterior si existía
        }

        // Creamos el cronómetro: cada 1000ms suma un segundo y actualiza el HUD en pantalla
        timerReloj = new javax.swing.Timer(1000, e -> {
            segundosTranscurridos++;
            if (ventana != null) {
                ventana.actualizarHUD(); // Le avisa a la barra superior que cambie el tiempo
            }
        });
        timerReloj.start(); // Arranca el tiempo

        // Leemos el archivo y cargamos la matriz (Esto queda igual)
        lector.cargarArchivo(rutasNiveles[indice]);
        String datos = lector.getCadena();
        this.matriz = generador.generarMatrizDesdeString(datos);
        this.jugador = matriz.obtenerJugador();

        if (this.jugador == null) {
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

        boolean seMovio = false;

        if (destino == null) {
            matriz.moverObjeto(jugador, proxPosJugador);
            movimientosNivel++; // Suma al HUD en vivo
            seMovio = true;
            audio.reproducirSonido("paso.wav");
        }

        else if (destino instanceof CajaSimple) {
            Posicion proxPosCaja = proxPosJugador.sumar(dir.getDeltaX(), dir.getDeltaY());
            GameObject destinoDeCaja = matriz.obtenerObjetoEn(proxPosCaja);

            if (destinoDeCaja == null) {
                matriz.moverObjeto(destino, proxPosCaja);    // Mueve la caja
                matriz.moverObjeto(jugador, proxPosJugador); // Mueve al jugador
                movimientosNivel++; // Suma movimiento al HUD
                empujesNivel++;     // Suma empuje al HUD
                seMovio = true;
                audio.reproducirSonido("empuje.wav");
            }
        }

        if (ventana != null && seMovio) {
            ventana.actualizarPantalla();
            ventana.actualizarHUD();
        }

        if (matriz.estanTodasLasMetasCubiertas()) {
            if (timerReloj != null) {
                timerReloj.stop();
            }
            audio.reproducirSonido("victoria.wav");

            DialogoVictoria cartelVictoria = new DialogoVictoria(
                    ventana,
                    getNivelActual(),
                    movimientosNivel,
                    empujesNivel,
                    getTiempoFormateado(),
                    () -> {
                        indiceNivelActual++;
                        cargarNivel(indiceNivelActual);
                    }
            );
            cartelVictoria.setVisible(true);
        }
    }

    // 4. AGREGÁ ESTOS MÉTODOS ABAJO DE TODO (Para que la ventana pueda chusmear los datos en vivo)
    public int getNivelActual() {
        return indiceNivelActual + 1;
    }

    public int getMovimientos() {
        return movimientosNivel;
    }

    public int getEmpujes() {
        return empujesNivel;
    }

    public String getTiempoFormateado() {
        int horas = segundosTranscurridos / 3600;
        int minutos = (segundosTranscurridos % 3600) / 60;
        int segundos = segundosTranscurridos % 60;
        // Devuelve exactamente el formato de tu foto: H:MM:SS
        return String.format("%d:%02d:%02d", horas, minutos, segundos);
    }
}