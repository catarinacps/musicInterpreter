/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicinterpreter;

/**
 *
 * @author Henrique Silva, Nicolas Eymael e Luis Miguel
 */
public class DecodificadorTexto {

    static final int NUMERO_DE_INSTRUMENTOS = 127;
    static final int VOLUME_MAXIMO = 127;
    static final int ULTIMA_OITAVA = 9;

    private String saidaDecodificada;

    /**
     * Construtor da classe DecodificadorTexto
     * A biblioteca utilizada (JFugue) é capaz de reproduzir musica utilizando uma string como entrada,
     * pensando nisso utilizamos 5 parametros para formar a string que contem as notas musicais a serem reproduzidas,
     * são eles: Entrada do Usuario, Volume, Instrumento, Ritmo e Oitava.
     *
     * 
     * @param entradaTexto  texto a ser decodificado, pode ter sido originado de um arquivo .txt ou diretamente escrito pelo usuario
     * @param volumeInicial volume inicializado pelo usuario nas configuraçoes (default 64, min 0, max 127)
     * @param instrumentoInicial    instrumento inicializado pelo usuario nas configuraçoes (default 0 (piano acustico), min 0, max 127)
     * @param indiceRitmoInicial    indice do ritmo inicializado pelo usuario nas configuraçoes, o indice será decodificado para o ritmo correspondente (em BPM)
     * @param oitavaInicial oitava das notas inicializada pelo usuario nas configuraçoes (default 4, min 0, max 9)
     */
    public DecodificadorTexto(String entradaTexto, int volumeInicial, int instrumentoInicial, int indiceRitmoInicial, int oitavaInicial){
        this.saidaDecodificada = decodificaArquivo(entradaTexto, volumeInicial, instrumentoInicial, indiceRitmoInicial, oitavaInicial);
    }

    /**
     * Retorna a string com as notas musicais, como um metodo get()
     * 
     * @return string com todas as notas das musica, ja decodificadas e bem definidas
     */
    public String pegaSaida(){
		return this.saidaDecodificada;
	}

    /**
     * Recebe as entrada codificada das notas junto com os parametros caracteristicos da musica (volume, instrumento, ritmo e oitava)
     * com isso utiliza-se um loop for percorrendo o texto de entrada e a string contendo a musica é montada em cada iteração
     * veja que cada caracter do texto de entrada pode estar em um dos 4 casos, que são:
     *      alteração no instrumento a ser tocado
     *      alteração no volume da musica
     *      alteração na oitava da nota
     *      nota a ser tocada
     *      repetição ou pausa, de acordo com o caracter anterior
     * 
     * 
     * @param entradaTexto  texto a ser decodificado, pode ter sido originado de um arquivo .txt ou diretamente escrito pelo usuario
     * @param volumeInicial volume inicializado pelo usuario nas configuraçoes (default 64, min 0, max 127)
     * @param instrumentoInicial    instrumento inicializado pelo usuario nas configuraçoes (default 0 (piano acustico), min 0, max 127)
     * @param indiceRitmoInicial    indice do ritmo inicializado pelo usuario nas configuraçoes, o indice será decodificado para o ritmo correspondente (em BPM)
     * @param oitavaInicial oitava das notas inicializada pelo usuario nas configuraçoes (default 4, min 0, max 9)
     * @return  uma string decodificada, contendo a musica com o volume e o instrumento a ser tocado em cada instante, assim como cada uma das notas tocadas
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
