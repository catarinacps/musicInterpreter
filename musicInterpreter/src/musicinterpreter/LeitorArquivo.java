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



public class LeitorArquivo {
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

	public static void salvaArquivoTexto(String caminhoArquivo, String textoASalvar){
		try{
			PrintWriter saidaTexto = new PrintWriter(caminhoArquivo);

			saidaTexto.println(textoASalvar);
                        
                        saidaTexto.close();
		}
		catch(IOException erro){
			System.err.printf("Erro na salvatura do arquivo %s.\n", erro.getMessage());
		}
	}
}
