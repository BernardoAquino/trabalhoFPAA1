package divisaoEConquista;

import java.util.Arrays;

public class AppDivisaoConquista {
    public static int inicio;
    public static int fim;
    public static int soma;
    public static int[] periodo;

    public static int[] maxCrossing(int[] nums, int low, int mid, int high) {
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
        return new int[] { maxLeftIndex, maxRightIndex, leftSum + rightSum };
    }

    public static int[] maxSubArray(int[] nums, int low, int high) {
        if (low == high)
            return new int[] { low, high, nums[low] };

        int mid = (low + high) / 2;

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

    public static void main(String[] args) {
        int[] nums = { 13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7 };
        /**
         * Passar os dados de temperatura para um array
         */

        /**
         * Passar os dados de temperatura para a diferença(Faz por último)**
         */

        /**
         * Armazenar o Início, Fim e Soma de cada um dos anos(linhas)
         */

        /**
         * Verificar se teve coincidência dos dias
         * 
         * Ex: Período mais quente dia 56 até dia 68;(ANO 1)
         * 
         * Ano2 60 até 69(Verificar se um valor ta dentro do outro). Coincidência 60 até
         * 68
         */

        /**
         * Agrupar as linhas da tabela em uma só e rodar novamente o código
         */

        maxSubArray(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(periodo));
        System.out.println("Início[Posição/Dia]: " + inicio);
        System.out.println("Fim[Posição/Dia]: " + fim);
        System.out.println("Soma: " + soma);
    }
}
