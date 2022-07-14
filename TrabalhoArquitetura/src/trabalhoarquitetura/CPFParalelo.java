package trabalhoarquitetura;

public class CPFParalelo {



    public static void main(String[] args) {
        
        int numNucleos = 4;

       TrabalhadorCPF trabs[] = new  TrabalhadorCPF[numNucleos];
       int tamanhoFaixaCpf = 999999999 / numNucleos;
       
       
       for(int i =0; i < trabs.length; i++){
           
           int inicioFaixa = (tamanhoFaixaCpf * i) + 1; //1
           int fimFaixa = tamanhoFaixaCpf * (i + 1); //4999999999
           
           trabs[i] = new TrabalhadorCPF(inicioFaixa,fimFaixa);
           trabs[i].start();
           
       }
       //barreira de sincronização, onde iremos aguardar todos os trabs terminarem suas tarefas
       
        for (int i = 0; i < trabs.length; i++) {
            try{
                trabs[i].join();
                
            }catch(InterruptedException ex){
                System.err.println("App, encerrou e a thread estava bloqueada... ");
            }
            
        }

    }

}
