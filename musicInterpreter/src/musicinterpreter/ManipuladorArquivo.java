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
 *
 * @author Luiz Eduardo
 */
public class ManipuladorArquivo {

    /**
     *
     * @param nomeArquivo
     * @return
     */
    public static String leArquivo(String nomeArquivo){
        try{
            FileInputStream arquivoEntrada = new FileInputStream(nomeArquivo);
            InputStreamReader arquivoFormatado = new InputStreamReader(arquivoEntrada);

            int byteLido = 0;
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
     *
     * @param caminhoArquivo
     * @param textoASalvar
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
