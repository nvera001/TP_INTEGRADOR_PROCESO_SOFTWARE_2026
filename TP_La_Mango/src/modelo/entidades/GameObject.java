package modelo.entidades;

import modelo.nucleo.Posicion;

public abstract class GameObject {
    protected Posicion posicion;
    protected char simbolo;

    public GameObject(Posicion posicion, char simbolo) {
        this.posicion = posicion;
        this.simbolo = simbolo;
    }

    public Posicion getPosicion() { return posicion; }
    public void setPosicion(Posicion posicion) { this.posicion = posicion; }
    public char getSimbolo() { return simbolo; }
}