package modelo.entidades;

import modelo.nucleo.Posicion;

public abstract class GameObject {
    public enum CapaJuego { SUELO, ELEMENTO }

    protected Posicion posicion;
    protected char simbolo;

    public GameObject(Posicion posicion, char simbolo) {
        this.posicion = posicion;
        this.simbolo = simbolo;
    }

    public abstract CapaJuego getCapa();

    public boolean esMeta() { return false; }
    public boolean esCerrojo() { return false; }
    public boolean esMuroCerrado() { return false; }
    public boolean esResbaladizo() { return false; }

    public Posicion getPosicion() { return posicion; }
    public void setPosicion(Posicion posicion) { this.posicion = posicion; }
    public char getSimbolo() { return simbolo; }
}