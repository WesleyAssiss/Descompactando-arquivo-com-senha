package senhaparalelo;

import java.io.File;
import java.util.List;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;

public class TrabalhadorSenha extends Thread {

    //inteiros para demilitar a faixa de trabalha
    private int charInicial;
    private int charFinal;
    private int senha[];
    private static boolean encontrou = false;

    public TrabalhadorSenha(int inicio, int fim) {
        this.charInicial = inicio;
        this.charFinal = fim;
        this.senha = new int[7];

        preencherSenhaInicial();

    }

    private void preencherSenhaInicial() {
        //desconsiderando os caracteres especiais da tabela ASCII
        this.charInicial = normalizarValorASCII(this.charInicial);
        this.charFinal = normalizarValorASCII(this.charFinal);

        senha[0] = this.charInicial;

        //inicializando a partir do segundo char com o valor de "0"
        for (int i = 1; i < senha.length; i++) {
            senha[i] = 48;
        }
    }

    //normalização para desconsiderar caracteres especiais que não estão na senha
    private int normalizarValorASCII(int valor) {
        if (valor >= 58 && valor <= 64) {
            return 65;
        }
        if (valor >= 91 && valor <= 96) {
            return 97;
        }

        return valor;
    }

    @Override
    public void run() {
        geraSenhas();

    }

    //passa para a próxima senha
    public void incrementaSenha(int senha[]) {

        //incrementa o último símbolo da senha
        senha[senha.length - 1]++;
        int indiceVerf = senha.length - 1;

        //respeitando os intervalos [0-9] [A-Z] [a-z]
        while (indiceVerf >= 0 && (senha[indiceVerf] == 58 || senha[indiceVerf] == 91
                || senha[indiceVerf] == 123)) {
            switch (senha[indiceVerf]) {
                //intervalo dos numeros 
                case 58: {
                    senha[indiceVerf] = 65;
                    break;
                }
                //intervalo das letras maiuscula
                case 91: {
                    senha[indiceVerf] = 97;
                    break;
                }
                //intervalo letras minusculas.
                case 123: {
                    senha[indiceVerf] = 48;
                    senha[indiceVerf - 1]++;
                    indiceVerf--;
                    break;
                }

            }

        }
    }

    //esse método verifica se já geramos todas as senhas, ou seja chegamos ao limite zzzzzz
    public boolean todasForamGeradas(int senha[]) {

        //verifica para a primeira posição separado por conta da faixa de valor 
        if (senha[0] != this.charFinal) {
            return false;
        }

        //verifica a partir da segunda posição de todos os char's são z
        for (int i = 1; i < senha.length; i++) { //zzzzz
            // algum simbolo não é igual a "z"
            if (senha[i] != 122) {
                return false;
            }

        }

        //todos os símbolos são "z"
        return true;

    }

    //imprimir a senha
    public char[] imprimeSenha(int senha[]) {

        char vetChar[] = new char[senha.length];

        for (int i = 0; i < senha.length; i++) {
            vetChar[i] = (char) senha[i];
            System.out.print(vetChar[i] + "");
        }
        System.out.println("");
        return vetChar;
    }

    //metodo responsável por gerar todas as senhas
    public void geraSenhas() {

        
        //enquanto não gerar novas senhas imprime a senha e gera uma nova com o incremento 
        do {

            //descompactar arquivo com senha
            try {
                //este é o arquivo que vamos descompactar
                ZipFile zipFile = new ZipFile(new File("C:\\Users\\Downloads\\ZIP4J\\EncontrarSenha"));
                if (zipFile.isEncrypted()) {
                    //tentando a senha atual

                    zipFile.setPassword(imprimeSenha(this.senha));
                }
                List fileHeaderList = zipFile.getFileHeaders();

                //tivemos sucesso e os arquivos foram descompactados
                for (int i = 0; i < fileHeaderList.size(); i++) {
                    FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);

                    zipFile.extractFile(fileHeader, "C:\\Users\\Downloads");
                    System.out.println("Sucesso essa é a senha");
                    imprimeSenha(this.senha);

                    //vamos parar a execução das Threads
                    encontrou = true;
                }
               
                //Várias tentativas utilizando Força Bruta    
            } catch (Exception e) {
                System.out.println(imprimeSenha(senha));
            }

            incrementaSenha(senha);

        } while (!todasForamGeradas(senha) && !encontrou);

    }

}
