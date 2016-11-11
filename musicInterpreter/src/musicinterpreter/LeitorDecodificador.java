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
            
            
            
            
            FileReader arquivo = new FileReader(nomeArquivo);
            BufferedReader leitorArquivo = new BufferedReader(arquivo);
            
            String linhaArquivo = leitorArquivo.readLine();
            //codigo aqui?? decodificar???
            
            while(linhaArquivo != NULL){
               //codigo aqui??
               
               linhaArquivo = leitorArquivo.readLine();
            }
            
            arquivo.close();
        }
        catch(IOException erro){
            System.err.printf("Erro na abertura do arquivo %s.\n", erro.getMessage());
        }
    }
}
