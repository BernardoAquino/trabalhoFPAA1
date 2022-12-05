import java.util.ArrayList;

public class Backtracking1a {

    private static int[] tamanhosRotas = {51,52,51,89,72,62,74,66,75,71,71,56,83,69,80,87,70,54,84,80,89,64,59,73,66};
    private static int qtdeCaminhoes = 3;


    public static Integer diffSomaParticoes(ArrayList<Caminhao> caminhoes){
        ArrayList<Integer> somas = new ArrayList<Integer>();
        for (Caminhao caminhao : caminhoes) {
            somas.add(caminhao.getTotalKm());
        }
        Integer menor = somas.stream().mapToInt(Integer::intValue).min().getAsInt();
        Integer maior = somas.stream().mapToInt(Integer::intValue).max().getAsInt();
        return Math.abs(maior - menor);
    }

    public static ArrayList<Caminhao> comparaOpcoesPelaDiff(ArrayList<Caminhao> melhorOpcaoAtual, ArrayList<Caminhao> opcaoAtual){
        if (melhorOpcaoAtual == null || diffSomaParticoes(opcaoAtual) < diffSomaParticoes(melhorOpcaoAtual)){
           melhorOpcaoAtual = opcaoAtual;
       }
       return melhorOpcaoAtual;
    }

    public static void backtracking(Rota[] rotas, int qtdeCaminhoes) {
        ArrayList<Caminhao> caminhoes  = new ArrayList<Caminhao>(qtdeCaminhoes);
        // Cria lista para cada partição(caminhão)
        for (int i = 0; i < qtdeCaminhoes; i++) {
            caminhoes.add(new Caminhao(i));
        }
        for (Caminhao caminhao : caminhoes) {
            System.out.println("Caminhão " + caminhao.getId());
        } 

        // Para cada rota
        for (Rota rota : rotas) {
            // Verificar o melhor caminhão para adicionar a rota
            ArrayList<Caminhao> melhorOpcaoAtual = null;
            for (int i = 0; i < qtdeCaminhoes; i++) {
                // Cria uma cópia da lista de partição
                ArrayList<Caminhao> opcaoAtual = new ArrayList<Caminhao>();
                for (Caminhao c : caminhoes) {
                    opcaoAtual.add(new Caminhao(c.getId()));
                    for (Rota r : c.getRotas()) {
                        opcaoAtual.get(c.getId()).adicionarRota(r);
                    }
                }
                // Adiciona a rota na partição
                opcaoAtual.get(i).adicionarRota(rota);
                melhorOpcaoAtual = comparaOpcoesPelaDiff(melhorOpcaoAtual, opcaoAtual);

            }
            caminhoes = melhorOpcaoAtual;
        }
        
        // Imprime resultado
        for (Caminhao caminhao : caminhoes) {
            System.out.println("Caminhão " + caminhao.getId() + " - Total de Km: " + caminhao.getTotalKm());
            caminhao.imprimeRotas();
            System.out.println("-------------------------------------------------");
        }
        // Imprime a diferença entre a maior e menor soma
        System.out.println("Diferença entre a maior e menor soma: " + diffSomaParticoes(caminhoes));



    }

    public static void main(String[] args) {
        Rota[] rotas = new Rota[tamanhosRotas.length];
        for (int i = 0; i < tamanhosRotas.length; i++) {
            Rota rota = new Rota(i, tamanhosRotas[i]);
            rotas[i] = rota;
        }

        backtracking(rotas, qtdeCaminhoes);


    }
}
