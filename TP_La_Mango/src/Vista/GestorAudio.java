package Vista;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class GestorAudio {
    public void reproducirSonido(String nombreArchivo) {
        new Thread(() -> {
            try {
                URL url = Thread.currentThread().getContextClassLoader().getResource("Vista/recursos/audios/" + nombreArchivo);                if (url == null) {
                    System.err.println("No se encontró el sonido: " + nombreArchivo);
                    return;
                }

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();

            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Error al reproducir audio: " + e.getMessage());
            }
        }).start();
    }
}