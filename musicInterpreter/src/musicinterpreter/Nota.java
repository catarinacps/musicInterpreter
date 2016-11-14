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
    private String nomeNota;
    private int oitava;
    private int volume;
    
    public aumentaOitava(){
        if(oitava < 8)
            oitava++;
    }
    
     
    public diminuiOitava(){
        if(oitava > 1)
            oitava--;
    }
    
    public resetOitava(){
        oitava = 0;
    }
    
    public tocaNota(){
        
    }
}
