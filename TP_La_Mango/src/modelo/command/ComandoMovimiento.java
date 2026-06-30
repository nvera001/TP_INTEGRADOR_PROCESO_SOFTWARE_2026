package modelo.command;

import modelo.entidades.GameObject;
import modelo.nucleo.Posicion;
import modelo.nucleo.Matriz;
import modelo.decoradores.CajaFragil;

public class ComandoMovimiento implements Comando {
    private final GameObject jugador;
    private final Posicion posViejaJugador;

    private final GameObject caja;
    private final Posicion posViejaCaja;
    private final boolean seRompioCaja;

    public ComandoMovimiento(GameObject jugador, Posicion posViejaJugador, GameObject caja, Posicion posViejaCaja, boolean seRompioCaja) {
        this.jugador = jugador;
        this.posViejaJugador = posViejaJugador;
        this.caja = caja;
        this.posViejaCaja = posViejaCaja;
        this.seRompioCaja = seRompioCaja;
    }

    @Override
    public void deshacer(Matriz matriz) {
        matriz.moverObjeto(jugador, posViejaJugador);

        if (caja != null) {
            if (caja instanceof CajaFragil) {
                ((CajaFragil) caja).incrementarResistencia();
            }

            if (seRompioCaja) {
                caja.setPosicion(posViejaCaja);
                matriz.colocarObjeto(caja);
            } else {
                matriz.moverObjeto(caja, posViejaCaja);
            }
        }
    }

    @Override
    public boolean esEmpuje() {
        return caja != null;
    }
}