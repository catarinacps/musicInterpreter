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
     * Construtor da classe.
     * Recebe as entrada codificada das notas junto com os parametros
     * caracteristicos da musica (volume, instrumento, ritmo e oitava) com isso
     * utiliza-se um loop for percorrendo o texto de entrada e a string contendo
     * a musica é montada em cada iteração veja que cada caracter do texto de
     * entrada pode estar em um dos 4 casos, que são: alteração no instrumento a
     * ser tocado alteração no volume da musica alteração na oitava da nota nota
     * a ser tocada repetição ou pausa, de acordo com o caracter anterior
     *
     *
     * @param entradaTexto texto a ser decodificado, pode ter sido originado de
     * um arquivo .txt ou diretamente escrito pelo usuario
     * @param configIniciais é o objeto que contém todas as configurações iniciais da musica,
     * que mais tarde é quebrado em seus respectivos atributos
     */
    public DecodificadorTexto(String entradaTexto, Configuracoes configIniciais) {
        Nota notaTocada = null;
        int oitavaAtual = configIniciais.pegaOitava();
        int volumeAtual = configIniciais.pegaVolume();
        int instrumentoAtual = configIniciais.pegaInstrumento();
        int ritmoInicial = configIniciais.pegaRitmo();

        char[] entradaEmCaracteres = entradaTexto.toCharArray();

        this.saidaDecodificada = "T" + Integer.toString(ritmoInicial) + " ";
        this.saidaDecodificada += "I" + Integer.toString(instrumentoAtual) + " ";

        for (int i = 0; i < entradaTexto.length(); i++) {
            if (testeInstrumento(entradaEmCaracteres[i])) {
                instrumentoAtual = modificaInstrumento(entradaEmCaracteres[i], instrumentoAtual);

                this.saidaDecodificada += "I" + Integer.toString(instrumentoAtual) + " ";
            } else if (testeVolume(entradaEmCaracteres[i])) {
                volumeAtual = modificaVolume(entradaEmCaracteres[i], volumeAtual);
            } else if (testeOitava(entradaEmCaracteres[i])) {
                oitavaAtual = modificaOitava(oitavaAtual, configIniciais.pegaOitava());
            } else if (testeNota(entradaEmCaracteres[i])) {
                notaTocada = new Nota(entradaEmCaracteres[i], oitavaAtual, volumeAtual);
                this.saidaDecodificada += notaTocada.pegaNota() + notaTocada.pegaOitava() + "a" + notaTocada.pegaVolume() + " ";
            } else { // nenhum dos caracteres anteriores, entao repetir a nota anterior ou fazer uma pausa
                try {
                    if (testeNota(entradaEmCaracteres[i - 1])) {
                        this.saidaDecodificada += notaTocada.pegaNota() + notaTocada.pegaOitava() + "a" + notaTocada.pegaVolume() + " ";
                    } else {
                        this.saidaDecodificada += "R ";
                    }
                } catch (IndexOutOfBoundsException erroLimiteArray) {
                    this.saidaDecodificada += "R ";
                }
            }
        }
    }

    /**
     * Retorna a string com as notas musicais, como um metodo get()
     *
     * @return string com todas as notas das musica, ja decodificadas e bem
     * definidas
     */
    public String pegaSaida() {
        return this.saidaDecodificada;
    }

    private int multiplicaVolume(int volume, float multiplicador) {
        float multiplicacaoFloat = volume * multiplicador;		//utilizamos float ja que, na definicao, aumenta-se 10%
        int multiplicacaoInt = Math.round(multiplicacaoFloat);

        if (multiplicacaoInt > VOLUME_MAXIMO) {
            multiplicacaoInt = VOLUME_MAXIMO;
        }
        return multiplicacaoInt;
    }

    private boolean testeInstrumento(char caracterLido) {
        return caracterLido == '!' || (caracterLido >= '0' && caracterLido <= '9') ||
               caracterLido == '\n' || caracterLido == ';' || caracterLido == ',';
    }

    private int modificaInstrumento(char caracterLido, int instrumentoAtual) {
        switch (caracterLido) {
            case '!':
                return 7; //Harpischord
            case '\n':
                return 15; //Tubular Bells
            case ';':
                return 76; //Pan Flute
            case ',':
                return 20; //Church Organ
            default: // se digito numerico, somar esse digito ao codigo do instrumento atual
                if ((instrumentoAtual += Character.getNumericValue(caracterLido)) > NUMERO_DE_INSTRUMENTOS) {
                    return instrumentoAtual -= NUMERO_DE_INSTRUMENTOS;
                }
                return instrumentoAtual;
        }
    }

    private boolean testeVolume(char caracterLido) {
        return caracterLido == ' ' || caracterLido == 'O' || caracterLido == 'o' || caracterLido == 'I' ||
               caracterLido == 'i' || caracterLido == 'U' || caracterLido == 'u';
    }

    private int modificaVolume(char caracterLido, int volumeAtual) {
        if (caracterLido == ' ') {
            return multiplicaVolume(volumeAtual, 2f);
        } else {
            return multiplicaVolume(volumeAtual, 1.1f);
        }
    }

    private boolean testeOitava(char caracterLido) {
        return caracterLido == '?';
    }

    private int modificaOitava(int oitavaAtual, int oitavaInicial) {
        if (oitavaAtual == ULTIMA_OITAVA) {
            return oitavaInicial;
        } else {
            return oitavaAtual += 1;
        }
    }

    private boolean testeNota(char caracterLido) {
        return caracterLido >= 'A' && caracterLido <= 'G';
    }

}
