import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Aoc9 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("resources/nine")).filter(l -> !l.contains("map")).toList();
        long partOne = 0;
        long partTwo = 0;
        for (String line : lines) {
            List<Long> ints = new ArrayList<>(Arrays.stream(line.split(" ")).map(Long::parseLong).toList());
            partOne += getValue(new ArrayList<>(List.of(ints)), 1);
            partTwo += getValue(new ArrayList<>(List.of(ints)), 2);
        }
        System.out.println("Part one " + partOne);
        System.out.println("Part two " + partTwo);
    }

    public static long getValue(List<List<Long>> list, int part) {
        List<Long> newList = new ArrayList<>();
        List<Long> current = list.size() == 1 ? list.get(0) : list.get(list.size() - 1);
        for (int i = 1; i < current.size(); i++) {
            newList.add(current.get(i) - current.get(i - 1));
        }
        list.add(newList);
        if (newList.stream().anyMatch(l -> l != 0L)) {
            return getValue(list, part);
        } else if (part == 1) {
            return list.stream().map(l -> l.get(l.size() - 1)).mapToLong(i -> i).sum();
        } else {
            long sum = 0;
            for (int i = list.size() - 2; i >= 0; i--) {
                sum = list.get(i).get(0) - sum;
            }
            return sum;
        }
    }
}

