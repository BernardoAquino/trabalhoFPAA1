package programacaoDinamica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Arquivo {
    private static final String COMMA_DELIMITER = ";";
    private static final String PATH = "/home/gustavocn/Desktop/fpaa/trabalhoFPAA1/src/dados/"; // Caminho absoluto do
                                           // arquivo(Arruma para
                                           // relativo)

    public List<List<Integer>> lerArquivo(String fileName) throws FileNotFoundException, IOException {
        List<List<Integer>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader(PATH + fileName + ".txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Integer> values = Arrays.stream(line.split(COMMA_DELIMITER)).map(Integer::parseInt)
                        .collect(Collectors.toList());
                records.addAll(Arrays.asList(values));
            }
        }

        return records;
    }
}
