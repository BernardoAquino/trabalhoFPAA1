import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class App {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        /**
         * Arquivos de teste/leitura
         */
        Arquivo arquivo = new Arquivo();
        List<List<Integer>> dados = arquivo.lerArquivo("caminhoes_compacto");
        // List<List<Integer>> dados = arquivo.lerArquivo("caminhoes_disperso");
        // List<List<Integer>> dados = arquivo.lerArquivo("caminhoes_longo");

        /**
         * Preenchimento do número de caminhões, criação da lista de rotas e início da
         * cronometragem
         */
        int numeroCaminhoes = 0;
        List<Rota> rotas = new ArrayList<>();
        long inicioGuloso = System.nanoTime();

        for (List<Integer> lista : dados) {
            if (lista.size() == 1)
                numeroCaminhoes = lista.get(0);
            else
                rotas.add(new Rota(lista.get(0), lista.get(1)));
        }
        System.out.println("Número de caminhões: " + numeroCaminhoes);
        System.out.println("Rotas Utilizadas: " + rotas);

        /**
         * Teste do Algoritmo Guloso(Listagem dos caminhões, suas rotas e o total em Km)
         * 
         * O algoritmo itera por cada rota e adiciona ao caminhão com a menor soma de
         * rotas até o momento.
         */
        Collection<Caminhao> caminhoes = algoritmoGuloso(rotas, numeroCaminhoes);
        long terminoGuloso = System.nanoTime(); // Termino da cronometragem

        System.out.println("==========================================");
        for (Caminhao caminhao : caminhoes) {
            System.out.println("Caminhão: " + caminhao.getId());
            System.out.println("Quantidade Rotas: " + caminhao.getQtdeRotas());
            System.out.println("Total Km: " + caminhao.getTotalKm());
            caminhao.getRotas();
            System.out.println("==========================================");
        }

        /**
         * Teste do Algoritmo Guloso(Total em Km das rotas, Média das rotas dos
         * caminhões, maior rota, menor rota, diferença entre elas e tempo que o
         * algoritmo levou para executar)
         */
        Integer soma = somaRotasCaminhoes(caminhoes);
        Integer max = maxSomaRotasCaminhoes(caminhoes);
        Integer min = minSomaRotasCaminhoes(caminhoes);
        double media = soma / numeroCaminhoes;
        int minMaxDiferenca = max - min;
        double tempo = (double) ((terminoGuloso - inicioGuloso) / 1_000_000);

        System.out.println("Total em Km das rotas: " + soma);
        System.out.println("Média em Km das rotas por caminhão: " + media);
        System.out.println("Maior distância de rota: " + max);
        System.out.println("Menor distância de rota: " + min);
        System.out.println("Diferença em Km entre a maior e menor rota: " + minMaxDiferenca);
        System.out.println("Tempo de Execução(milisegundos): " + tempo + "ms");
    }

    private static Collection<Caminhao> algoritmoGuloso(Collection<Rota> rotas,
            int numeroCaminhoes) {

        // Copia a Collection em uma lista para poder ordena-la de forma descrescente
        List<Rota> numeroCaminhoesCopia = new ArrayList<>(rotas);
        numeroCaminhoesCopia.sort(Comparator.comparing(Rota::getDistancia, Comparator.reverseOrder()));

        Queue<Rota> caminhoesQueue = new ArrayDeque<>(numeroCaminhoesCopia); // Cria uma Queue com os dados ordenados

        /**
         * Cria uma PriorityQueue com os caminhoes para garantir que o menor esteja
         * sempre no começo. Utiliza o comparador para ordenar.
         */
        PriorityQueue<Caminhao> caminhoesPriorityQueue = new PriorityQueue<>(numeroCaminhoes,
                new CaminhaoComparator());
        for (int i = 0; i < numeroCaminhoes; i++) {
            caminhoesPriorityQueue.add(new Caminhao(i));
        }

        /**
         * Algoritmo guloso(principal)
         * 
         * Itera sobre a Queue de caminhões e através da PriorityQueue retorna o
         * caminhao com a menor soma de rotas. Após isso, a rota é adicionada ao
         * caminhão e sua soma é aumentada. Para finalizar, o caminhão é adicionado
         * novamente na PriorityQueue para que ela se reorganize e coloque o menor
         * elemento no topo novamente.
         */
        while (!caminhoesQueue.isEmpty()) {
            Rota rota = caminhoesQueue.poll();
            Caminhao menorSomaCaminhao = caminhoesPriorityQueue.poll();
            menorSomaCaminhao.adicionarRota(rota);
            menorSomaCaminhao.adicionaSoma(rota.getDistancia());
            caminhoesPriorityQueue.add(menorSomaCaminhao);
        }
        return caminhoesPriorityQueue;
    }

    /**
     * 
     * @param caminhoes
     * @return Maior distância de soma de rotas que um caminhão irá percorrer
     */
    private static Integer maxSomaRotasCaminhoes(Collection<Caminhao> caminhoes) {
        return caminhoes.stream().map(Caminhao::getSoma).max(Integer::compareTo).get();
    }

    /**
     * 
     * @param caminhoes
     * @return Menor distância de soma de rotas que um caminhão irá percorrer
     */
    private static Integer minSomaRotasCaminhoes(Collection<Caminhao> caminhoes) {
        return caminhoes.stream().map(Caminhao::getSoma).min(Integer::compareTo).get();
    }

    /**
     * 
     * @param caminhoes
     * @return A soma em Km de todas as rotas dos caminhões
     */
    private static Integer somaRotasCaminhoes(Collection<Caminhao> caminhoes) {
        return caminhoes.stream().map(Caminhao::getSoma).reduce(0, Integer::sum);
    }
}