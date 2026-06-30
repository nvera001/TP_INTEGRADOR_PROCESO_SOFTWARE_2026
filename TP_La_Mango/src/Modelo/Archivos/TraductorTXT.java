package Modelo.Archivos;

import Modelo.Entidades.CajaSimple;
import Modelo.Entidades.*;
import Modelo.Decoradores.CajaFragil;
import Modelo.Decoradores.CajaLlave;
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
        diccionarioCadenas.put('S', posicion -> new TerrenoResbaladizo(posicion));

        diccionarioCadenas.put('L', posicion -> new Cerrojo(posicion));

        diccionarioCadenas.put('M', posicion -> new MuroCerrado(posicion));


        diccionarioCadenas.put('F', new CreadorGameObject() {
            @Override
            public GameObject crearGameObject(Posicion posicion) {
                return new CajaFragil(new CajaSimple(posicion), 18);
            }
        });

        diccionarioCadenas.put('K', new CreadorGameObject() {
            @Override
            public GameObject crearGameObject(Posicion posicion) {
                return new CajaLlave(new CajaSimple(posicion));
            }
        });

        diccionarioCadenas.put('Z', new CreadorGameObject() {
            @Override
            public GameObject crearGameObject(Posicion posicion) {
                CajaSimple base = new CajaSimple(posicion);
                CajaLlave conLlave = new CajaLlave(base);
                CajaFragil conFragilidadYLlave = new CajaFragil(conLlave, 18, 'Z');

                return conFragilidadYLlave;
            }
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
