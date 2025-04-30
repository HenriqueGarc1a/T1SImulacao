import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Fila {

    private int servers;
    private int capacity;
    private double minArrival;
    private double maxArrival;
    private double minService;
    private double maxService;
    private int costumers;
    private int loss;
    private ArrayList<Double> temposAcumulados;
    private double last;


    private HashMap<Fila,Double> next = new HashMap<>();
    private Random rand = new Random();

    public Fila(int servers,int capacity,double minArrival,double maxArrival,double minService,double maxService){

        this.servers = servers;
        this.capacity = capacity;
        this.maxArrival = maxArrival;
        this.minArrival = minArrival;
        this.maxService = maxService;
        this.minService = minService;

        temposAcumulados = new ArrayList<>();

        last = 0;
            
    }

    public int status(){

        return this.costumers;

    }

    public int capacity(){

        return this.capacity;

    }
    
    public int servers(){

        return this.servers;

    }

    public int getloss(){

        return this.loss;

    }

    public void loss(){

        this.loss++;

    }

    public void in(double tempo){

        this.costumers++;
        acumula(tempo-last);
        last = tempo;
    }

    public void out(double tempo) {

        this.costumers--;
        acumula(tempo-last);
        last = tempo;


    }

    public void setNext(Fila fila,Double probabilidade){

        next.put(fila, probabilidade);

    }


    public Fila getNext(){

        double r = rand.nextDouble(); 
        double acumulado = 0.0;
    
        for (Map.Entry<Fila, Double> entrada : next.entrySet()) {
            acumulado += entrada.getValue();
            if (r <= acumulado) {
                return entrada.getKey(); 
            }
        }
    
        
        return null;

    }

        public double getMinArrival() {
            return minArrival;
        }
        
        public double getMaxArrival() {
            return maxArrival;
        }
        
        public double getMinService() {
            return minService;
        }
        
        public double getMaxService() {
            return maxService;
        }

        public double getNextService(double times){

            
            return times+rand.nextDouble(minService,maxService);


        }

        public double getNextArriva(double times){

            
            return times+rand.nextDouble(minArrival,maxArrival);


        }

        public boolean hasNext(){

            return !next.isEmpty();

        }


        private void acumula(double tempo){

     
            if(status() < temposAcumulados.size()){
                
                double x = temposAcumulados.get(status());
                x += tempo;
                temposAcumulados.set(status(),x);

            }
            else{

                while(status() >= temposAcumulados.size()){

                    temposAcumulados.add(tempo);

                }


            }


        }

        public void printaAcumulado() {
            double total = 0.0;
        
            for (double tempo : temposAcumulados) {
                total += tempo;
            }
        
            System.out.printf("%-10s %-20s %-15s%n", "Clientes", "Tempo Acumulado", "Probabilidade");
        
            for (int i = 0; i < temposAcumulados.size(); i++) {
                double tempo = temposAcumulados.get(i);
                double porcentagem = (total > 0) ? (tempo / total) * 100 : 0;
        
                System.out.printf("%-10d %-20.4f %-14.2f%%%n", i, tempo, porcentagem);
            }
        
            System.out.printf("%nTempo total acumulado: %.4f%n", total);
            System.out.printf("Total de clientes perdidos: %d%n", this.loss);
        }
    

    public HashMap<Fila, Double> getNextMap() {
        return next;
    }

}

    



