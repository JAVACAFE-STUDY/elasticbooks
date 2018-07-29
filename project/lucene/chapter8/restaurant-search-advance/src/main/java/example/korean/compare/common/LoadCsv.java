package example.korean.compare.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class LoadCsv {
    public static List<String> loadCsv() {
        Path filePath = Paths.get("src/main/resources/restaurant_Info_explain_20160906.csv");
        try {
            return Files.lines(filePath).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
