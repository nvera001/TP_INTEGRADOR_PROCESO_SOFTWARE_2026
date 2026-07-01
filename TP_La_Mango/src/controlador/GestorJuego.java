package controlador;

import modelo.archivos.LectorTXT;
import modelo.nucleo.Posicion;
import modelo.builder.GeneradorNivel;
import modelo.nucleo.Matriz;
import modelo.nucleo.Direccion;
import vista.VentanaPrincipal;
import vista.GestorAudio;
import vista.DialogoVictoria;
import modelo.command.GestorTablero;
import modelo.memento.MaquinaDelTiempo;

public class GestorJuego {
    private final String[] rutasNiveles = {
            "/modelo/mapas/mapa.txt",
            "/modelo/mapas/mapa2.txt",
            "/modelo/mapas/mapa3.txt"
    };

    private Matriz matrizActual;
    private modelo.memento.MaquinaDelTiempo maquinaDelTiempo;

    private int indiceNivelActual = 0;
    private VentanaPrincipal ventana;
    private final LectorTXT lector;
    private final GeneradorNivel generador;
    private final GestorAudio audio;
    private final ContadorEstadisticas estadisticas;

    private javax.swing.Timer timerReloj;
    private GestorTablero gestorTablero;

    public GestorJuego() {
        this.lector = new LectorTXT();
        this.generador = new GeneradorNivel();
        this.audio = new GestorAudio();
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
        this.estadisticas.reset();
        if (ventana != null) {
            ventana.setUndoVisible(true);
        }
        if (timerReloj != null) {
            timerReloj.stop();
        }

        timerReloj = new javax.swing.Timer(1000, e -> {
            estadisticas.incrementarSegundo();
            if (ventana != null) {
                ventana.actualizarHUD();
            }
        });
        timerReloj.start();

        lector.cargarArchivo(rutasNiveles[indice]);
        String datos = lector.getCadena();

        this.matrizActual = generador.generarMatrizDesdeString(datos);

        this.maquinaDelTiempo = new MaquinaDelTiempo(this.matrizActual);

        this.gestorTablero = new GestorTablero(this.matrizActual, estadisticas, audio, ventana, this);

        if (ventana != null) {
            ventana.mostrarPantallaJuego(this.matrizActual);
        }
    }

    public void intentarMover(Direccion dir) {
        if (gestorTablero != null) {
            // 1. Ejecuta el movimiento regular del jugador/cajas
            gestorTablero.intentarMover(dir);

            // 2. ESCANEO DE MONEDA: ¿El jugador pisó el checkpoint 'O'?
            Posicion posJugador = matrizActual.obtenerPosicionJugador();
            if (posJugador != null && matrizActual.esMonedaFoto(posJugador)) {
                maquinaDelTiempo.registrarFoto();
            }
        }
    }

    public void viajarEnElTiempo() {
        if (maquinaDelTiempo != null) {
            maquinaDelTiempo.restaurarCajas();
            audio.reproducirSonido("resbaladizo.wav"); // Sonido especial de viaje temporal

            if (ventana != null) {
                ventana.actualizarPantalla();
                ventana.actualizarHUD();
            }
        }
    }

    public void deshacerCincoMovimientos() {
        if (gestorTablero != null) {
            gestorTablero.deshacer();
        }
    }

    public void finalizarNivel() {
        if (timerReloj != null) { timerReloj.stop(); }
        audio.reproducirSonido("victoria.wav");

        DialogoVictoria cartelVictoria = new DialogoVictoria(
                ventana,
                getNivelActual(),
                estadisticas.getMovimientos(),
                estadisticas.getEmpujes(),
                gestorTablero.getGestorHistorial().getCantUndoNivel(),
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
    public int getNivelActual() { return indiceNivelActual + 1; }
    public int getMovimientos() { return estadisticas.getMovimientos(); }
    public int getEmpujes() { return estadisticas.getEmpujes(); }
    public String getTiempoFormateado() { return estadisticas.getTiempoFormateado(); }

    public int calcularPuntaje() {
        int undos = (gestorTablero != null) ? gestorTablero.getGestorHistorial().getCantUndoNivel() : 0;
        return estadisticas.calcularPuntaje(undos);
    }
}