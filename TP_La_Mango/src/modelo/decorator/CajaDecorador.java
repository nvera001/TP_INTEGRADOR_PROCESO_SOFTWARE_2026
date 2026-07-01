package modelo.decorator;

import modelo.entidades.GameObject;
import modelo.entidades.Caja;
import modelo.nucleo.Posicion;
import modelo.nucleo.Direccion;
import modelo.nucleo.Matriz;

public abstract class CajaDecorador extends GameObject implements Caja {
    protected Caja cajaEnvoltorio;

    public CajaDecorador(Caja caja, char simbolo) {
        super(caja.getPosicion(), simbolo);
        this.cajaEnvoltorio = caja;
    }

    public CapaJuego getCapa() {return CapaJuego.ELEMENTO;}

    public void setPosicion(Posicion posicion) {
        super.setPosicion(posicion);
        cajaEnvoltorio.setPosicion(posicion);
    }

    public boolean serEmpujada(Direccion dir, Matriz matriz, GameObject entidadA_Mover) {return cajaEnvoltorio.serEmpujada(dir, matriz, entidadA_Mover);}

    public boolean contieneLlave() {
        return cajaEnvoltorio.contieneLlave();
    }
}