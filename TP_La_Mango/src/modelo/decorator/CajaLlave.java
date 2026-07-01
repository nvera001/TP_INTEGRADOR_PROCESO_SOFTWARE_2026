package modelo.decorator;

import modelo.entidades.Caja;

public class CajaLlave extends CajaDecorador {
    public CajaLlave(Caja caja) {
        super(caja, 'K');
    }

    public boolean contieneLlave() {
        return true;
    }
}