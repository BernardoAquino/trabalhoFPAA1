package backtracking;


import java.util.ArrayList;
import java.util.Stack;

public class BacktrackingRecursivo {

    //private static int[] tamanhosRotas = {51,52,51,89,72,62,74,66,75,71,71,56,83,69,80,87,70,54,84,80,89,64,59,73,66};
    private static int[] tamanhosRotas = {1,2,3,4,5};
    
    private static int qtd_caminhao = 3;
    private static Stack<Caminhao[]> solucoes = new Stack<Caminhao[]>();
    private static Caminhao[] melhorSolucao = new Caminhao[qtd_caminhao];

   public static Double desvioPadrao(Caminhao[] caminhoes) {
        Double media = 0.0;
        for (Caminhao c : caminhoes) {
            media += c.getTotalKm();
        }
        media = media / caminhoes.length;

        Double desvioMedio = 0.0;
        for (Caminhao c : caminhoes) {
            desvioMedio += Math.abs(c.getTotalKm() - media);
        }
        //desvioMedio = desvioMedio / caminhoes.length;
        return desvioMedio;
    }
/** 
 *  Chama desvio padrao mas retorna a diferenca entre os extremos (para teste)
    public static Double desvioPadrao(Caminhao[] caminhoes) {
        // min totalKm()
        int minKm = caminhoes[0].getTotalKm();
        Caminhao minCaminhao = caminhoes[0];
        
        for (Caminhao c : caminhoes) {
            if (c.getTotalKm() < minKm) {
                minKm = c.getTotalKm();
                minCaminhao = c;
            }
        }
        
        // max totalKm()
        int maxKm = caminhoes[0].getTotalKm();
        Caminhao maxCaminhao = caminhoes[0];
        
        for (Caminhao c : caminhoes) {
            if (c.getTotalKm() > maxKm) {
                maxKm = c.getTotalKm();
                maxCaminhao = c;
            }
        }
        
        return Double.valueOf(maxKm - minKm);
    }
    **/


    public static ArrayList<Caminhao[]> podaPiorSolucao(ArrayList<Caminhao[]> solucoesAPodar) {
        if (solucoesAPodar.size() < 1) {
            return solucoesAPodar;
        }
        ArrayList<Caminhao[]> solucoesList = new ArrayList<Caminhao[]>();
        for (Caminhao[] c : solucoesAPodar) {
            solucoesList.add(c);
        }
        Double max = Double.MIN_VALUE;
        int maxIndex = -1;

        for (int i = solucoesList.size() - 3; i < solucoesList.size(); i++) {
            Double media = desvioPadrao(solucoesList.get(i));
            if (media > max) {
                max = media;
                maxIndex = i;
            }
        }
        solucoesList.remove(maxIndex);
        return solucoesList;
    }

    public static ArrayList<Caminhao[]> geraSolucoes(Caminhao[] solucaoAtual, Rota rota) throws CloneNotSupportedException{

        ArrayList<Caminhao[]> solucoes = new ArrayList<Caminhao[]>();

        for (int i = 0; i < solucaoAtual.length; i++) {
            Caminhao[] s = new Caminhao[solucaoAtual.length];
            for (int j = 0; j < solucaoAtual.length; j++) {
                s[j] = solucaoAtual[j].clone();
            }
            s[i].adicionarRota(rota);

            solucoes.add(s);
        }

        return podaPiorSolucao(solucoes);

    }

    private static Caminhao[] verificarMelhorSolucao(Caminhao[] caminhoes) {
        if (melhorSolucao[0] == null) {
            melhorSolucao = caminhoes;
        } else {
            Double mediaAtual = desvioPadrao(caminhoes);
            Double mediaMelhor = desvioPadrao(melhorSolucao);
            if (mediaAtual < mediaMelhor) {
                melhorSolucao = caminhoes;
                System.out.println("Melhor solução: " + melhorSolucao[0].getTotalKm() + ", " + melhorSolucao[1].getTotalKm() + ", " + melhorSolucao[2].getTotalKm());
            }
        }
        return melhorSolucao;   
    }

    public static void empilhaSolucoes(ArrayList<Caminhao[]> solucoes) {
        for (Caminhao[] c : solucoes) {
            BacktrackingRecursivo.solucoes.push(c);
        }
    }

    public static Caminhao[] iniciarCaminhoes(ArrayList<Caminhao> caminhoes, ArrayList<Rota> rotas, Caminhao[] solucao){
        for (int i = 0; i < solucao.length; i++) {
            Caminhao c = new Caminhao(i);
            c.adicionarRota(rotas.get(0));
            solucao[i] = c;
            rotas.remove(0);
        }
        return solucao;

    }

    public static Caminhao[] backtracking(ArrayList<Caminhao> caminhoes, ArrayList<Rota> rotas, Caminhao[] solucao) throws CloneNotSupportedException{
        if (rotas.size() == 0) {
            return verificarMelhorSolucao(solucao);
        }
        ArrayList<Caminhao[]> solucoes = geraSolucoes(solucao, rotas.get(0));
        empilhaSolucoes(solucoes);
        rotas.remove(0);
        while (!solucoes.isEmpty()) {
            Caminhao[] s = solucoes.get(0);
            solucoes.remove(0);
            backtracking(caminhoes, rotas, s);
        }
        return melhorSolucao;


    }


    private static int totalDeRotasUtilizadas(Caminhao[] solucao) {
        int qtdRotasUtilizadas =0;
        for (Caminhao c : solucao) {
            qtdRotasUtilizadas += c.getRotas().size();
        }
        return qtdRotasUtilizadas;
    }

    public static ArrayList<Rota> criaRotas(int[] tamanhosRotas) {
        ArrayList<Rota> rotas = new ArrayList<Rota>();
        for (int i = 0; i < tamanhosRotas.length; i++) {
            rotas.add(new Rota(i, tamanhosRotas[i]));
        }
        return rotas;
    }

    public static ArrayList<Caminhao> criaCaminhoes(int qtd_caminhao) {
        ArrayList<Caminhao> caminhoes = new ArrayList<Caminhao>();
        for (int i = 0; i < qtd_caminhao; i++) {
            caminhoes.add(new Caminhao(i));
        }
        return caminhoes;
    }
    public static void main(String[] args) throws CloneNotSupportedException {
        ArrayList<Rota> rotas = criaRotas(tamanhosRotas);
        ArrayList<Caminhao> caminhoes = criaCaminhoes(qtd_caminhao);
        Caminhao[] solucao = new Caminhao[qtd_caminhao];
        solucao = iniciarCaminhoes(caminhoes, rotas, solucao);
        System.out.println("Solução inicial: " + solucao[0].getTotalKm() + ", " + solucao[1].getTotalKm() + ", " + solucao[2].getTotalKm());

        melhorSolucao = backtracking(caminhoes, rotas, solucao);


        System.out.println("Solução final: " + melhorSolucao[0].getTotalKm() + ", " + melhorSolucao[1].getTotalKm() + ", " + melhorSolucao[2].getTotalKm());
        System.out.println("Desvio padrão: " + desvioPadrao(melhorSolucao));
        System.out.println(rotas.size());
    }

}
