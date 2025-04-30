public class Evento {

    private double times;
    private EventoTipo tipo;
    private Fila fila;

    public Evento(EventoTipo evento, double times,Fila fila){

        this.times = times;
        this.tipo = evento;
        this.fila = fila;

    }

    public EventoTipo getTipo(){

        return this.tipo;

    }

    public double getTime(){

        return this.times;

    }

    public Fila getFila(){

        return this.fila;

    }

}