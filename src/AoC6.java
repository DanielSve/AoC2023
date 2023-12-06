import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AoC6 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("resources/six")).filter(l -> !l.contains("map")).toList();
        List<Long> times = Arrays.stream(lines.get(0).split(":")[1].trim().split("\\s+")).map(Long::parseLong).toList();
        List<Long> records = Arrays.stream(lines.get(1).split(":")[1].trim().split("\\s+")).map(Long::parseLong).toList();

        System.out.println("Part one " + getCountsMultiplied(times, records));

        long time = Long.parseLong(lines.get(0).split(":")[1].replaceAll("\\s+", ""));
        long record = Long.parseLong(lines.get(1).split(":")[1].replaceAll("\\s+", ""));

        System.out.println("Part two " + getCountsMultiplied(List.of(time), List.of(record)));
    }

    public static long getCountsMultiplied(List<Long> times, List<Long> records) {
        int countMultiplied = 1;
        for (int i = 0; i < times.size(); i++) {
            long time = times.get(i);
            long count = 0;
            for (int j = 0; j <time ; j++) {
                long distance =  (time - j) * j;
                if (distance > records.get(i)) {
                    count ++;
                }
                if (count > 0 && distance <records.get(i)) break;
            }
            countMultiplied *= count;
        }
        return countMultiplied;
    }
}
