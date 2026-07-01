package vista;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class CargadorRecursos {
    private static CargadorRecursos instancia;
    private final Map<String, Image> imagenes;

    private CargadorRecursos() {
        imagenes = new HashMap<>();
        cargarImagenes();
    }

    public static CargadorRecursos getInstancia() {
        if (instancia == null) {
            instancia = new CargadorRecursos();
        }
        return instancia;
    }

    private void cargarImagenes() {
        try {
            imagenes.put("piso", leerImagen("/vista/recursos/imagenes/piso.png"));
            imagenes.put("jugador", leerImagen("/vista/recursos/imagenes/jugador.png"));
            imagenes.put("pared", leerImagen("/vista/recursos/imagenes/pared.png"));
            imagenes.put("caja", leerImagen("/vista/recursos/imagenes/caja.png"));
            imagenes.put("caja_meta", leerImagen("/vista/recursos/imagenes/caja_meta.png"));
            imagenes.put("meta", leerImagen("/vista/recursos/imagenes/meta.png"));
            imagenes.put("fondo", leerImagen("/vista/recursos/imagenes/fondo.jpg"));

            imagenes.put("caja_fragil", leerImagen("/vista/recursos/imagenes/caja_fragil.png"));
            imagenes.put("caja_llave", leerImagen("/vista/recursos/imagenes/caja_llave.png"));
            imagenes.put("caja_fragil_llave", leerImagen("/vista/recursos/imagenes/caja_fragil_llave.png"));

            imagenes.put("cerrojo", leerImagen("/vista/recursos/imagenes/cerrojo.png"));
            imagenes.put("muro", leerImagen("/vista/recursos/imagenes/muro.png"));
            imagenes.put("resbaladizo", leerImagen("/vista/recursos/imagenes/resbaladizo.png"));
            imagenes.put("moneda", leerImagen("/vista/recursos/imagenes/moneda.png"));

        } catch (IOException e) {
            System.err.println("Error crítico: No se pudo cargar algún asset del Sokoban.");
            e.printStackTrace();
        }
    }

    private Image leerImagen(String ruta) throws IOException {
        URL url = getClass().getResource(ruta);
        if (url == null) {
            throw new IOException("No se encontró el archivo en la ruta del código: " + ruta
                    + "\n--> Asegurate de que las imágenes estén dentro de 'src/vista/recursos/' y tengan los nombres en minúscula.");
        }
        return ImageIO.read(url);
    }

    public Image getImagen(String clave) {
        return imagenes.get(clave);
    }
}