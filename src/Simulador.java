import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

public class Simulador {
    
    private PriorityQueue<Evento> escalonador;
    private ArrayList<Evento> historico;
    private ArrayList<Fila> filas;
    private double times;
    private int count;
    private int maxcount;

    private Evento atual;

    public Simulador(int maxcount){

        filas = new ArrayList<>();
        escalonador = new PriorityQueue<>(Comparator.comparingDouble(t -> t.getTime()));
        historico = new ArrayList<>();

        this.count = 0;
        this.maxcount = maxcount;

    }


    public void addFila(Fila fila){

        filas.add(fila);

    }

    public void addFirst(double firstArrival){

        atual = new Evento(EventoTipo.Chegada, firstArrival, filas.get(0));

    }

    public void simular(){

        escalonador.add(atual);
        historico.add(atual);

        atual = escalonador.poll();

        do{

            times = atual.getTime();

            if(atual.getTipo() == EventoTipo.Chegada){

                chegada();

            }
            else if(atual.getTipo() == EventoTipo.Saida){

                saida();

            }
            else{

                chegadaPorPassagem();

            }

            atual = escalonador.poll();

            if(count >= maxcount)
                break;

        }while(true);
    
    
    }
        

    private void chegada(){

        if(atual.getFila().status() < atual.getFila().capacity()){

            atual.getFila().in(atual.getTime());

            if(atual.getFila().status() <= atual.getFila().servers()){

                Evento proximosaida = new Evento(EventoTipo.Saida,atual.getFila().getNextService(times), atual.getFila());
                count++;
                escalonador.add(proximosaida);
                historico.add(proximosaida);
            }
            

        }
        else{

            atual.getFila().loss();

        }

        Evento proximochegada = new Evento(EventoTipo.Chegada,atual.getFila().getNextArriva(times), atual.getFila());
        count++;

        escalonador.add(proximochegada);
        historico.add(proximochegada);


    }

    private void chegadaPorPassagem(){

        if(atual.getFila().status() < atual.getFila().capacity()){

            atual.getFila().in(atual.getTime());

            if(atual.getFila().status() <= atual.getFila().servers()){

                Evento proximosaida = new Evento(EventoTipo.Saida,atual.getFila().getNextService(times), atual.getFila());
                count++;

                escalonador.add(proximosaida);
                historico.add(proximosaida);
            }
            

        }
        else{

            atual.getFila().loss();

        }

    }

    private void saida(){

        atual.getFila().out(atual.getTime());

        if(atual.getFila().status() >= atual.getFila().servers()){

            Evento proximosaida = new Evento(EventoTipo.Saida,atual.getFila().getNextService(times), atual.getFila());
            count++;
            escalonador.add(proximosaida);
            historico.add(proximosaida);
        }

        if(atual.getFila().hasNext()){

            Fila next = atual.getFila().getNext();

            if(next == null)
                return;

            Evento proximochegadaporpassagem = new Evento(EventoTipo.ChegadaPorPassagem,times, next);
            count++;
            escalonador.add(proximochegadaporpassagem);
            historico.add(proximochegadaporpassagem);

        
        }

    }

   
    public void printaHistorico() {
        for (int i = 0; i < filas.size(); i++) {
            System.out.println("=== Histórico da Fila " + (i+1) + " ===");
            System.out.println();

            filas.get(i).printaAcumulado();
            System.out.println();
        }

      
    }

    public void printaTopologia() {
    System.out.println("==== SISTEMA DE FILAS ====\n");

    for (int i = 0; i < filas.size(); i++) {
        Fila fila = filas.get(i);
        System.out.println("Fila " + i + ":");
        System.out.println("  - Servidores: " + fila.servers());
        System.out.println("  - Capacidade: " + fila.capacity());
        System.out.printf("  - Tempo de chegada: [%.2f, %.2f]%n", fila.getMinArrival(), fila.getMaxArrival());
        System.out.printf("  - Tempo de serviço: [%.2f, %.2f]%n", fila.getMinService(), fila.getMaxService());

        if (fila.hasNext()) {
            System.out.println("  - Conectada para:");
            for (Map.Entry<Fila, Double> entry : fila.getNextMap().entrySet()) {
                int destino = filas.indexOf(entry.getKey());
                System.out.printf("    -> Fila %d (%.2f%%)%n", destino, entry.getValue() * 100);
            }
        } else {
            System.out.println("  - Sem conexões para outras filas.");
        }

        System.out.println();
    }
}

   
        

   

}
