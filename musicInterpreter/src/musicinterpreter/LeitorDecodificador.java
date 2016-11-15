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
    static final int VOLUME_PADRAO = 64;
    static final int INSTRUMENTO_PADRAO = 1;
    
    public String leArquivo(String nomeArquivo){
        try{
            FileInputStream arquivoEntrada = new FileInputStream(nomeArquivo);
            InputStreamReader arquivoFormatado = new InputStreamReader(arquivoEntrada);

            String saidaDecodificada = "";

            int i = 0;
            int buffer = 0;
            Nota anterior = new Nota();
            int oitavaAtual = OITAVA_PADRAO;
            float volumeAtual = VOLUME_PADRAO;
            int instrumentoAtual = INSTRUMENTO_PADRAO;

            do{
				buffer = arquivoFormatado.read();

				if(testeInstrumento((char) buffer)){
					alteraInstrumento((char) buffer, instrumentoAtual);

					saidaDecodificada += 'I' + Integer.toString(instrumentoAtual) + ' ';
				}
				else if(testeVolume((char) buffer)){
					alteraVolume((char) buffer, volumeAtual);
				}
				else if(testeOitava((char) buffer)){
					alteraOitava((char) buffer, oitavaAtual);
				}
				else{
					
				}


				//--------------------i do'nt know wtf is happening------------------------------------
                                saidaDecodificada[i] = decodificaEntrada((char) arquivoFormatado.read(), anterior, oitavaAtual, volumeAtual);

				anterior = saidaDecodificada[i];
				i++;
                                  //faz alguma coisa com o buffer, tipo decodificar sla memes
            }while(buffer != -1);

            arquivoEntrada.close();

            return saidaDecodificada;
        }
        catch(IOException erro){
            System.err.printf("Erro na abertura do arquivo %s.\n", erro.getMessage());
            return null;
        }
    }

    private Nota decodificaEntrada(char entradaChar, Nota anterior, int oitavaAtual, int volumeAtual){
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

	private int multiplicaVolume(int volume, int multiplicador){
		return volume*multiplicador;
	}

	private boolean testeInstrumento(char buffer){
		if(buffer == '!' || (buffer >= '0' && buffer <= '9') || buffer == '\n' || buffer == ';' || buffer == ','){
			return true;
		}
		return false;
	}

	private void alteraInstrumento(char buffer, int instrumentoAtual){
		switch(buffer){
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
				instrumentoAtual += Character.getNumericValue(buffer); // se digito numerico, somar esse digito ao codigo do instrumento atual
				break;
		}
	}

	private boolean testeVolume(char buffer){
		if(buffer == ' ' || buffer == 'O' || buffer == 'o' || buffer == 'I' || buffer == 'i' || buffer == 'U' || buffer == 'u'){
			return true;
		}
		return false;
	}

	private void alteraVolume(char buffer, int volumeAtual){
		if(buffer == ' '){
			volumeAtual = multiplicaVolume(volumeAtual, 2);
		}
		else{
			volumeAtual = multiplicaVolume(volumeAtual, 1.1);
		}
	}

	private boolean testeOitava(char buffer){
		if(buffer == '?'){
			return true;
		}
		return false;
	}

	private void alteraOitava(char buffer, int oitavaAtual){
		if(oitavaAtual == 8){
			oitavaAtual = OITAVA_PADRAO;
		}
		else{
			oitavaAtual += 1;
		}
	}
}
