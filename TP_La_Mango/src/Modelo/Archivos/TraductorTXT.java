package Modelo.Archivos;

import Modelo.Decoradores.CajaFragil;
import Modelo.Decoradores.CajaLlave;
import Modelo.Entidades.CajaSimple;
import Modelo.Entidades.*;
import Modelo.Fabricas.*;
import Modelo.Nucleo.Posicion;

import java.util.HashMap;
import java.util.Map;

public class TraductorTXT {

    private Map<Character, CreadorGameObject> diccionarioCadenas;

    public TraductorTXT() {
        diccionarioCadenas = new HashMap<>();

        diccionarioCadenas.put('#', new CreadorParedSimple());
        diccionarioCadenas.put('P', new CreadorJugador());
        diccionarioCadenas.put('X', new CreadorMeta());
        diccionarioCadenas.put('S', new CreadorTerrenoResbaladizo());
        diccionarioCadenas.put('L', new CreadorCerrojo());
        diccionarioCadenas.put('M', new CreadorMuroCerrado());

        diccionarioCadenas.put('C', new CreadorCajaSimple());
        diccionarioCadenas.put('F', new CreadorCajaFragil());
        diccionarioCadenas.put('K', new CreadorCajaLlave());

        diccionarioCadenas.put('Z', posicion -> {
            Caja base = new CajaSimple(posicion);
            Caja conLlave = new CajaLlave(base);
            Caja conFragilidadYLlave = new CajaFragil(conLlave, 18, 'Z');

            return (GameObject) conFragilidadYLlave;
        });
    }

    public GameObject instanciarDesdeCaracter(char c, Posicion pos) {
        CreadorGameObject creador = diccionarioCadenas.get(c);

        if (creador != null) {
            return creador.crearGameObject(pos);
        } else if (c != '.' && c != ' ' && c != '\n' && c != '\r') {
            System.out.println("Error: Carácter desconocido en el mapa -> " + c);
        }

        return null;
    }
}
