package entregadois;

import java.util.Random;

public class Cliente implements Runnable {
    private static int idCounter = 1; //contador que gera id
    private final int id; // id
    private final Barbearia barbearia; // barbearia que ele vai

    public Cliente(Barbearia barbearia) { 
        this.barbearia = barbearia;
        this.id = idCounter++; //id unico
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() { //thread
        Random random = new Random(); //tempo de espera aleatorio
        while (true) {
            try {
                boolean atendido = barbearia.cortaCabelo(this);//tenta ser atendido
                if (atendido) {
                    Thread.sleep(random.nextInt(5000) + 3000); // Simula o tempo de espera após corte
                } else {
                    Thread.sleep(random.nextInt(5000) + 3000); // Simula o tempo de espera antes de tentar novamente
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
