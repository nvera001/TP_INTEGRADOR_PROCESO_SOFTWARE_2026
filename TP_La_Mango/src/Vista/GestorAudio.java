package Vista;

import javax.sound.sampled.*;
import java.net.URL;

public class GestorAudio {
    // Variable global que recuerda si el usuario sacó el volumen
    private static boolean sonidoActivo = true;

    public static void toggleSonido() {
        sonidoActivo = !sonidoActivo; // Invierte el estado (ON -> OFF / OFF -> ON)
    }

    public static boolean isSonidoActivo() {
        return sonidoActivo;
    }

    public void reproducirSonido(String nombreArchivo) {
        if (!sonidoActivo) return; // Si está desactivado, el método se corta acá y no suena nada

        try {
            URL urlStream = getClass().getResource("/Vista/recursos/audios/" + nombreArchivo);

            if (urlStream != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(urlStream);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } else {
                System.out.println("No se encontró el archivo de audio en: /Vista/recursos/audios/" + nombreArchivo);
            }
        } catch (Exception e) {
            System.out.println("Error al reproducir sonido: " + e.getMessage());
        }
    }
}
