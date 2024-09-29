
import java.util.Queue;
import java.util.LinkedList;

public class Barbearia {
    private final int numBarbeiros; // Número de barbeiros
    private final Queue<Cliente> filaEspera; // Fila de espera dos clientes
    private int cadeirasDisponiveis; // Número de cadeiras de espera disponíveis
    private int clientesAtendidos = 0; // Contador de clientes atendidos
    private final int totalClientes; // Número total de clientes a serem atendidos

    public Barbearia(int numBarbeiros, int numCadeirasEspera, int totalClientes) {
        this.numBarbeiros = numBarbeiros;
        this.cadeirasDisponiveis = numCadeirasEspera;
        this.totalClientes = totalClientes;
        this.filaEspera = new LinkedList<>();
    }

    public synchronized boolean cortaCabelo(Cliente cliente) {
        if (clientesAtendidos >= totalClientes) {
            return false; // Se já atendemos todos os clientes, não aceitar mais
        }

        if (cadeirasDisponiveis > 0) {
            cadeirasDisponiveis--;  // Ocupa uma cadeira
            filaEspera.add(cliente);
            System.out.println("Cliente " + cliente.getId() + " esperando corte.");
            notify();  // Notifica apenas um barbeiro
            return true;
        } else {
            System.out.println("Cliente " + cliente.getId() + " tentou entrar na barbearia, mas está lotada.");
            return false;
        }
    }

    public synchronized Cliente proximoCliente() {
        if (filaEspera.isEmpty()) {
            return null; // Retorna null se não houver clientes na fila
        }
        return filaEspera.poll(); // Retorna o próximo cliente da fila
    }

    public synchronized void corteTerminado(Cliente cliente) {
        clientesAtendidos++; // Incrementa o número de clientes atendidos
        cadeirasDisponiveis++;  // Libera uma cadeira de espera
        System.out.println("Cliente " + cliente.getId() + " terminou o corte... saindo da barbearia!");

        if (!filaEspera.isEmpty()) {
            notify();  // Notifica um barbeiro apenas se houver clientes esperando
        }
    }
}
