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

import Controlador.Comandos.Comando;
import Controlador.Comandos.ComandoMovimiento;
import java.util.Stack;

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

    private final Stack<Comando> historial = new Stack<>();
    private int usosUndoTotalNivel = 0;

    private int cantUndoNivel = 0;

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
        this.cantUndoNivel = 0;

        if (indice >= rutasNiveles.length) {
            volverAlMenu();
            return;
        }

        this.historial.clear();
        this.usosUndoTotalNivel = 0;
        if (ventana != null) {
            ventana.setUndoVisible(true); // Se vuelve a mostrar al iniciar/reiniciar el nivel
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

        Posicion viejaPosJugador = jugador.getPosicion();
        GameObject destino = matriz.obtenerObjetoEn(viejaPosJugador.sumar(dir.getDeltaX(), dir.getDeltaY()));
        Posicion viejaPosCaja = (destino instanceof Caja) ? destino.getPosicion() : null;

        Posicion posJugador = jugador.getPosicion();
        Posicion proxPosJugador = posJugador.sumar(dir.getDeltaX(), dir.getDeltaY());

        boolean seMovio = false;
        boolean seRompioCaja = false;

        if (destino == null) {
            matriz.moverObjeto(jugador, proxPosJugador);
            movimientosNivel++;
            seMovio = true;
            audio.reproducirSonido("paso.wav");

            registrarComando(new ComandoMovimiento(jugador, viejaPosJugador, null, null, false));
        }

        else if (destino instanceof Caja) {
            Caja caja = (Caja) destino;
            Posicion proxPosCaja = proxPosJugador.sumar(dir.getDeltaX(), dir.getDeltaY());

            if (caja.serEmpujada(dir, matriz, destino)) {
                matriz.moverObjeto(jugador, proxPosJugador); // Mueve al jugador
                movimientosNivel++;
                empujesNivel++;
                seMovio = true;
                audio.reproducirSonido("empuje.wav");

                if (matriz.obtenerObjetoEn(proxPosCaja) == null) {
                    seRompioCaja = true;
                }

                // Guardamos comando de empuje
                registrarComando(new ComandoMovimiento(jugador, viejaPosJugador, destino, viejaPosCaja, seRompioCaja));
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
                    this.cantUndoNivel,    // NUEVO: Cantidad de undos
                    calcularPuntaje(),     // NUEVO: Puntaje calculado
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

    private void registrarComando(Comando cmd) {
        historial.push(cmd);
        // Si nos pasamos del tope de la consigna (15), descartamos el movimiento más antiguo
        if (historial.size() > 15) {
            historial.remove(0);
        }
    }

    public void deshacerCincoMovimientos() {
        // Validación estricta: Si ya se usó 3 veces, el método se corta de inmediato
        if (usosUndoTotalNivel >= 3) {
            System.out.println("Límite de Deshacer alcanzado para este nivel.");
            return;
        }
        if (historial.isEmpty()) {
            System.out.println("No hay movimientos para deshacer.");
            return;
        }

        // Sacamos hasta 5 comandos del historial
        int pasosA_Deshacer = Math.min(5, historial.size());

        for (int i = 0; i < pasosA_Deshacer; i++) {
            Comando cmd = historial.pop();
            cmd.deshacer(matriz);

            // Restamos las estadísticas del HUD
            movimientosNivel--;
            if (cmd.esEmpuje()) {
                empujesNivel--;
            }
        }

        usosUndoTotalNivel++; // Registramos el uso (1, 2 o 3)
        this.cantUndoNivel++;
        System.out.println("Undo ejecutado con éxito. Uso número: " + usosUndoTotalNivel);

        // REGLA CLAVE: Si llegó al tercer uso, ocultamos el botón de inmediato
        if (usosUndoTotalNivel >= 3 && ventana != null) {
            ventana.setUndoVisible(false);
            System.out.println("¡Botón de Deshacer agotado y ocultado!");
        }

        // Refrescamos los datos en pantalla
        if (ventana != null) {
            ventana.actualizarPantalla();
            ventana.actualizarHUD();
        }
    }
    public int calcularPuntaje() {
        int base = 5000;
        int penalizacion = (movimientosNivel * 10) + (empujesNivel * 20) + (cantUndoNivel * 100);
        // Retorna el cálculo, asegurando un piso mínimo de 100 puntos
        return Math.max(100, base - penalizacion);
    }

}