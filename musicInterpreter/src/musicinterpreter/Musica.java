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


    /**
     * Construtor da classe Musica
     * A biblioteca utilizada (JFugue) é capaz de reproduzir musica utilizando uma string como entrada,
     * pensando nisso utilizamos 5 parametros para formar a string que contem as notas musicais a serem reproduzidas,
     * são eles: Entrada do Usuario, Volume, Instrumento, Ritmo e Oitava.
     * Assim, a string decodificada é armazenada em "notas", e são inicializados um "tocador" e um "tocadorControlado"
     * veja que o tocadorControlado utiliza o tocador simples como parametro, mas difere do mesmo por ser capaz de controlar
     * a musica por meio de pausas, paradas e afins
     *  
     * @param entradaTexto  texto a ser decodificado, pode ter sido originado de um arquivo .txt ou diretamente escrito pelo usuario
     * @param volumeInicial volume inicializado pelo usuario nas configuraçoes (default 64, min 0, max 127)
     * @param instrumentoInicial    instrumento inicializado pelo usuario nas configuraçoes (default 0 (piano acustico), min 0, max 127)
     * @param indiceRitmoInicial    indice do ritmo inicializado pelo usuario nas configuraçoes, o indice será decodificado para o ritmo correspondente (em BPM)
     * @param oitavaInicial oitava das notas inicializada pelo usuario nas configuraçoes (default 4, min 0, max 9)
     */
    public Musica(String entradaTexto, int volumeInicial, int instrumentoInicial, int indiceRitmoInicial, int oitavaInicial){
        DecodificadorTexto decodificador = new DecodificadorTexto(entradaTexto, volumeInicial, instrumentoInicial, indiceRitmoInicial, oitavaInicial);
        this.notas = decodificador.pegaSaida();

		this.tocador = new Player();
		this.tocadorControlado = new ManagedPlayer();
    }

    /**
     * Inicializa o stream de audio utilizando o tocadorControlado
     * caso ele nao tenha sido iniciado ainda
     * 
     * @throws InvalidMidiDataException 
     * @throws MidiUnavailableException
     */
    public void tocaMusica() throws InvalidMidiDataException, MidiUnavailableException{
            if(!this.tocadorControlado.isPlaying())
		this.tocadorControlado.start(this.tocador.getSequence(this.notas));
	}

    /**
     * Pausa o stream de audio do tocadorControlado
     * caso ele esteja tocando
     * 
     */
    public void pausaMusica(){
		if(this.tocadorControlado.isPlaying()){
			this.tocadorControlado.pause();
            }
        }

    /**
     * Testa se a musica está pausada, utilizando o tocadorControlado
     * 
     * 
     * @return valor booleano true se tocadorControlado está pausado, senão false
     */
    public boolean musicaEstaPausada(){
            if(this.tocadorControlado.isPaused()){
                return true;
            }
            else{
                return false;
            }
        }

    /**
     * Resume o stream de audio do tocadorControlado
     * caso ele esteja pausado
     */
    public void resumeMusica(){
		if(this.tocadorControlado.isPaused()){
			this.tocadorControlado.resume();
		}
	}

    /**
     * Para o stream de audio do tocadorControlado
     * e o reseta
     * 
     */
    public void paraMusica(){
		if(this.tocadorControlado.isPaused() || this.tocadorControlado.isPlaying()){
                        this.tocadorControlado.reset();

		}
	}

    /**
     * Para o stream de audio e finaliza o tocadorControlado
     */
    public void fechaMusica(){
            this.tocadorControlado.finish();
        }

    /**
     * Retorna a string com as notas musicais, como um metodo get()
     * 
     * @return string com todas as notas das musica, ja decodificadas e bem definidas
     */
    public String pegaNotas(){
		return this.notas;
	}

    /**
     * Salva um arquivo de audio .mid com o nome e diretorio especificado pelo usuario
     * 
     * @param caminhoArquivo caminho absoluto do novo arquivo a ser salvo (escolhido por meio de um File Chooser)
     * @throws IOException
     */
    public void salvaMIDI(String caminhoArquivo) throws IOException{
		Pattern musica = new Pattern(this.notas);

		MidiFileManager.savePatternToMidi(musica, new File(caminhoArquivo));
	}
}
