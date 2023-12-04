import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

public class AoC4 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("resources/four")).toList();
        List<Integer> sums = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < lines.size() ; i++) {
            String first = lines.get(i).split("\\|")[0].split(":")[1];
            String second = lines.get(i).split("\\|")[1];
            List<Integer> wins = Arrays.stream(first.trim().replaceAll("  ", " ").split(" "))
                    .map(Integer::parseInt).toList();
            List<Integer> matches = Arrays.stream(second.trim().replaceAll("  ", " ").split(" "))
                    .map(Integer::parseInt).filter(wins::contains).toList();

            // Part one
            List<Integer> ints = IntStream.range(0, matches.size()).map(j -> (int )Math.pow(2, j)).boxed().toList();
            sums.add(ints.isEmpty()? 0 : ints.get(ints.size()-1));

            // Part two
            String key = lines.get(i).split("\\|")[0].split(":")[0];
            map.put(key, map.containsKey(key) ? map.get(key) + 1 : 1);
            for (int c = 0; c < map.get(key); c++) {
                for (int j = 0; j < matches.size() ; j++) {
                    String currentCard = lines.get(i + j + 1).split(":")[0];
                    map.put(currentCard, map.containsKey(currentCard) ? map.get(currentCard) + 1 : 1);
                }
            }
        }
        System.out.println("Part one " + sums.stream().mapToInt(s -> s).sum());
        System.out.println("Part two " + map.values().stream().mapToInt(s -> s).sum());
    }
}
