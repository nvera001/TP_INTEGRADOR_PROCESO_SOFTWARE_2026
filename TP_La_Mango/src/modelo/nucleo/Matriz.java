package modelo.nucleo;

import modelo.entidades.*;
import modelo.memento.Memento;

public class Matriz {

    private final GameObject[][] capaSuelo;
    private final GameObject[][] capaElementos;
    private final boolean[][] grillaMonedas;

    private final int filas;
    private final int columnas;
    private boolean murosAbiertos = false;

    private Matriz estado;

    public Matriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.capaSuelo = new GameObject[filas][columnas];
        this.capaElementos = new GameObject[filas][columnas];
        this.grillaMonedas = new boolean[filas][columnas];
    }

    public int getFilas() { return this.filas; }
    public int getColumnas() { return this.columnas; }
    public GameObject[][] getCapaElementos() { return this.capaElementos; }

    public void marcarComoMonedaFoto(Posicion pos) {
        if (!esPosicionInvalida(pos)) {
            this.grillaMonedas[pos.getY()][pos.getX()] = true;
        }
    }

    public boolean esMonedaFoto(Posicion pos) {
        if (esPosicionInvalida(pos)) return false;
        return this.grillaMonedas[pos.getY()][pos.getX()];
    }

    public void colocarObjeto(GameObject obj) {
        Posicion pos = obj.getPosicion();
        if (obj.getCapa() == GameObject.CapaJuego.SUELO) {
            capaSuelo[pos.getY()][pos.getX()] = obj;
        } else {
            capaElementos[pos.getY()][pos.getX()] = obj;
        }
    }

    public GameObject obtenerObjetoEn(Posicion pos) {
        if (esPosicionInvalida(pos)) return null;

        GameObject elemento = capaElementos[pos.getY()][pos.getX()];
        if (elemento != null) {
            return elemento;
        }

        if (!murosAbiertos) {
            GameObject suelo = capaSuelo[pos.getY()][pos.getX()];
            if (suelo != null && suelo.esMuroCerrado()) {
                return suelo;
            }
        }
        return null;
    }

    public GameObject obtenerSueloEn(Posicion pos) {
        if (esPosicionInvalida(pos)) return null;
        return capaSuelo[pos.getY()][pos.getX()];
    }

    public void moverObjeto(GameObject obj, Posicion nuevaPos) {
        Posicion viejaPos = obj.getPosicion();
        capaElementos[viejaPos.getY()][viejaPos.getX()] = null;
        capaElementos[nuevaPos.getY()][nuevaPos.getX()] = obj;
        obj.setPosicion(nuevaPos);

        actualizarEstadoMuros();
    }

    public boolean esMeta(Posicion pos) {
        GameObject suelo = obtenerSueloEn(pos);
        return suelo != null && suelo.esMeta();
    }

    public boolean esCerrojo(Posicion pos) {
        GameObject suelo = obtenerSueloEn(pos);
        return suelo != null && suelo.esCerrojo();
    }

    public boolean esMuro(Posicion pos) {
        if (esPosicionInvalida(pos)) return false;
        GameObject suelo = capaSuelo[pos.getY()][pos.getX()];
        return suelo != null && suelo.esMuroCerrado();
    }

    public boolean esResbaladizo(Posicion pos) {
        GameObject suelo = obtenerSueloEn(pos);
        return suelo != null && suelo.esResbaladizo();
    }

    public boolean MurosAbiertos() {
        return this.murosAbiertos;
    }

    private void actualizarEstadoMuros() {
        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < columnas; x++) {
                GameObject suelo = capaSuelo[y][x];
                if (suelo != null && suelo.esCerrojo()) {
                    GameObject elementoEncima = capaElementos[y][x];
                    if (elementoEncima instanceof Caja && ((Caja) elementoEncima).contieneLlave()) {
                        this.murosAbiertos = true;
                        return;
                    }
                }
            }
        }
        this.murosAbiertos = false;
    }

    public boolean estanTodasLasMetasCubiertas() {
        boolean alMenosUnaMeta = false;

        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < columnas; x++) {
                GameObject suelo = capaSuelo[y][x];
                if (suelo != null && suelo.esMeta()) {
                    alMenosUnaMeta = true;
                    GameObject elementoEncima = capaElementos[y][x];
                    if (!(elementoEncima instanceof Caja)) {
                        return false;
                    }
                }
            }
        }
        return alMenosUnaMeta;
    }

    public void eliminarObjeto(Posicion pos) {
        if (!esPosicionInvalida(pos)) {
            capaElementos[pos.getY()][pos.getX()] = null;
        }
    }

    public GameObject obtenerObjetoGrillaPura(Posicion pos) {
        if (esPosicionInvalida(pos)) return null;
        return capaElementos[pos.getY()][pos.getX()];
    }

    private boolean esPosicionInvalida(Posicion pos) {
        return pos.getX() < 0 || pos.getX() >= columnas || pos.getY() < 0 || pos.getY() >= filas;
    }

    public Posicion obtenerPosicionJugador() {
        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < columnas; x++) {
                GameObject obj = capaElementos[y][x];
                if (obj != null && obj.getSimbolo() == '@') {
                    return new Posicion(x, y);
                }
            }
        }
        return null;
    }

    public Memento guardar() {
        return new Memento(this.capaElementos);
    }

    public void restaurarCajas(Memento memento) {
        GameObject jugadorActual = null;
        Posicion posJugadorActual = obtenerPosicionJugador();
        if (posJugadorActual != null) {
            jugadorActual = capaElementos[posJugadorActual.getY()][posJugadorActual.getX()];
        }

        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < columnas; x++) {
                if (capaElementos[y][x] instanceof Caja) {
                    capaElementos[y][x] = null;
                }
            }
        }

        GameObject[][] cajasGuardadas = memento.getEstadoCajas();
        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < columnas; x++) {
                GameObject caja = cajasGuardadas[y][x];
                if (caja != null) {
                    if (posJugadorActual != null && posJugadorActual.getX() == x && posJugadorActual.getY() == y) {
                        caja.setPosicion(new Posicion(x, y));
                    } else {
                        capaElementos[y][x] = caja;
                        caja.setPosicion(new Posicion(x, y));
                    }
                }
            }
        }

        if (jugadorActual != null && posJugadorActual != null) {
            capaElementos[posJugadorActual.getY()][posJugadorActual.getX()] = jugadorActual;
        }

        actualizarEstadoMuros();
    }
}