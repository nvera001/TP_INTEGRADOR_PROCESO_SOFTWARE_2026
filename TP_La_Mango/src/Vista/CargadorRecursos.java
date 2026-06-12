package Vista;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class CargadorRecursos {
    private final Map<String, Image> imagenes;

    public CargadorRecursos() {
        imagenes = new HashMap<>();
        cargarImagenes();
    }

    private void cargarImagenes() {
        try {
            // Usamos rutas absolutas dentro del Classpath. 
            // Ojo con las mayúsculas: /Vista/recursos/
            imagenes.put("piso", leerImagen("/Vista/recursos/piso.png"));
            imagenes.put("jugador", leerImagen("/Vista/recursos/jugador.png"));
            imagenes.put("pared", leerImagen("/Vista/recursos/pared.png"));
            imagenes.put("caja", leerImagen("/Vista/recursos/caja.png"));
            imagenes.put("caja_meta", leerImagen("/Vista/recursos/caja_meta.png"));
            imagenes.put("meta", leerImagen("/Vista/recursos/meta.png"));
            imagenes.put("fondo", leerImagen("/Vista/recursos/fondo.jpg"));
            
            System.out.println("¡Todas las imágenes se cargaron con éxito desde el Classpath!");
        } catch (IOException e) {
            System.err.println("Error crítico: No se pudo cargar algún asset del Sokoban.");
            e.printStackTrace();
        }
    }

    // Método auxiliar para validar que el archivo realmente exista antes de leerlo
    private Image leerImagen(String ruta) throws IOException {
        URL url = getClass().getResource(ruta);
        if (url == null) {
            throw new IOException("No se encontró el archivo en la ruta del código: " + ruta 
                + "\n--> Asegurate de que las imágenes estén dentro de 'src/Vista/recursos/' y tengan los nombres en minúscula.");
        }
        return ImageIO.read(url);
    }

    public Image getImagen(String clave) {
        return imagenes.get(clave);
    }
}