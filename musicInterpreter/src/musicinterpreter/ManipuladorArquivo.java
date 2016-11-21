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

/**
 * A classe ManipuladorArquivo realiza as operações de leitura e escrita requisitadas pelo sistema.
 * 
 * @author Henrique Silva, Nicolas Eymael e Luis Miguel
 */
public class ManipuladorArquivo {

    /**
     * Retorna o arquivo .txt inteiro em formato String.
     * É necessario receber o caminho relativo ou absoluto do arquivo de texto a ser lido.
     * 
     * @param nomeArquivo é o caminho do arquivo desejado
     * @return retorna o arquivo inteiro numa string
     */
    public static String leArquivo(String nomeArquivo){
        try{
            FileInputStream arquivoEntrada = new FileInputStream(nomeArquivo);
            InputStreamReader arquivoFormatado = new InputStreamReader(arquivoEntrada);

            int byteLido;
	    String saidaIntegral = "";

	    byteLido = arquivoFormatado.read();

            while(byteLido != -1){
                saidaIntegral += (char) byteLido;

				byteLido = arquivoFormatado.read();
            }
            
            arquivoEntrada.close();

            return saidaIntegral;
        }
        catch(IOException erro){
            System.err.printf("Erro na abertura do arquivo %s.\n", erro.getMessage());
            return null;
        }
    }

    /**
     * A função realiza a gravação de uma string num arquivo de texto cujo caminho será informado.
     * Também é necessário informar o caminho relativo ou absoluto do novo arquivo a ser criado.
     * Caso já exista um arquivo naquele caminho, o existente será sobrescrevido.
     * 
     * @param caminhoArquivo é o caminho do novo arquivo
     * @param textoASalvar é a string a ser salva
     */
    public static void salvaArquivoTexto(String caminhoArquivo, String textoASalvar){
		try{
			PrintWriter saidaTexto = new PrintWriter(caminhoArquivo);

			saidaTexto.printf(textoASalvar);
                        
                        saidaTexto.close();
		}
		catch(IOException erro){
			System.err.printf("Erro na salvatura do arquivo %s.\n", erro.getMessage());
		}
	}
}
