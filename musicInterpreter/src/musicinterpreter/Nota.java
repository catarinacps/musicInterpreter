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
public class Nota {
    private char codigoNota;
    private int oitava;
    private int volume;

    /**
     *
     * @param codigoNota
     * @param oitava
     * @param volume
     */
    public Nota(char codigoNota, int oitava, int volume){
        this.codigoNota = codigoNota;
        this.oitava = oitava;
        this.volume = volume;
    }

    /**
     *
     * @return
     */
    public char pegaNota(){
        return this.codigoNota;
    }
    
    /**
     *
     * @return
     */
    public String pegaOitava(){
        return Integer.toString(this.oitava);
    }
    
    /**
     *
     * @return
     */
    public String pegaVolume(){
        return Integer.toString(this.volume);
    }
}
