package entregadois;

import java.util.Random;

public class Barbeiro extends Pessoa implements Runnable {
    private final Barbearia barbearia;

    public Barbeiro(int id, Barbearia barbearia) {
        super(id); // Chama o construtor da superclasse
        this.barbearia = barbearia;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                Cliente cliente;
                synchronized (barbearia) {
                    cliente = barbearia.proximoCliente();
                    if (cliente == null) {
                        System.out.println("Barbeiro " + id + " indo dormir um pouco... não há clientes na barbearia...");
                        barbearia.wait(); // Espera até que um cliente esteja disponível
                        continue; // Retorna ao início do loop após ser notificado
                    }
                }

                // Simula o corte de cabelo
                System.out.println("Cliente " + cliente.getId() + " cortando cabelo com Barbeiro " + id);
                Thread.sleep(random.nextInt(3000) + 2000); // Simula o tempo de corte de cabelo
                barbearia.corteTerminado(cliente); // Registra o término do corte
                System.out.println("Barbeiro " + id + " terminou o corte para Cliente " + cliente.getId() + ".");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
