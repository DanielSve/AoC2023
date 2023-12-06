import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class AoC5 {

    public static List<List<Long>> map = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("resources/five")).filter(l -> !l.contains("map")).toList();
        List<Long> seeds = Arrays.stream(lines.get(0).split(":")[1].trim().split(" ")).map(Long::parseLong).toList();
        lines = lines.subList(1, lines.size());
        lines.forEach(l -> map.add(l.isEmpty() ? new ArrayList<>() : Arrays.stream(l.split(" ")).map(Long::parseLong).toList()));

        System.out.println("Part one " + seeds.stream().mapToLong(AoC5::getLocation).min().getAsLong());

        Long lowest = 999999999999L;
        for (int i = 0; i < seeds.size(); i = i + 2) {
            Long range = seeds.get(i + 1);
            for (long j = 0; j < range; j++) {
                Long seed = seeds.get(i) + j;
                long location = getLocation(seed);
                if (location < lowest) lowest = location;
            }
        }
        System.out.println("Part two: " + lowest);
    }

    private static Long getLocation(Long seed) {
        long location = seed;
        boolean skip = true;
        for (List<Long> l: map) {
            if (l.isEmpty())  {
                skip = false;
                continue;
            }
            if (skip) continue;
            if (location >= l.get(1) && location <= l.get(1) + l.get(2) -1) {
                location = location - l.get(1) + l.get(0);
                skip = true;
            }
        }
        return location;
    }
}
