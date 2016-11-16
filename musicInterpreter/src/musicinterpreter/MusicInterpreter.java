/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicinterpreter;

import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author Luiz Eduardo
 */
public class MusicInterpreter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InvalidMidiDataException, MidiUnavailableException, IOException {
        String entradaLida = LeitorArquivo.leArquivo("arquivoKatiau.txt");

        System.out.println(entradaLida);

        Musica novaMusica = new Musica(entradaLida);

        System.out.println(novaMusica.pegaNotas());

        novaMusica.tocaMusica();

        novaMusica.salvaMIDI();
    }

}
