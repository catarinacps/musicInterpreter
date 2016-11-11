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

public class LeitorDecodificador {
    public Nota[] leArquivo(String nomeArquivo){
        try{
            FileInputStream arquivoEntrada = new FileInputStream(nomeArquivo);
            InputStreamReader arquivoFormatado = new InputStreamReader(arquivoEntrada);
            
            long tamanho = arquivoEntrada.getChannel().size();
            
            Nota[] saidaDecoficada = new Nota[tamanho];
            
            int i = 0;
            
            do{
                int buffer = arquivoFormatado.read();
                
                saidaDecoficada[i] = decodificaEntrada((char) buffer);
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
    
    private void decodificaEntrada(char entradaChar){
        
    }
}
