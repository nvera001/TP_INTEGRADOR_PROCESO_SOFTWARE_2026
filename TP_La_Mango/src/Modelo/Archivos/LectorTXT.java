package Modelo.Archivos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LectorTXT {
    private String cadena = "";

    /**
     * Carga un archivo de texto directamente desde los paquetes del código (Classpath).
     * @param rutaResource Ejemplo: "/Modelo/Mapas/mapa.txt"
     */
    public void cargarArchivo(String rutaResource) {
        this.cadena = ""; // Reiniciamos la cadena por seguridad

        // Buscamos el archivo dentro del Classpath del proyecto
        try (InputStream is = getClass().getResourceAsStream(rutaResource)) {
            
            if (is == null) {
                System.out.println("Error: No se encontró el mapa en la ruta interna: " + rutaResource);
                System.out.println("--> Verificá que el archivo esté exactamente en 'src" + rutaResource + "'");
                return;
            }

            // Convertimos el stream en un lector de texto lineal
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String linea = br.readLine();

            while (linea != null) {
                cadena += linea.trim() + "\n";
                linea = br.readLine();
            }

        } catch (IOException e) {
            System.out.println("Error crítico al intentar leer el contenido del mapa.");
        }
    }

    public String getCadena() {
        return cadena;
    }
}