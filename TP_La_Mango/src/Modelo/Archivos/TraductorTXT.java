package Modelo.Archivos;

import Modelo.Entidades.GameObject;
import Modelo.Entidades.Jugador;
import Modelo.Fabricas.CreadorCajaSimple;
import Modelo.Fabricas.CreadorMeta;
import Modelo.Fabricas.CreadorGameObject;
import Modelo.Fabricas.CreadorJugador;
import Modelo.Fabricas.CreadorParedSimple;
import Modelo.Nucleo.Posicion;

import java.util.HashMap;
import java.util.Map;

public class TraductorTXT {

    private Map<Character, CreadorGameObject> diccionarioCadenas;

    public TraductorTXT() {
        diccionarioCadenas = new HashMap<>();
        CreadorCajaSimple ccs = new CreadorCajaSimple();
        CreadorParedSimple cps = new CreadorParedSimple();
        CreadorJugador cjugador = new CreadorJugador();
        CreadorMeta cmeta = new CreadorMeta();

        diccionarioCadenas.put('#', cps);
        diccionarioCadenas.put('C', ccs);
        diccionarioCadenas.put('P', cjugador);
        diccionarioCadenas.put('X', cmeta);

    }

    // Método refactorizado para devolver el GameObject o null si es espacio vacío
    public GameObject instanciarDesdeCaracter(char c, Posicion pos) {
        CreadorGameObject creador = diccionarioCadenas.get(c);

        if (creador != null) {
            return creador.crearGameObject(pos);
        } else if (c != '.' && c != ' ' && c != '\n' && c != '\r') {
            // Ignoramos puntos, espacios y saltos de línea silenciosamente
            System.out.println("Error: Carácter desconocido en el mapa -> " + c);
        }

        return null; // Retorna null si es un espacio vacío o un carácter no reconocido
    }
}
