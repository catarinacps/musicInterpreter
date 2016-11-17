/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicinterpreter;

import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;

/**
 *
 * @author Henrique Silva, Nicolas Eymael e Luis Miguel
 */
public class Musica {
    private String notas;

	static final int OITAVA_PADRAO = 5;
    static final int VOLUME_PADRAO = 64;
    static final int INSTRUMENTO_PADRAO = 1;
	static final int NUMERO_DE_INSTRUMENTOS = 127;
	static final int VOLUME_MAXIMO = 127;
	static final int ULTIMA_OITAVA = 9;

	/*
	------------------------------------------------------------------------------------------------------------------------
	Metodos Publicos:
	->Musica() "construtor":
	Obviamente.

	->tocaMusica():
	Usando o parametro "notas", que e uma string, cria um ManagedPlayer para suportar pausas e toca a musica.
	A parte de pausar nao foi implementada ainda.

	->pegaNotas():
	Retorna o string "notas"

	->

	------------------------------------------------------------------------------------------------------------------------
	*/

    public Musica(String entradaTexto){
        DecodificadorTexto decodificador = new DecodificadorTexto(entradaTexto);
        this.notas = decodificador.pegaSaida();
    }

	public void tocaMusica() throws InvalidMidiDataException, MidiUnavailableException{
		Player tocador = new Player();
		ManagedPlayer tocadorControlado = new ManagedPlayer();

		tocadorControlado.start(tocador.getSequence(this.notas));
		//implementar dps pause, stop e resume (junto com a interface tlg)
	}

	public String pegaNotas(){
		return this.notas;
	}

	public void salvaMIDI() throws IOException{
		Pattern musica = new Pattern(this.notas);

		MidiFileManager.savePatternToMidi(musica, new File("nomearquivo.mid"));
		//implementar na interface pro cara escolher o nome tlg
	}

	
}
