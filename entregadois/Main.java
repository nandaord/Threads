
public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso: java Main <numBarbeiros> <numCadeiras> <numClientes>");
            return;
        }

        int numBarbeiros = Integer.parseInt(args[0]);
        int numCadeiras = Integer.parseInt(args[1]);
        int numClientes = Integer.parseInt(args[2]);

        Barbearia barbearia = new Barbearia(numBarbeiros, numCadeiras, numClientes);

        // Inicia os barbeiros
        for (int i = 1; i <= numBarbeiros; i++) {
            Barbeiro barbeiro = new Barbeiro(i, barbearia);
            Thread t = new Thread(barbeiro);
            t.setName(String.valueOf(i)); // Define o nome da thread como o ID do barbeiro
            t.start();
        }

        // Inicia os clientes
        for (int i = 1; i <= numClientes; i++) {
            new Thread(new Cliente(barbearia)).start();
            try {
                Thread.sleep(100); // Pequeno intervalo entre a chegada dos clientes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}