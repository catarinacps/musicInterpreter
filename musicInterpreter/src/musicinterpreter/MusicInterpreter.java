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
public class MusicInterpreter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String arquivoLido = LeitorArquivo.leArquivo("arquivoKatiau.txt");
                
        System.out.println(arquivoLido);

        Musica novaMusica = new Musica(arquivoLido);
        
        System.out.println(novaMusica.pegaNotas());
    }

}
