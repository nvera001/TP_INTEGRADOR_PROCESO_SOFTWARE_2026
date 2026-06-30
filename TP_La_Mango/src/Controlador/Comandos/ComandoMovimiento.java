package Controlador.Comandos;

import Modelo.Entidades.GameObject;
import Modelo.Nucleo.Posicion;
import Modelo.Nucleo.Matriz;
import Modelo.Decoradores.CajaFragil;

public class ComandoMovimiento implements Comando {
    private final GameObject jugador;
    private final Posicion posViejaJugador;

    private final GameObject caja; // null si el jugador se movió solo
    private final Posicion posViejaCaja;
    private final boolean seRompioCaja;

    public ComandoMovimiento(GameObject jugador, Posicion posViejaJugador,
                             GameObject caja, Posicion posViejaCaja, boolean seRompioCaja) {
        this.jugador = jugador;
        this.posViejaJugador = posViejaJugador;
        this.caja = caja;
        this.posViejaCaja = posViejaCaja;
        this.seRompioCaja = seRompioCaja;
    }

    @Override
    public void deshacer(Matriz matriz) {
        // 1. PRIMERO movemos al jugador para que libere el casillero donde estaba parado
        matriz.moverObjeto(jugador, posViejaJugador);

        // 2. SEGUNDO procesamos la caja sobre el casillero que ya quedó limpio
        if (caja != null) {
            // Si es frágil, le devolvemos el punto de resistencia que gastó
            if (caja instanceof CajaFragil) {
                ((CajaFragil) caja).incrementarResistencia();
            }

            if (seRompioCaja) {
                // Si se había pulverizado, la revivimos en su posición vieja
                caja.setPosicion(posViejaCaja);
                matriz.colocarObjeto(caja);
            } else {
                // Si solo se había movido, la desplazamos de vuelta hacia atrás
                matriz.moverObjeto(caja, posViejaCaja);
            }
        }
    }

    @Override
    public boolean esEmpuje() {
        return caja != null;
    }
}