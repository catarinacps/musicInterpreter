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
import java.io.*;



public class LeitorDecodificador {
    
    static final int OITAVA_PADRAO = 5;
    static final int VOLUME_PADRAO = 64;
    static final int INSTRUMENTO_PADRAO = 1;
    
    public String leArquivo(String nomeArquivo){
        try{
            FileInputStream arquivoEntrada = new FileInputStream(nomeArquivo);
            InputStreamReader arquivoFormatado = new InputStreamReader(arquivoEntrada);

            String saidaDecodificada = "";

            int byteLido = 0;
            char caracterLido = 0;
            char caracterAnterior = 0; 
            Nota notaTocada = null;
            int oitavaAtual = OITAVA_PADRAO;
            int volumeAtual = VOLUME_PADRAO;
            int instrumentoAtual = INSTRUMENTO_PADRAO;

            do{
				byteLido = arquivoFormatado.read();
                                caracterLido = (char) byteLido;

				if(testeInstrumento(caracterLido)){
					alteraInstrumento(caracterLido, instrumentoAtual);

					saidaDecodificada += 'I' + Integer.toString(instrumentoAtual) + ' ';
				}
				else if(testeVolume(caracterLido)){
					alteraVolume(caracterLido, volumeAtual);
				}
				else if(testeOitava(caracterLido)){
					alteraOitava(caracterLido, oitavaAtual);
				}
                                else if (testeNota(caracterLido)){
                                    notaTocada = new Nota(caracterLido, oitavaAtual, volumeAtual);
                                    saidaDecodificada += notaTocada.pegaNota() + notaTocada.pegaOitava() + "a" + notaTocada.pegaVolume() + " ";
				
				}
                                else{ // nenhum dos caracteres anteriores, entao repetir a nota anterior ou fazer uma pausa
                                    if (testeNota(caracterAnterior)){
                                        saidaDecodificada += notaTocada.pegaNota() + notaTocada.pegaOitava() + "a" + notaTocada.pegaVolume() + " ";
                                    }
                                    else{
                                        saidaDecodificada += "R ";
                                    }
                                }

                                  
                                caracterAnterior = caracterLido;
            }while(byteLido != -1);

            arquivoEntrada.close();

            return saidaDecodificada;
        }
        catch(IOException erro){
            System.err.printf("Erro na abertura do arquivo %s.\n", erro.getMessage());
            return null;
        }
    }

	private int multiplicaVolume(int volume, float multiplicador){
                float multiplicacaoFloat = volume * multiplicador;
                int multiplicacaoInt = Math.round(multiplicacaoFloat);
                if(multiplicacaoInt > 127)
                    multiplicacaoInt = 127;
		return multiplicacaoInt;
	}

	private boolean testeInstrumento(char caracterLido){
		if(caracterLido == '!' || (caracterLido >= '0' && caracterLido <= '9') || caracterLido == '\n' || caracterLido == ';' || caracterLido == ','){
			return true;
		}
		return false;
	}

	private void alteraInstrumento(char caracterLido, int instrumentoAtual){
		switch(caracterLido){
			case '!':
				instrumentoAtual = 7; //Harpsichord
				break;
			case '\n':
				instrumentoAtual = 15; //Tubular Bells
				break;
			case ';':
				instrumentoAtual = 76; //Pan Flute
				break;
			case ',':
				instrumentoAtual = 20; //Church Organ
				break;
			default: // se digito numerico, somar esse digito ao codigo do instrumento atual
				instrumentoAtual += Character.getNumericValue(caracterLido); 
				break;
		}
	}

	private boolean testeVolume(char caracterLido){
		if(caracterLido == ' ' || caracterLido == 'O' || caracterLido == 'o' || caracterLido == 'I' || caracterLido == 'i' || caracterLido == 'U' || caracterLido == 'u'){
			return true;
		}
		return false;
	}

	private void alteraVolume(char caracterLido, int volumeAtual){
		if(caracterLido == ' '){
			volumeAtual = multiplicaVolume(volumeAtual, 2f);
		}
		else{
			volumeAtual = multiplicaVolume(volumeAtual, 1.1f);
		}
	}

	private boolean testeOitava(char caracterLido){
		if(caracterLido == '?'){
			return true;
		}
		return false;
	}

	private void alteraOitava(char caracterLido, int oitavaAtual){
		if(oitavaAtual == 9){
			oitavaAtual = OITAVA_PADRAO;
		}
		else{
			oitavaAtual += 1;
		}
	}
        
        private boolean testeNota(char caracterLido){
		if(caracterLido >= 'A' && caracterLido <= 'G'){
			return true;
		}
		return false;
	}
        

}
