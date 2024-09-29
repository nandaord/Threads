
import java.util.Random;

public class Cliente extends Pessoa implements Runnable {
    private final Barbearia barbearia;
    private static final int MAX_TENTATIVAS = 5;

    public Cliente(Barbearia barbearia) {
        super(idCounter++); // Chama o construtor da superclasse Pessoa para gerar o ID
        this.barbearia = barbearia;
    }

    @Override
    public void run() {
        Random random = new Random();
        int tentativas = 0;

        while (tentativas < MAX_TENTATIVAS) {
            try {
                System.out.println("Cliente " + id + " tentando entrar na barbearia...");
                boolean atendido = barbearia.cortaCabelo(this); // Tenta ser atendido
                if (atendido) {
                    System.out.println("Cliente " + id + " sendo atendido.");
                    Thread.sleep(random.nextInt(3000) + 3000); // Simula o tempo de espera após o corte
                    break; // Sai do loop após ser atendido
                } else {
                    System.out.println("Cliente " + id + " tentou entrar na barbearia, mas está lotada... indo dar uma voltinha.");
                    Thread.sleep(random.nextInt(2000) + 3000); // Espera antes de tentar novamente
                    tentativas++;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // Sai do loop em caso de interrupção
            }
        }

        if (tentativas == MAX_TENTATIVAS) {
            System.out.println("Cliente " + id + " desistiu após " + MAX_TENTATIVAS + " tentativas.");
        }
    }
}
