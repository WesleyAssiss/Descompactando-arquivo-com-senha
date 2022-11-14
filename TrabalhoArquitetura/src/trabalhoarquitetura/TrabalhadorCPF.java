/*
Wesley Elbert Assis
*/

package trabalhoarquitetura;

public class TrabalhadorCPF extends Thread {

    private int inicioCPF;
    private int fimCPF;
    private int cpf[];
    
    public TrabalhadorCPF (int inicio, int fim){
        this.inicioCPF = inicio;
        this.fimCPF = fim;
        this.cpf = new int [9];
        preencheCPFInicio();
    }
        private void preencheCPFInicio() {
        String inicioStr = "" + this.inicioCPF;
        
        int indiceCPF = 8;
        for(int i = inicioStr.length() -1; i >= 0; i--){
            cpf[indiceCPF] = Integer.parseInt(inicioStr.charAt(i) + "");
            indiceCPF--;
        }
    }
    
    
    @Override
    public void run() {
      geraTodosCpfs();
    }

    //nosso vetor terá 9 posições
    //digito10 igual a -1, ainda não foi calculado
    public  int calcDigito12(int cpf[], int digito10) {
        int faltMult = digito10 != -1 ? 11 : 10; //Avaliando se vou calcular o digito 10 ou 11
        int somaDigitosMult = 0; // variável para armazenar a soma das mult dos digitos

        //multiplicando todos os 9 digitos do cpf pelos respectivos fatores de multiplicação
        for (int i = 0; i < cpf.length; i++) {
            somaDigitosMult += cpf[i] * faltMult;
            faltMult--;
        }

        //considerando a mult. do digito 10 na nossa soma
        if (digito10 != -1) {
            somaDigitosMult += digito10 * 2;
        }

        int resto = somaDigitosMult % 11;
        if (resto < 2) {
            return 0;

        } else {
            return 11 - resto;
        }
    }

    public  int[] calculaDigitosVerif(int cpf9[]) {
        //calculamos os digitos verificadores 10° e 11° e armazenamos no array 
        int digitosVerif[] = new int[2];
        digitosVerif[0] = calcDigito12(cpf9, -1); //calculando o digito 10 do cpf
        digitosVerif[1] = calcDigito12(cpf9, digitosVerif[0]); //calculando o digito 11 do cpf

        return digitosVerif;

    }

    //soma +1 no digito 9 do cpf
    public  void incrementaCPF(int cpf[]) {
        cpf[8]++;
        int indice = 8;
        while (indice > 0 && cpf[indice] >= 10) {
            cpf[indice] = 0;
            cpf[indice - 1]++;
            indice--;
        }
    }

    public  void geraTodosCpfs() {

        int cpf[] = {0, 0, 0, 0, 0, 0, 0, 0, 1};

        //estamos gerando do cpf 000000001 até o 999999999
        for (int cpfI = this.inicioCPF; cpfI <= this.fimCPF; cpfI++) {

            int digitosVerif[] = calculaDigitosVerif(cpf);

            for (int i = 0; i < cpf.length; i++) {
                System.out.print(cpf[i]);

            }
            System.out.println(digitosVerif[0] + "" + digitosVerif[1]);

            incrementaCPF(cpf);
        }
    }



}
