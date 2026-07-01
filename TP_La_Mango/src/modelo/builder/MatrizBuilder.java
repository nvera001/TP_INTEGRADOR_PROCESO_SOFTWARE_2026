package modelo.builder;

import modelo.nucleo.Matriz;
import modelo.nucleo.Posicion;

public interface MatrizBuilder {
    void reset(int filas, int columnas);
    void construirPared(Posicion pos);
    void construirJugador(Posicion pos);
    void construirMeta(Posicion pos);
    void construirTerrenoResbaladizo(Posicion pos);
    void construirCerrojo(Posicion pos);
    void construirMuroCerrado(Posicion pos);
    void construirMonedaFoto(Posicion pos);
    void construirCaja(char tipoCaja, Posicion pos);

    Matriz getResultado();
}