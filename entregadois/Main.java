package entregadois;

public class Main {
    public static void main(String[] args) {
        Barbearia barbearia = new Barbearia(3, 5); // 3 barbeiros e 5 cadeiras de espera

        // Inicia os barbeiros
        for (int i = 1; i <= 3; i++) {
            new Thread(new Barbeiro(i, barbearia)).start();
        }

        // Inicia os clientes
        for (int i = 1; i <= 10; i++) {
            new Thread(new Cliente(barbearia)).start();
        }
    }
}
