import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class SimuladorMaker {

    public Simulador configurarSimulador(String caminhoJson) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(caminhoJson);
            ConfigSimulador config = gson.fromJson(reader, ConfigSimulador.class);
            reader.close();

            Simulador simulador = new Simulador(config.maxAleatorios);
            Map<Integer, Fila> mapaFilas = new HashMap<>();

            // Cria filas e adiciona no mapa
            for (ConfigFila cf : config.filas) {
                Fila f = new Fila(cf.servidores, cf.capacidade, cf.minChegada, cf.maxChegada, cf.minServico, cf.maxServico);
                mapaFilas.put(cf.id, f);
            }

            // Agora adiciona os destinos
            for (ConfigFila cf : config.filas) {
                Fila origem = mapaFilas.get(cf.id);
                if (cf.proximas != null) {
                    for (ConfigDestino destino : cf.proximas) {
                        origem.setNext(mapaFilas.get(destino.id), destino.prob);
                    }
                }
                simulador.addFila(origem);
            }

            simulador.addFirst(config.firstArrival);

            return simulador;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private class ConfigSimulador {
        int maxAleatorios;
        double firstArrival;
        List<ConfigFila> filas;
    }

    private class ConfigFila {
        int id;
        int capacidade;
        int servidores;
        int minChegada;
        int maxChegada;
        int minServico;
        int maxServico;
        List<ConfigDestino> proximas;
    }

    private class ConfigDestino {
        int id;
        double prob;
    }
}