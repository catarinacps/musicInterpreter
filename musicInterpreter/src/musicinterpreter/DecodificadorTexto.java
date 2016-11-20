/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicinterpreter;

/**
 *
 * @author Admin
 */
public class DecodificadorTexto {

    static final int OITAVA_PADRAO = 5;
    static final int NUMERO_DE_INSTRUMENTOS = 127;
    static final int VOLUME_MAXIMO = 127;
    static final int ULTIMA_OITAVA = 9;

    private String saidaDecodificada;

    public DecodificadorTexto(String entradaTexto, int volumeInicial, int instrumentoInicial, int indiceRitmo){
        this.saidaDecodificada = decodificaArquivo(entradaTexto, volumeInicial, instrumentoInicial, indiceRitmo);
    }

    public String pegaSaida(){
		return this.saidaDecodificada;
	}

    public String decodificaArquivo(String entradaTexto, int volumeInicial, int instrumentoInicial, int indiceInicial){
		String stringAuxiliar = "";
		Nota notaTocada = null;
		int oitavaAtual = OITAVA_PADRAO;
		int volumeAtual = volumeInicial;
		int instrumentoAtual = instrumentoInicial;
		int i = 0;
                
                float ritmoInicial = decodificaControleRitmo(indiceInicial);

		char[] entradaEmCaracteres = entradaTexto.toCharArray();
                
                stringAuxiliar += "I" + Integer.toString(instrumentoAtual) + " ";

		for(i = 0; i < entradaTexto.length(); i++){
			if(testeInstrumento(entradaEmCaracteres[i])){
				instrumentoAtual = alteraInstrumento(entradaEmCaracteres[i], instrumentoAtual);

				stringAuxiliar += "I" + Integer.toString(instrumentoAtual) + " ";
			}
			else if(testeVolume(entradaEmCaracteres[i])){
				volumeAtual = alteraVolume(entradaEmCaracteres[i], volumeAtual);
			}
			else if(testeOitava(entradaEmCaracteres[i])){
				oitavaAtual = alteraOitava(entradaEmCaracteres[i], oitavaAtual);
			}
			else if (testeNota(entradaEmCaracteres[i])){
				notaTocada = new Nota(entradaEmCaracteres[i], oitavaAtual, volumeAtual);
				stringAuxiliar += notaTocada.pegaNota() + notaTocada.pegaOitava() + '/' + Float.toString(ritmoInicial) + "a" + notaTocada.pegaVolume() + " ";

			}
			else{ // nenhum dos caracteres anteriores, entao repetir a nota anterior ou fazer uma pausa
				try{
					if (testeNota(entradaEmCaracteres[i-1])){
						stringAuxiliar += notaTocada.pegaNota() + notaTocada.pegaOitava() + '/' + Float.toString(ritmoInicial) + "a" + notaTocada.pegaVolume() + " ";
					}
					else{
						stringAuxiliar += "R" + '/' + Float.toString(ritmoInicial) + " ";
					}
				}
				catch(IndexOutOfBoundsException erroLimiteArray){
					stringAuxiliar += "R" + '/' + Float.toString(ritmoInicial) + " ";
				}
			}
		}

		return stringAuxiliar;
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
				return 7; //Harpischord
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
        
    private float decodificaControleRitmo(int indice){
        switch(indice){
            case 0: return 0.01f;
            case 1: return 0.05f;
            case 2: return 0.10f;
            case 3: return 0.15f;
            case 4: return 0.20f;
            case 5: return 0.25f;
            case 6: return 0.50f;
            case 7: return 0.75f;
            case 8: return 1.00f;
            default: return 0.25f;
        }
    }
}
