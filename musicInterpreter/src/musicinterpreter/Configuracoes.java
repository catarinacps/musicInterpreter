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
public class Configuracoes {
    private final int volume;
    private final int instrumento;
    private final int ritmo;
    private final int oitava;
    
    /**
     * Essa classe existe para representar de uma maneira coesa as configuracoes
     * de Musica, que, normalmente, acabam por ocupar muitas linhas nas passagens
     * de parametros.
     * O atributo ritmo precisa passar por um metodo de decodificacao, ja que o valor recebido
     * no parametro do construtor deve ser o indice do item selecionado na GUI.
     *
     * @param volume recebe a configuracao de volume direto da interface GUI
     * @param instrumento recebe a configuracao de instrumento direto da interface GUI
     * @param indiceRitmo recebe o indice da configuracao de ritmo da ComboBox da GUI
     * @param oitava recebe a configuracao de oitava direto da interface GUI
     */
    public Configuracoes(int volume, int instrumento, int indiceRitmo, int oitava) {
        this.volume = volume;
        this.instrumento = instrumento;
        this.ritmo = decodificaControleRitmo(indiceRitmo);
        this.oitava = oitava;
    }
    
    /**
     * Metodo "get" do volume
     * 
     * @return o atributo volume do objeto
     */
    public int pegaVolume(){
        return this.volume;
    }
    
    /**
     * Metodo "get" do instrumento
     *
     * @return o atributo instrumento do objeto
     */
    public int pegaInstrumento(){
        return this.instrumento;
    }

    /**
     * Metodo "get" do ritmo
     *
     * @return o atributo ritmo do objeto
     */
    public int pegaRitmo(){
        return this.ritmo;
    }
    
    /**
     * Metodo "get" da oitava
     *
     * @return o atributo oitava do objeto
     */
    public int pegaOitava(){
        return this.oitava;
    }
    
    private int decodificaControleRitmo(int indice) {
        // o parametro indice passa por um switch basico para os ritmos previamente
        // definidos na interface GUI
        switch (indice) {
            case 0:
                return 40;
            case 1:
                return 45;
            case 2:
                return 50;
            case 3:
                return 55;
            case 4:
                return 60;
            case 5:
                return 65;
            case 6:
                return 70;
            case 7:
                return 80;
            case 8:
                return 95;
            case 9:
                return 110;
            case 10:
                return 120; //padrao
            case 11:
                return 145;
            case 12:
                return 180;
            case 13:
                return 220;
            default:
                return 120;
        }
    }
}
