package modelo.archivos;

import modelo.decorator.CajaFragil;
import modelo.decorator.CajaLlave;
import modelo.entidades.CajaSimple;
import modelo.entidades.*;
import modelo.factory_method.*;
import modelo.nucleo.Posicion;

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

        diccionarioCadenas.put('F', posicion -> {
            Caja base = new CajaSimple(posicion);
            Caja conFragilidad= new CajaFragil(base, 18);
            return (GameObject) conFragilidad;
        });

        diccionarioCadenas.put('K', posicion -> {
            Caja base = new CajaSimple(posicion);
            Caja conLlave = new CajaLlave(base);
            return (GameObject) conLlave;
        });

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