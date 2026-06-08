package Modelo.Entidades;

public class CajaSimple implements Caja, GameObject {

    @Override
    public boolean esSolido() {
        return true;
    }
}
