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



public class leitorDecodificador {
    
    static final int OITAVA_PADRAO = 5;
    static final float VOLUME_PADRAO = 64;
    static final int INSTRUMENTO_PADRAO = 1;
    
    public String leArquivo(String nomeArquivo){
        try{
            FileInputStream arquivoEntrada = new FileInputStream(nomeArquivo);
            InputStreamReader arquivoFormatado = new InputStreamReader(arquivoEntrada);

            String saidaDecodificada = "";

            int i = 0;
            int caracterLido = 0;
            char caracterAnterior = 0; 
            Nota notaTocada = new Nota();
            int oitavaAtual = OITAVA_PADRAO;
            float volumeAtual = VOLUME_PADRAO;
            int instrumentoAtual = INSTRUMENTO_PADRAO;

            do{
				caracterLido = arquivoFormatado.read();

				if(testeInstrumento((char) caracterLido)){
					alteraInstrumento((char) caracterLido, instrumentoAtual);

					saidaDecodificada += 'I' + Integer.toString(instrumentoAtual) + ' ';
				}
				else if(testeVolume((char) caracterLido)){
					alteraVolume((char) caracterLido, volumeAtual);
				}
				else if(testeOitava((char) caracterLido)){
					alteraOitava((char) caracterLido, oitavaAtual);
				}
                                else if (testeNota((char) caracterLido)){
                                    //e se a nota estiver vazia????
                                    
                                    notaTocada = decodificaEntrada((char) caracterLido, oitavaAtual, volumeAtual);
                                    
                                    //colocar a nota no stringao
				
				}
                                else{ // nenhum dos caracteres anteriores, entao repetir a nota anterior ou fazer uma pausa
                                    if (testeNota(caracterAnterior)){
                                        // colocar a nota no stringao
                                    }
                                    else{
                                        //faz o urro aka silencio ou pausa
                                    }
                                }

                                  
                                caracterAnterior = (char) caracterLido;
            }while(caracterLido != -1);

            arquivoEntrada.close();

            return saidaDecodificada;
        }
        catch(IOException erro){
            System.err.printf("Erro na abertura do arquivo %s.\n", erro.getMessage());
            return null;
        }
    }

    private Nota decodificaEntrada(char entradaChar, int oitavaAtual, float volumeAtual){
        switch(entradaChar){
            case 'A':
		Nota charDecodificado = new Nota("La", oitavaAtual, volumeAtual);
		break;
            case 'B':
                Nota charDecodificado = new Nota("Si", oitavaAtual, volumeAtual);
                break;
            case 'C':
                Nota charDecodificado = new Nota("Do", oitavaAtual, volumeAtual);
                break;
            case 'D':
                Nota charDecodificado = new Nota("Re", oitavaAtual, volumeAtual);
                break;
            case 'E':
                Nota charDecodificado = new Nota("Mi", oitavaAtual, volumeAtual);
                break;
            case 'F':
                Nota charDecodificado = new Nota("Fa", oitavaAtual, volumeAtual);
                break;
            case 'G':
                Nota charDecodificado = new Nota("Sol", oitavaAtual, volumeAtual);
		break;
        }
        return charDecodificado;
    }

	private int multiplicaVolume(float volume, float multiplicador){
		return volume*multiplicador;
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
			default:
				instrumentoAtual += Character.getNumericValue(caracterLido); // se digito numerico, somar esse digito ao codigo do instrumento atual
				break;
		}
	}

	private boolean testeVolume(char caracterLido){
		if(caracterLido == ' ' || caracterLido == 'O' || caracterLido == 'o' || caracterLido == 'I' || caracterLido == 'i' || caracterLido == 'U' || caracterLido == 'u'){
			return true;
		}
		return false;
	}

	private void alteraVolume(char caracterLido, float volumeAtual){
		if(caracterLido == ' '){
			volumeAtual = multiplicaVolume(volumeAtual, 2);
		}
		else{
			volumeAtual = multiplicaVolume(volumeAtual, 1.1);
		}
	}

	private boolean testeOitava(char caracterLido){
		if(caracterLido == '?'){
			return true;
		}
		return false;
	}

	private void alteraOitava(char caracterLido, int oitavaAtual){
		if(oitavaAtual == 8){
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
