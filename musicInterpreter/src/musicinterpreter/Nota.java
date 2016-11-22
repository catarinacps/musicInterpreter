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
public class Nota {
    private char codigoNota;
    private int oitava;
    private int volume;

    /**
     * Construtor da classe Nota
     * Recebe como parametros o codigo da nota com sua oitava e volume e
     * armazena nos atributos do objeto
     * 
     * @param codigoNota char recebido sendo que A,B,C,D,E,F e G correspondem respectivamente a La,Si,Do,Re,Mi,Fa e Sol
     * @param oitava inteiro simbolizando a oitava da nota, note que o numero 0 corresponde à primeira oitava
     * @param volume inteiro simbolizando o volume da nota a ser tocada
     */
    public Nota(char codigoNota, int oitava, int volume){
        this.codigoNota = codigoNota;
        this.oitava = oitava;
        this.volume = volume;
    }

    /**
     * Apenas retorna um char que simboliza a nota a ser tocada
     * 
     * @return atributo codigoNota (char)
     */
    public char pegaNota(){
        return this.codigoNota;
    }
    
    /**
     * converte a oitava da nota (anteriormente um inteiro) para um string
     * 
     * @return string correspondente ao numero da oitava
     */
    public String pegaOitava(){
        return Integer.toString(this.oitava);
    }
    
    /**
     * converte o volume da nota (anteriormente um inteiro) para um string
     * 
     * @return string correspondente ao valor do volume
     */
    public String pegaVolume(){
        return Integer.toString(this.volume);
    }
}
