package entregadois;

import java.util.LinkedList;
import java.util.Queue;

public class Barbearia {
    private final int numBarbeiros; //n de barbeiros
    private final int numCadeirasEspera; //n de cadeiras
    private final Queue<Cliente> filaEspera; //fila de espera
    public final boolean[] cadeirasBarbeiro; // True se ocupada e False livre
    public final Object lock = new Object(); // Monitor para sincronização

    public Barbearia(int numBarbeiros, int numCadeirasEspera) {
        this.numBarbeiros = numBarbeiros;
        this.numCadeirasEspera = numCadeirasEspera;
        this.filaEspera = new LinkedList<>();
        this.cadeirasBarbeiro = new boolean[numBarbeiros];
        for (int i = 0; i < numBarbeiros; i++) {
            cadeirasBarbeiro[i] = false;
        }
    }

    public boolean cortaCabelo(Cliente cliente) {
        synchronized (lock) {
            if (filaEspera.size() < numCadeirasEspera) {
                filaEspera.add(cliente); //entra na fila de espera
                System.out.println("Cliente " + cliente.getId() + " esperando corte.");
                lock.notifyAll(); // Notifica os barbeiros que há um cliente esperando
                return true;
            } else {
                System.out.println("Cliente " + cliente.getId() + " tentou entrar na barbearia, mas está lotada... indo dar uma voltinha."); //barbearia cheia
                return false;
            }
        }
    }

    public Cliente proximoCliente() throws InterruptedException {
        synchronized (lock) {
            while (filaEspera.isEmpty()) {
                System.out.println("Barbeiro esperando cliente...");
                lock.wait(); // Espera até que um cliente chegue
                System.out.println("Barbeiro acordou! Começando os trabalhos!");
            }
            return filaEspera.poll(); // Retorna o próximo cliente
        }
    }

    public void corteTerminado(Cliente cliente) {
        synchronized (lock) {
            System.out.println("Cliente " + cliente.getId() + " terminou o corte... saindo da barbearia!");
            lock.notifyAll(); // Notifica os clientes e barbeiros
        }
    }

    // Método auxiliar para alocar uma cadeira para o barbeiro de forma segura
    public int alocarCadeira() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                for (int i = 0; i < cadeirasBarbeiro.length; i++) {
                    if (!cadeirasBarbeiro[i]) {
                        cadeirasBarbeiro[i] = true; // Marca a cadeira como ocupada
                        return i;
                    }
                }
                lock.wait(); // Espera até que uma cadeira seja liberada
            }
        }
    }

    // Método auxiliar para liberar a cadeira após o corte
    public void liberarCadeira(int cadeira) {
        synchronized (lock) {
            cadeirasBarbeiro[cadeira] = false; // Libera a cadeira
            lock.notifyAll(); // Notifica que uma cadeira foi liberada
        }
    }
}

