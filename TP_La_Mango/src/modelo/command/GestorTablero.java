package modelo.command;

import controlador.ContadorEstadisticas;
import controlador.GestorJuego;
import modelo.nucleo.Matriz;
import modelo.nucleo.Direccion;
import modelo.nucleo.Posicion;
import modelo.entidades.GameObject;
import modelo.entidades.Jugador;
import modelo.entidades.Caja;
import vista.VentanaPrincipal;
import vista.GestorAudio;

public class GestorTablero {
    private final Matriz matriz;
    private final GestorHistorial gestor;

    private final ContadorEstadisticas estadisticas;
    private final GestorAudio audio;
    private final VentanaPrincipal ventana;
    private final GestorJuego juegoGlobal;
    private boolean estaDeslizando = false;

    public GestorTablero(Matriz matriz, ContadorEstadisticas estadisticas, GestorAudio audio, VentanaPrincipal ventana, GestorJuego juegoGlobal) {
        this.matriz = matriz;
        this.estadisticas = estadisticas;
        this.audio = audio;
        this.ventana = ventana;
        this.juegoGlobal = juegoGlobal;
        this.gestor = new GestorHistorial(estadisticas);
    }

    public void intentarMover(Direccion dir) {
        if (estaDeslizando || matriz == null) return;

        Jugador jugador = Jugador.getInstancia();
        Posicion viejaPosJugador = jugador.getPosicion();
        GameObject destino = matriz.obtenerObjetoEn(viejaPosJugador.sumar(dir.getDeltaX(), dir.getDeltaY()));
        Posicion viejaPosCaja = (destino instanceof Caja) ? destino.getPosicion() : null;
        Posicion proxPosJugador = jugador.getPosicion().sumar(dir.getDeltaX(), dir.getDeltaY());

        boolean seMovio = false;
        boolean seRompioCaja = false;

        if (destino == null) {
            matriz.moverObjeto(jugador, proxPosJugador);
            estadisticas.registrarMovimiento();
            seMovio = true;
            audio.reproducirSonido("paso.wav");
            gestor.guardar(new ComandoMovimiento(jugador, viejaPosJugador, null, null, false));
        }
        else if (destino instanceof Caja) {
            Caja caja = (Caja) destino;
            Posicion proxPosCaja = proxPosJugador.sumar(dir.getDeltaX(), dir.getDeltaY());

            if (caja.serEmpujada(dir, matriz, destino)) {
                matriz.moverObjeto(jugador, proxPosJugador);
                estadisticas.registrarMovimiento();
                estadisticas.registrarEmpuje();
                seMovio = true;
                audio.reproducirSonido("empuje.wav");

                if (matriz.obtenerObjetoEn(proxPosCaja) == null) { seRompioCaja = true; }

                gestor.guardar(new ComandoMovimiento(jugador, viejaPosJugador, destino, viejaPosCaja, seRompioCaja));

                if (matriz.esResbaladizo(destino.getPosicion())) { ejecutarDeslizamientoAnimado(destino, dir); }
            }
        }

        if (ventana != null && seMovio) {
            ventana.actualizarPantalla();
            ventana.actualizarHUD();
        }

        if (matriz.estanTodasLasMetasCubiertas()) {
            juegoGlobal.finalizarNivel();
        }
    }

    public void deshacer() {
        if (!gestor.puedeDeshacer()) return;

        gestor.deshacerUltimos5(matriz);

        if (gestor.getUsosUndoTotal() >= 3 && ventana != null) {
            ventana.setUndoVisible(false);
        }

        if (ventana != null) {
            ventana.actualizarPantalla();
            ventana.actualizarHUD();
        }
    }

    public GestorHistorial getGestorHistorial() { return gestor; }

    private void ejecutarDeslizamientoAnimado(GameObject caja, Direccion dir) {
        estaDeslizando = true;
        javax.swing.Timer timerGelo = new javax.swing.Timer(100, null);
        timerGelo.addActionListener(e -> {
            if (matriz.esResbaladizo(caja.getPosicion())) {
                Posicion siguientePos = caja.getPosicion().sumar(dir.getDeltaX(), dir.getDeltaY());
                GameObject obstaculo = matriz.obtenerObjetoEn(siguientePos);

                if (obstaculo == null) {
                    matriz.moverObjeto(caja, siguientePos);
                    if (ventana != null) { ventana.actualizarPantalla(); }
                } else {
                    timerGelo.stop();
                    estaDeslizando = false;
                }
            } else {
                timerGelo.stop();
                estaDeslizando = false;
            }
        });
        timerGelo.start();
    }
}