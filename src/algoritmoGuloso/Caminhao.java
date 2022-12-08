package algoritmoGuloso;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Caminhao que possui ID, Soma e Rotas
 * 
 * O atributo Soma faz o acompanhamento do valor total em Km de rotas que ele
 * possui(Mesma ideia de uma Classe de Partição)
 */
public class Caminhao {
    private int id;
    private int soma;
    private List<Rota> rotas;

    Caminhao(int id) {
        this.id = id;
        this.soma = 0;
        this.rotas = new ArrayList<>();
    }

    public void adicionaSoma(int quantidade) {
        this.soma += quantidade;
    }

    public void adicionarRota(Rota rota) {
        rotas.add(rota);
    }

    public int getId() {
        return this.id;
    }

    public int getSoma() {
        return this.soma;
    }

    public void getRotas() {
        for (Rota rota : this.rotas) {
            System.out.println(rota.toString());
        }
    }

    public int getQtdeRotas() {
        return this.rotas.size();
    }

    public int getTotalKm() {
        return this.rotas.stream().map(Rota::getDistancia).reduce(0, Integer::sum);
    }
}
