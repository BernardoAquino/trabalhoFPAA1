package divisaoEConquista;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AppDivisaoConquista {
    public static int inicio;
    public static int fim;
    public static int soma;
    public static int[] periodo;

    public static int[] maxCrossing(int[] nums, int low, int mid, int high) {
        // Inclui os elementos à esquerda do meio
        int sum = 0;
        int leftSum = Integer.MIN_VALUE;
        int maxLeftIndex = -1;

        for (int i = mid; i >= low; i--) {
            sum += nums[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeftIndex = i;
            }
        }

        // Inclui os elementos à direita do meio
        sum = 0;
        int rightSum = Integer.MIN_VALUE;
        int maxRightIndex = -1;

        for (int i = mid + 1; i <= high; i++) {
            sum += nums[i];
            if (sum > rightSum) {
                rightSum = sum;
                maxRightIndex = i;
            }
        }

        /**
         * Retorna a soma dos elementos da esquerda e da direita do meio
         */
        return new int[] { maxLeftIndex, maxRightIndex, leftSum + rightSum };
    }

    public static int[] maxSubArray(int[] nums, int low, int high) {
        // Caso base(Apenas 1 elemento)
        if (low == high)
            return new int[] { low, high, nums[low] };

        // Acha o meio
        int mid = (low + high) / 2;

        /**
         * Após isso, retorna-se o subArray com valor máximo da seguinte árvore:
         * 
         * • subArray com soma máxima na metade da esquerda
         * • subArray com soma máxima na metade da direita
         * • subArray com soma máxima de modo que o subArray ultrapasse/cruze o ponto
         * médio
         * 
         */
        int[] leftResults = maxSubArray(nums, low, mid);
        int[] rightResults = maxSubArray(nums, mid + 1, high);
        int[] crossResults = maxCrossing(nums, low, mid, high);

        if (leftResults[2] >= rightResults[2] && leftResults[2] >= crossResults[2]) {
            periodo = leftResults;
            soma = leftResults[2];
            inicio = leftResults[0] + 1;
            fim = leftResults[1] + 1;
            return leftResults;
        } else if (rightResults[2] >= leftResults[2] && rightResults[2] >= crossResults[2]) {
            periodo = rightResults;
            soma = rightResults[2];
            inicio = rightResults[0] + 1;
            fim = rightResults[1] + 1;
            return rightResults;
        } else {
            periodo = crossResults;
            soma = crossResults[2];
            inicio = crossResults[0] + 1;
            fim = crossResults[1] + 1;
            return crossResults;
        }
    }

    public static Integer calcularVariacao(Integer tempAnterior, Integer tempAtual) {
        if (tempAnterior == null) {
            return 0;
        }

        return tempAtual - tempAnterior;
    }

    public static ArrayList<int[]> temperaturaAbsolutaToVariacao(ArrayList<int[]> tempAnosAtual) {
        ArrayList<int[]> tempAnosVariacao = new ArrayList<>();

        for (int[] tempAno : tempAnosAtual) {
            int[] tempAnoVariacao = new int[tempAno.length];
            Integer tempAnterior = null;
            for (int i = 0; i < tempAno.length; i++) {
                tempAnoVariacao[i] = calcularVariacao(tempAnterior, tempAno[i]);
                tempAnterior = tempAno[i];
            }
            tempAnosVariacao.add(tempAnoVariacao);
        }
        return tempAnosVariacao;
    }

    public static int[] diasCoincidem(int[] result1, int[] result2) {
        ArrayList<Integer> dias1 = new ArrayList<>();
        ArrayList<Integer> dias2 = new ArrayList<>();
        for (int i = result1[0]; i <= result1[1]; i++) {
            dias1.add(i);
        }
        for (int i = result2[0]; i <= result2[1]; i++) {
            dias2.add(i);
        }

        List<Integer> diasIntersecaoList = dias1.stream()
                .distinct()
                .filter(dias2::contains)
                .collect(Collectors.toList());
        int[] diasIntersecaoArray = new int[diasIntersecaoList.size()];

        int i = 0;
        for (Integer dia : diasIntersecaoList) {
            diasIntersecaoArray[i] = dia;
            i++;
        }
        return diasIntersecaoArray;

    }

    public static void verificarCoincidencia(ArrayList<int[]> r) {
        for (int i = 0; i < r.size(); i++) {
            for (int j = 1 + i; j < r.size(); j++) {
                System.out.println("Coincidencias entre os anos " + (i + 1) + " e " + (j + 1) + ": "
                        + Arrays.toString(diasCoincidem(r.get(i), r.get(j))));
            }
        }
    }

    public static int[] runDivisaoConquista(int[] nums) {
        int[] result = new int[3];
        maxSubArray(nums, 0, nums.length - 1);
        System.out.println("\n");
        System.out.println(Arrays.toString(periodo));
        System.out.println("Início[Posição/Dia]: " + inicio);
        result[0] = inicio;
        System.out.println("Fim[Posição/Dia]: " + fim);
        result[1] = fim;
        System.out.println("Soma: " + soma);
        result[2] = inicio;
        return result;
    }

    private static int[] agruparLinhas(ArrayList<int[]> tempAnosVariacao) {
        ArrayList<Integer> tempAnosVariacaoAgrupada = new ArrayList<Integer>();

        for (int[] i : tempAnosVariacao) {
            for (int j : i) {
                tempAnosVariacaoAgrupada.add(j);
            }
        }

        int[] tempAnosVariacaoAgrupadaArray = new int[tempAnosVariacaoAgrupada.size()];

        for (int i = 0; i < tempAnosVariacaoAgrupada.size(); i++) {
            tempAnosVariacaoAgrupadaArray[i] = tempAnosVariacaoAgrupada.get(i);
        }

        return tempAnosVariacaoAgrupadaArray;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        /**
         * lê o arquivo e passa para um array
         */
        Arquivo arquivo = new Arquivo("/home/gustavocn/Desktop/fpaa/trabalhoFPAA1/src/dados/");

        // Alterar fileName
        String fileName = "temperaturas.txt";

        List<List<Integer>> tempAnosList = arquivo.lerArquivo(fileName);

        ArrayList<int[]> tempAnos = new ArrayList<>();

        long startTime = System.currentTimeMillis();


        for (List<Integer> list : tempAnosList) {
            int[] tempAno = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                tempAno[i] = list.get(i);
            }
            tempAnos.add(tempAno);
        }


        ArrayList<int[]> tempAnosVariacao = temperaturaAbsolutaToVariacao(tempAnos);
        // Result Lista -> List< [inicio, fim, soma] , [inicio, fim, soma] , [inicio,
        // fim, soma] , [inicio, fim, soma] > ([]== ano)

        ArrayList<int[]> results = new ArrayList<>();
        /**
         * Armazenar o Início, Fim e Soma de cada um dos anos(linhas)
         */
        System.out.println("--------------------------------------------------------------------------\n");
        System.out.println("\nSeparado por ano: \n");

        for (int[] nums : tempAnosVariacao) {
            int[] result = runDivisaoConquista(nums);
            results.add(result);
        }

        /**
         * Verificar se teve coincidência dos dias
         * 
         * Ex: Período mais quente dia 56 até dia 68;(ANO 1)
         * 
         * Ano2 60 até 69(Verificar se um valor ta dentro do outro). Coincidência 60 até
         * 68
         */

        // Printa coincidências
        verificarCoincidencia(results);

        /**
         * Agrupar as linhas da tabela em uma só e rodar novamente o código
         */
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\nAgrupando os anos: \n");
        int[] tempAnosAgrupado = agruparLinhas(tempAnosVariacao);
        int[] result = runDivisaoConquista(tempAnosAgrupado);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Tempo de execução: " + totalTime + "ms");
    }

}
