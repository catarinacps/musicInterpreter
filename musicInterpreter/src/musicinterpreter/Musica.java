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
	private Player tocador;
	private ManagedPlayer tocadorControlado;

	static final int OITAVA_PADRAO = 5;
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

    public Musica(String entradaTexto, int volumeInicial, int instrumentoInicial, int ritmoInicial){
        DecodificadorTexto decodificador = new DecodificadorTexto(entradaTexto, volumeInicial, instrumentoInicial, ritmoInicial);
        this.notas = decodificador.pegaSaida();

		this.tocador = new Player();
		this.tocadorControlado = new ManagedPlayer();
    }

	public void tocaMusica() throws InvalidMidiDataException, MidiUnavailableException{
            if(!this.tocadorControlado.isPlaying())
		this.tocadorControlado.start(this.tocador.getSequence(this.notas));
	}

	public void pausaMusica(){
		if(this.tocadorControlado.isPlaying()){
			this.tocadorControlado.pause();
            }
        }

        
        public boolean musicaEstaPausada(){
            if(this.tocadorControlado.isPaused()){
                return true;
            }
            else{
                return false;
            }
        }
        
        

	public void resumeMusica(){
		if(this.tocadorControlado.isPaused()){
			this.tocadorControlado.resume();
		}
	}

	public void paraMusica(){
		if(this.tocadorControlado.isPaused() || this.tocadorControlado.isPlaying()){
                        this.tocadorControlado.reset();
                        
		}
	}
        
        public void fechaMusica(){
            this.tocadorControlado.finish();
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
