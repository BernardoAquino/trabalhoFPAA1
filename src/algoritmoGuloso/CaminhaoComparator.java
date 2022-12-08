package algoritmoGuloso;

import java.util.Comparator;

/**
 * Ordena os caminhões em ordem decrescente pela sua soma de rotas.
 */
public class CaminhaoComparator implements Comparator<Caminhao> {
    @Override
    public int compare(Caminhao caminhaoA, Caminhao caminhaoB) {
        return Integer.compare(caminhaoA.getSoma(), caminhaoB.getSoma());
    }
}
