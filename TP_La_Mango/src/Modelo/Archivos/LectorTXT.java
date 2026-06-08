package Modelo.Archivos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LectorTXT {
    private String cadena = "";

    public void cargarArchivo(String archivo) {
        try  {
            BufferedReader br = new BufferedReader(new FileReader(archivo));

            String linea = br.readLine();

            while (linea != null) {
                cadena += linea.trim();
                linea = br.readLine();
            }
            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: No se encontró el archivo en la ruta " + archivo);
        } catch (IOException e) {
            System.out.println("Error al intentar leer el contenido del archivo.");
        }

    }

    public String getCadena() {
        return cadena;
    }
}
