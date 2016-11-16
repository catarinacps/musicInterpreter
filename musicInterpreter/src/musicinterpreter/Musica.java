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
        this.notas = decodificaArquivo(entradaTexto);
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

	/*
	------------------------------------------------------------------------------------------------------------------------
	Metodos Privados:
	->decodificaArquivo(String entradaTexto):
	Recebe o arquivo de entrada em forma de string e o transforma numa sequencia de notas
	tambem em formato de string.
	Acompanha os seus metodos auxiliares (tambem privados)

	------------------------------------------------------------------------------------------------------------------------
	*/
	private String decodificaArquivo(String entradaTexto){
		String saidaDecodificada = "";
		Nota notaTocada = null;
		int oitavaAtual = OITAVA_PADRAO;
		int volumeAtual = VOLUME_PADRAO;
		int instrumentoAtual = INSTRUMENTO_PADRAO;
		int i = 0;

		char[] stringIntegral = entradaTexto.toCharArray();

		for(i = 0; i < entradaTexto.length(); i++){
			if(testeInstrumento(stringIntegral[i])){
				instrumentoAtual = alteraInstrumento(stringIntegral[i], instrumentoAtual);

				saidaDecodificada += "I" + Integer.toString(instrumentoAtual) + " ";
			}
			else if(testeVolume(stringIntegral[i])){
				volumeAtual = alteraVolume(stringIntegral[i], volumeAtual);
			}
			else if(testeOitava(stringIntegral[i])){
				oitavaAtual = alteraOitava(stringIntegral[i], oitavaAtual);
			}
			else if (testeNota(stringIntegral[i])){
				notaTocada = new Nota(stringIntegral[i], oitavaAtual, volumeAtual);
				saidaDecodificada += notaTocada.pegaNota() + notaTocada.pegaOitava() + "a" + notaTocada.pegaVolume() + " ";

			}
			else{ // nenhum dos caracteres anteriores, entao repetir a nota anterior ou fazer uma pausa
				try{
					if (testeNota(stringIntegral[i-1])){
						saidaDecodificada += notaTocada.pegaNota() + notaTocada.pegaOitava() + "a" + notaTocada.pegaVolume() + " ";
					}
					else{
						saidaDecodificada += "R ";
					}
				}
				catch(IndexOutOfBoundsException erroLimiteArray){
					saidaDecodificada += "R ";
				}
			}
		}

		return saidaDecodificada;
	}

	//------------------------------------------------------------------------------------------------------------------------
	//Metodos auxiliares de decodificaArquivo
	//------------------------------------------------------------------------------------------------------------------------

	private int multiplicaVolume(int volume, float multiplicador){
                float multiplicacaoFloat = volume * multiplicador;
                int multiplicacaoInt = Math.round(multiplicacaoFloat);
                if(multiplicacaoInt > VOLUME_MAXIMO)
                    multiplicacaoInt = VOLUME_MAXIMO;
		return multiplicacaoInt;
	}

	private boolean testeInstrumento(char caracterLido){
		if(caracterLido == '!' || (caracterLido >= '0' && caracterLido <= '9') || caracterLido == '\n' || caracterLido == ';' || caracterLido == ','){
			return true;
		}
		return false;
	}

	private int alteraInstrumento(char caracterLido, int instrumentoAtual){
		switch(caracterLido){
			case '!':
				return 7; //Harpsichord
			case '\n':
				return 15; //Tubular Bells
			case ';':
				return 76; //Pan Flute
			case ',':
				return 20; //Church Organ
			default: // se digito numerico, somar esse digito ao codigo do instrumento atual
				if((instrumentoAtual += Character.getNumericValue(caracterLido)) > NUMERO_DE_INSTRUMENTOS){
					return instrumentoAtual -= NUMERO_DE_INSTRUMENTOS;
				}
				return instrumentoAtual;
		}
	}

	private boolean testeVolume(char caracterLido){
		if(caracterLido == ' ' || caracterLido == 'O' || caracterLido == 'o' || caracterLido == 'I' || caracterLido == 'i' || caracterLido == 'U' || caracterLido == 'u'){
			return true;
		}
		return false;
	}

	private int alteraVolume(char caracterLido, int volumeAtual){
		if(caracterLido == ' '){
			return multiplicaVolume(volumeAtual, 2f);
		}
		else{
			return multiplicaVolume(volumeAtual, 1.1f);
		}
	}

	private boolean testeOitava(char caracterLido){
		if(caracterLido == '?'){
			return true;
		}
		return false;
	}

	private int alteraOitava(char caracterLido, int oitavaAtual){
		if(oitavaAtual == ULTIMA_OITAVA){
			return OITAVA_PADRAO;
		}
		else{
			return oitavaAtual += 1;
		}
	}

    private boolean testeNota(char caracterLido){
		if(caracterLido >= 'A' && caracterLido <= 'G'){
			return true;
		}
		return false;
	}

	//------------------------------------------------------------------------------------------------------------------------
	//Fim dos metodos auxiliares de decodificaArquivo
	//------------------------------------------------------------------------------------------------------------------------
}
