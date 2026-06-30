package controlador;

import modelo.archivos.LectorTXT;
import modelo.entidades.Caja;
import modelo.builder.GeneradorNivel;
import modelo.nucleo.Matriz;
import modelo.nucleo.Posicion;
import modelo.nucleo.Direccion;
import modelo.entidades.GameObject;
import modelo.entidades.Jugador;

import vista.VentanaPrincipal;
import vista.GestorAudio;
import vista.DialogoVictoria;

import controlador.comandos.ComandoMovimiento;

public class GestorJuego {
    private final String[] rutasNiveles = {
            "/modelo/mapas/mapa.txt",
            "/modelo/mapas/mapa2.txt",
            "/modelo/mapas/mapa3.txt"
    };

    private final GestorHistorial historialManager;
    private final ContadorEstadisticas estadisticas;

    private int indiceNivelActual = 0;
    private Matriz matriz;
    private VentanaPrincipal ventana;

    private final LectorTXT lector;
    private final GeneradorNivel generador;
    private final GestorAudio audio;

    private javax.swing.Timer timerReloj;
    private boolean estaDeslizando = false;

    public GestorJuego() {
        this.lector = new LectorTXT();
        this.generador = new GeneradorNivel();
        this.audio = new GestorAudio();

        this.historialManager = new GestorHistorial();
        this.estadisticas = new ContadorEstadisticas();
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

        this.historialManager.reset();
        this.estadisticas.reset();

        if (ventana != null) {ventana.setUndoVisible(true);}

        if (timerReloj != null) {timerReloj.stop();}

        timerReloj = new javax.swing.Timer(1000, e -> {
            estadisticas.incrementarSegundo();
            if (ventana != null) {
                ventana.actualizarHUD();
            }
        });
        timerReloj.start();

        lector.cargarArchivo(rutasNiveles[indice]);
        String datos = lector.getCadena();

        this.matriz = generador.generarMatrizDesdeString(datos);

        if (ventana != null) {ventana.mostrarPantallaJuego(this.matriz);}
    }

    public void intentarMover(Direccion dir) {
        if (estaDeslizando) return;
        if (matriz == null) return;

        Jugador jugador = Jugador.getInstancia();

        Posicion viejaPosJugador = jugador.getPosicion();
        GameObject destino = matriz.obtenerObjetoEn(viejaPosJugador.sumar(dir.getDeltaX(), dir.getDeltaY()));
        Posicion viejaPosCaja = (destino instanceof Caja) ? destino.getPosicion() : null;

        Posicion posJugador = jugador.getPosicion();
        Posicion proxPosJugador = posJugador.sumar(dir.getDeltaX(), dir.getDeltaY());

        boolean seMovio = false;
        boolean seRompioCaja = false;

        if (destino == null) {
            matriz.moverObjeto(jugador, proxPosJugador);
            estadisticas.registrarMovimiento();
            seMovio = true;
            audio.reproducirSonido("paso.wav");

            historialManager.registrarComando(new ComandoMovimiento(jugador, viejaPosJugador, null, null, false));
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

                if (matriz.obtenerObjetoEn(proxPosCaja) == null) {seRompioCaja = true;}

                historialManager.registrarComando(new ComandoMovimiento(jugador, viejaPosJugador, destino, viejaPosCaja, seRompioCaja));

                if (matriz.esResbaladizo(destino.getPosicion())) {ejecutarDeslizamientoAnimado(destino, dir);}
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
                    estadisticas.getMovimientos(),
                    estadisticas.getEmpujes(),
                    historialManager.getCantUndoNivel(),
                    calcularPuntaje(),
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

    public void deshacerCincoMovimientos() {
        if (!historialManager.puedeDeshacer()) {
            return;
        }

        historialManager.deshacerBloque(matriz, estadisticas);

        System.out.println("Undo ejecutado con éxito. Uso número: " + historialManager.getUsosUndoTotal());

        if (historialManager.getUsosUndoTotal() >= 3 && ventana != null) {
            ventana.setUndoVisible(false);
            System.out.println("¡Botón de Deshacer agotado y ocultado!");
        }

        if (ventana != null) {
            ventana.actualizarPantalla();
            ventana.actualizarHUD();
        }
    }

    public int getNivelActual() {
        return indiceNivelActual + 1;
    }

    public int getMovimientos() {
        return estadisticas.getMovimientos();
    }

    public int getEmpujes() {
        return estadisticas.getEmpujes();
    }

    public String getTiempoFormateado() {
        return estadisticas.getTiempoFormateado();
    }

    public int calcularPuntaje() {
        return estadisticas.calcularPuntaje(historialManager.getCantUndoNivel());
    }

    private void ejecutarDeslizamientoAnimado(GameObject caja, Direccion dir) {
        estaDeslizando = true;

        javax.swing.Timer timerGelo = new javax.swing.Timer(100, null);
        timerGelo.addActionListener(e -> {
            if (matriz.esResbaladizo(caja.getPosicion())) {
                Posicion siguientePos = caja.getPosicion().sumar(dir.getDeltaX(), dir.getDeltaY());
                GameObject obstaculo = matriz.obtenerObjetoEn(siguientePos);

                if (obstaculo == null) {
                    matriz.moverObjeto(caja, siguientePos);

                    if (ventana != null) {
                        ventana.actualizarPantalla();
                    }
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