package programacaoDinamica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppProgDinam {
    static int INF = Integer.MAX_VALUE;

    /**
     * 
     * @param values
     * @param k
     * @return
     */
    static int[][] partition(int[] values, int k) {
        int n = values.length;
        int[][] M = new int[n][k];
        int[][] D = new int[n - 1][k - 1];

        // Inicialização
        M[0][0] = values[0];
        // Calcula soma cumulativa
        for (int i = 1; i < n; i++) {
            M[i][0] = values[i] + M[i - 1][0];
        }
        // Inicializa condição de fronteira
        for (int i = 1; i < k; i++) {
            M[0][i] = values[0];
        }

        // Preenche o restante
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < k; j++) {
                int current_min = -1;
                int minx = INF;
                for (int x = 0; x < i; x++) {
                    int s = Math.max(M[x][j - 1], M[i][0] - M[x][0]);
                    if (current_min < 0 || s < current_min) {
                        current_min = s;
                        minx = x;
                    }
                }
                M[i][j] = current_min;
                D[i - 1][j - 1] = minx;
            }
        }
        return D;
    }

    static List<List<Integer>> reconstructPartition(int[] values, int[][] D, int k) {
        List<List<Integer>> result = new ArrayList();
        int n = D.length;
        k = k - 2;
        while (k >= 0) {
            List<Integer> inner = new ArrayList();
            for (int i = D[n - 1][k] + 1; i < n + 1; i++) {
                inner.add(values[i]);
            }
            result.add(inner);
            n = D[n - 1][k];
            k--;
        }

        List<Integer> inner = new ArrayList();
        for (int i = 0; i < n + 1; i++) {
            inner.add(values[i]);
        }
        result.add(inner);
        Collections.reverse(result);
        return result;
    }

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
        List<Integer> rotasPreencher = new ArrayList<>();
        long inicioGuloso = System.nanoTime();

        for (List<Integer> lista : dados) {
            if (lista.size() == 1)
                numeroCaminhoes = lista.get(0);
            else
                rotasPreencher.add(lista.get(1));
        }
        System.out.println("Número de caminhões: " + numeroCaminhoes);
        System.out.println("Rotas Utilizadas: " + rotasPreencher);
        System.out.println("----------------------------------------------------------------------------------");

        int[] rotas = rotasPreencher.stream().mapToInt(i -> i).toArray();
        int[][] tabela = partition(rotas, numeroCaminhoes);

        // Imprimir tabela
        for (int i = 0; i < tabela.length; i++) {
            for (int j = 0; j < tabela[i].length; j++) {
                String celula = String.valueOf(tabela[i][j]);
                System.out.print(String.format("%5s", celula));

            }
            System.out.println();
        }
        System.out.println(reconstructPartition(rotas, tabela, numeroCaminhoes)); // Listar valor máximo e mínimo
        // também(Armazenar isso em uma variável)

        // Valores máximos e mínimos

        // Cronometragem
        long terminoGuloso = System.nanoTime(); // Termino da cronometragem
        double tempo = (double) ((terminoGuloso - inicioGuloso) / 1_000_000);
        System.out.println("Tempo de Execução(milisegundos): " + tempo + "ms");
    }
}
