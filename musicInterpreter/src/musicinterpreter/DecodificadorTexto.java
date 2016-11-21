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

    static final int NUMERO_DE_INSTRUMENTOS = 127;
    static final int VOLUME_MAXIMO = 127;
    static final int ULTIMA_OITAVA = 9;

    private String saidaDecodificada;

    /**
     *
     * @param entradaTexto
     * @param volumeInicial
     * @param instrumentoInicial
     * @param indiceRitmoInicial
     * @param oitavaInicial
     */
    public DecodificadorTexto(String entradaTexto, int volumeInicial, int instrumentoInicial, int indiceRitmoInicial, int oitavaInicial){
        this.saidaDecodificada = decodificaArquivo(entradaTexto, volumeInicial, instrumentoInicial, indiceRitmoInicial, oitavaInicial);
    }

    /**
     *
     * @return
     */
    public String pegaSaida(){
		return this.saidaDecodificada;
	}

    /**
     *
     * @param entradaTexto
     * @param volumeInicial
     * @param instrumentoInicial
     * @param indiceRitmoInicial
     * @param oitavaInicial
     * @return
     */
    public String decodificaArquivo(String entradaTexto, int volumeInicial, int instrumentoInicial, int indiceRitmoInicial, int oitavaInicial){
		String stringAuxiliar = "";
		Nota notaTocada = null;
		int oitavaAtual = oitavaInicial;
		int volumeAtual = volumeInicial;
		int instrumentoAtual = instrumentoInicial;
		int i = 0;

        int ritmoInicial = decodificaControleRitmo(indiceRitmoInicial);

		char[] entradaEmCaracteres = entradaTexto.toCharArray();

		stringAuxiliar += "T" + Integer.toString(ritmoInicial) + " ";
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
				oitavaAtual = alteraOitava(entradaEmCaracteres[i], oitavaAtual, oitavaInicial);
			}
			else if (testeNota(entradaEmCaracteres[i])){
				notaTocada = new Nota(entradaEmCaracteres[i], oitavaAtual, volumeAtual);
				stringAuxiliar += notaTocada.pegaNota() + notaTocada.pegaOitava() + "a" + notaTocada.pegaVolume() + " ";

			}
			else{ // nenhum dos caracteres anteriores, entao repetir a nota anterior ou fazer uma pausa
				try{
					if (testeNota(entradaEmCaracteres[i-1])){
						stringAuxiliar += notaTocada.pegaNota() + notaTocada.pegaOitava() + "a" + notaTocada.pegaVolume() + " ";
					}
					else{
						stringAuxiliar += "R ";
					}
				}
				catch(IndexOutOfBoundsException erroLimiteArray){
					stringAuxiliar += "R ";
				}
			}
		}

		return stringAuxiliar;
	}

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

	private int alteraOitava(char caracterLido, int oitavaAtual, int oitavaInicial){
		if(oitavaAtual == ULTIMA_OITAVA){
			return oitavaInicial;
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

    private int decodificaControleRitmo(int indice){
        switch(indice){
            case 0: return 40;
            case 1: return 45;
            case 2: return 50;
            case 3: return 55;
            case 4: return 60;
            case 5: return 65;
            case 6: return 70;
            case 7: return 80;
            case 8: return 95;
			case 9: return 110;
			case 10: return 120; //default
			case 11: return 145;
			case 12: return 180;
			case 13: return 220;
            default: return 120;
        }
    }
}
