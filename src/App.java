public class App{

    public static void main(String[] args) {


        Simulador simulador = new Simulador(100000); 
        
        Fila f1 = new Fila(1, Integer.MAX_VALUE, 2, 4, 1, 2);
        
        Fila f2 = new Fila(2, 5, 0, 0, 4, 8); 
        
        Fila f3 = new Fila(2, 10, 0, 0, 5, 15); 
        
        f1.setNext(f2, 0.8);
        f1.setNext(f3, 0.2);
        
        f2.setNext(f1, 0.3);
        f2.setNext(f3, 0.5);
     
        f3.setNext(f2, 0.7);
    
        simulador.addFila(f1);
        simulador.addFila(f2);
        simulador.addFila(f3);
        
        simulador.simular(0); 

        simulador.printaHistorico();
       

    }


}