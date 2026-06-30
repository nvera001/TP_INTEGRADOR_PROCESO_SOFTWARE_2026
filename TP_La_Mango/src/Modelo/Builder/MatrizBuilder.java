package Modelo.Builder;

import Modelo.Nucleo.Matriz;
import Modelo.Nucleo.Posicion;

public interface MatrizBuilder {
    void reset(int filas, int columnas);
    void construirPared(Posicion pos);
    void construirJugador(Posicion pos);
    void construirMeta(Posicion pos);
    void construirTerrenoResbaladizo(Posicion pos);
    void construirCerrojo(Posicion pos);
    void construirMuroCerrado(Posicion pos);
    void construirCaja(char tipoCaja, Posicion pos);

    Matriz getResultado();
}