/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicinterpreter;

/**
 *
 * @author Luiz Eduardo
 */
public class Musica {
    private String notas;

	static final int OITAVA_PADRAO = 5;
    static final int VOLUME_PADRAO = 64;
    static final int INSTRUMENTO_PADRAO = 1;
	static final int NUMERO_DE_INSTRUMENTOS = 127;
	static final int VOLUME_MAXIMO = 127;
	static final int ULTIMA_OITAVA = 9;

    public Musica(String arquivoIntegral){
        this.notas = decodificaArquivo(arquivoIntegral);
    }

	/*public void tocaMusica(){
		Player tocador = new Player();
		tocador.play(this.notas);
	}*/

	private String decodificaArquivo(String arquivoIntegral){
		char caracterAnterior = 0;
		String saidaDecodificada = "";
		Nota notaTocada = null;
		int oitavaAtual = OITAVA_PADRAO;
		int volumeAtual = VOLUME_PADRAO;
		int instrumentoAtual = INSTRUMENTO_PADRAO;
		int i = 0;

		char[] stringIntegral = arquivoIntegral.toCharArray();

		for(i = 0; i < arquivoIntegral.length(); i++){
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

	public String pegaNotas(){
		return this.notas;
	}
}
