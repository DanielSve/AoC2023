import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class AoC5 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("resources/five")).filter(l -> !l.contains("map")).toList();
        List<Long> seeds = Arrays.stream(lines.get(0).split(":")[1].trim().split(" ")).map(Long::parseLong).toList();
        lines = lines.subList(1, lines.size());

//        System.out.println("Part one " + getLocations(seeds, lines).stream().sorted().toList().get(0));

        Long lowestTotal = 999999999999L;
        for (int i = 0; i < seeds.size(); i = i + 2) {
            Long range = seeds.get(i + 1);
            for (long j = 0; j < range; j++) {
                System.out.println("Progress: " + ((double) j) / ((double) range));
                Long lowest = getLowest(List.of(seeds.get(i) + j), lines);
                if (lowest < lowestTotal) lowestTotal = lowest;
            }
        }
        System.out.println("Part two: " + lowestTotal);
    }

    public static Long getLowest(List<Long> seeds, List<String> lines) {
        Boolean skip = true;
        Long lowest = 9999999999999L;
        long dest = 0;
        for (Long seed: seeds) {
            dest = seed;
            for (String line: lines) {
                if (line.isEmpty())  {
                    skip = false;
                    continue;
                }
                if (skip) continue;
                List<Long> map = Arrays.stream(line.split(" ")).map(Long::parseLong).toList();
                if (dest >= map.get(1) && dest <= map.get(1) + map.get(2) -1) {
                    dest = dest - map.get(1) + map.get(0);
                    skip = true;
                }
            }
            if ( dest < lowest) lowest = dest;
        }
        return lowest;
    }
}
