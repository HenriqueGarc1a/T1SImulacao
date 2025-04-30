import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class App{

    public static void main(String[] args) {
        

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); 
     
        JFileChooser fileChooser = new JFileChooser();

        String caminhoAtual = System.getProperty("user.dir");
        File diretorioInicial = new File(caminhoAtual);
        fileChooser.setCurrentDirectory(diretorioInicial);
        int resultado = fileChooser.showOpenDialog(frame);
        

        if (resultado == JFileChooser.APPROVE_OPTION) {
           diretorioInicial = fileChooser.getSelectedFile();
        } else {
            return;
        }

        frame.dispose(); 

        SimuladorMaker simuladorMaker = new SimuladorMaker();

        Simulador simulador = simuladorMaker.configurarSimulador(diretorioInicial.getAbsolutePath());
        
        simulador.simular(); 

        simulador.printaHistorico();

       //simulador.printaTopologia();
    
    }


}