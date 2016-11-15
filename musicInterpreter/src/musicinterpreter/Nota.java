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

    public Nota(char codigoNota, int oitava, int volume){
        this.codigoNota = codigoNota;
        this.oitava = oitava;
        this.volume = volume;
    }

    
    public char pegaNota(){
        return this.codigoNota;
    }
    
    public String pegaOitava(){
        return Integer.toString(this.oitava);
    }
    
    public String pegaVolume(){
        return Integer.toString(this.volume);
    }
}
