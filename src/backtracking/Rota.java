package backtracking;
public class Rota {
    private int id;
    private int distancia;

    public Rota(int id, int distancia) {
        this.id = id;
        this.distancia = distancia;
    }

    public int getId() {
        return this.id;
    }

    public int getDistancia() {
        return this.distancia;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + " | Distância: " + this.distancia;
    }
}
