
public class Pessoa {
    protected static int idCounter = 1; // Contador global de IDs
    protected int id; // ID de cada pessoa

    public Pessoa(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
