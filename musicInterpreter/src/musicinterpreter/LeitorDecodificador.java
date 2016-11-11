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
            
            do{
                int buffer = arquivoFormatado.read();
                
                //faz alguma coisa com o buffer, tipo decodificar sla memes
            }while(buffer != -1);
            
            arquivo.close();
        }
        catch(IOException erro){
            System.err.printf("Erro na abertura do arquivo %s.\n", erro.getMessage());
        }
    }
}
