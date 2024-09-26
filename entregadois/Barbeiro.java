package entregadois;

import java.util.Random;

public class Barbeiro implements Runnable {
    private final Barbearia barbearia; 
    private final int numero; // Número do barbeiro

    public Barbeiro(int numero, Barbearia barbearia) {
        this.numero = numero;
        this.barbearia = barbearia;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                Cliente cliente = barbearia.proximoCliente(); // Pega o próximo cliente da fila
                if (cliente != null) {
                    // Aloca uma cadeira para o barbeiro de forma segura
                    int cadeira = barbearia.alocarCadeira();
                    System.out.println("Cliente " + cliente.getId() + " cortando cabelo com Barbeiro " + numero + " na cadeira " + cadeira);

                    Thread.sleep(random.nextInt(3000) + 2000); // Simula o tempo de corte de cabelo

                    // Libera a cadeira após o corte
                    barbearia.liberarCadeira(cadeira);
                    barbearia.corteTerminado(cliente);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
