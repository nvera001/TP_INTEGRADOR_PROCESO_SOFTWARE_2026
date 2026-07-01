package vista;

import javax.sound.sampled.*;
import java.net.URL;

public class GestorAudio {
    private static boolean sonidoActivo = true;

    private long tiempoUltimoSonido = 0;

    private final int TIEMPO_ESPERA = 300;

    public static void toggleSonido() {
        sonidoActivo = !sonidoActivo;
    }

    public static boolean isSonidoActivo() {
        return sonidoActivo;
    }

    public void reproducirSonido(String nombreArchivo) {
        if (!sonidoActivo) return;

        long tiempoActual = System.currentTimeMillis();

        if (tiempoActual - tiempoUltimoSonido >= TIEMPO_ESPERA) {

            tiempoUltimoSonido = tiempoActual;

            try {
                URL urlStream = getClass().getResource("/vista/recursos/audios/" + nombreArchivo);

                if (urlStream != null) {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(urlStream);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                } else {
                    System.out.println("No se encontró el archivo de audio en: /vista/recursos/audios/" + nombreArchivo);
                }
            } catch (Exception e) {
                System.out.println("Error al reproducir sonido: " + e.getMessage());
            }
        }
    }
}