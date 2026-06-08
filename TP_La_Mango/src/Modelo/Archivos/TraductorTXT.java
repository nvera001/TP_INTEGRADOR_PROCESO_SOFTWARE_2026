package Modelo.Archivos;

import Modelo.Fabricas.CreadorCajaSimple;
import Modelo.Fabricas.CreadorGameObject;
import Modelo.Fabricas.CreadorParedSimple;

import java.util.HashMap;
import java.util.Map;

public class TraductorTXT {

    // Nuestro diccionario que vincula un carácter con una función creadora
    private Map<Character, CreadorGameObject> diccionarioCadenas;

    public TraductorTXT() {
        diccionarioCadenas = new HashMap<>();
        CreadorCajaSimple ccs = new CreadorCajaSimple();
        CreadorParedSimple cps = new CreadorParedSimple();

        diccionarioCadenas.put('#', (x, y) -> cps.crearGameObject(x, y));
        diccionarioCadenas.put('C', (x, y) -> ccs.crearGameObject(x, y));
        diccionarioCadenas.put('E', (x, y) -> {});
    }

    public void instanciarDesdeCaracter(char c, int x, int y) {
        CreadorGameObject creador = diccionarioCadenas.get(c);

        if (creador != null) {
            creador.crearGameObject(x, y);
        } else if (c != '\n' && c != '\r') {
            System.out.println("Error: Carácter desconocido en el mapa -> " + c);
        }
    }
}
