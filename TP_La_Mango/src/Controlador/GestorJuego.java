package Controlador;

import Modelo.Archivos.LectorTXT;
import Modelo.Entidades.Caja;
import Modelo.Fabricas.GeneradorNivel;
import Modelo.Nucleo.Matriz;
import Modelo.Nucleo.Posicion;
import Modelo.Nucleo.Direccion;
import Modelo.Entidades.GameObject;
import Modelo.Entidades.Jugador;
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

        this.movimientosNivel = 0;
        this.empujesNivel = 0;

        this.segundosTranscurridos = 0;
        if (timerReloj != null) {
            timerReloj.stop();
        }

        timerReloj = new javax.swing.Timer(1000, e -> {
            segundosTranscurridos++;
            if (ventana != null) {
                ventana.actualizarHUD();
            }
        });
        timerReloj.start();

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
            movimientosNivel++;
            seMovio = true;
            audio.reproducirSonido("paso.wav");
        }

        else if (destino instanceof Caja) {
            Caja caja = (Caja) destino;

            if (caja.serEmpujada(dir, matriz, destino)) {
                matriz.moverObjeto(jugador, proxPosJugador); // Mueve al jugador
                movimientosNivel++;
                empujesNivel++;
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

            // Corregido: Usamos una clase anónima para pasar las dos acciones obligatorias
            DialogoVictoria cartelVictoria = new DialogoVictoria(
                    ventana,
                    getNivelActual(),
                    movimientosNivel,
                    empujesNivel,
                    getTiempoFormateado(),
                    new DialogoVictoria.AccionVictoria() {
                        @Override
                        public void avanzarSiguienteNivel() {
                            indiceNivelActual++;
                            cargarNivel(indiceNivelActual);
                        }

                        @Override
                        public void volverAlMenu() {
                            GestorJuego.this.volverAlMenu();
                        }
                    }
            );
            cartelVictoria.setVisible(true);
        }
    }

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
        return String.format("%d:%02d:%02d", horas, minutos, segundos);
    }
}