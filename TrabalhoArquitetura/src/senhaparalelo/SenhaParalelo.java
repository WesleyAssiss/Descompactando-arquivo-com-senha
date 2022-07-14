package senhaparalelo;

public class SenhaParalelo {


    public static void main(String[] args) {

      int numNucleos = 4;

       TrabalhadorSenha trabs[] = new  TrabalhadorSenha[numNucleos];
      
       int tamanhoIntervalo = 74 / numNucleos;
       
       
       for(int i = 0; i < trabs.length; i++){
         int inicio = (i * tamanhoIntervalo) + 48;
         int fim = ((i + 1) * tamanhoIntervalo) + 48;
        
           
           trabs[i] = new TrabalhadorSenha(inicio,fim);
           trabs[i].start();
           
       }
       //barreira de sincronização, onde iremos aguardar todos os trabs terminarem suas tarefas
       
        for (int i = 0; i < trabs.length; i++) {
            try{
                trabs[i].join();
                
            }catch(InterruptedException ex){
                System.err.println("Uma Thread estava eecutando neste momento e foi interrompida ");
            }
            
        }

    }

}

