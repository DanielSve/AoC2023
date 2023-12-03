import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class AoC2 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("resources/two")).toList();
        int partOne = lines.stream().mapToInt(l -> Arrays.stream(l.split(":")[1].split(";"))
                .noneMatch(AoC2::exceeds) ? Integer.parseInt(l.split(":")[0].replace("Game ", "")) : 0).sum();
        System.out.println("Part one: " + partOne);
        System.out.println("Part two: " + lines.stream().mapToInt(l -> getPower(Arrays.stream(l.split(":")[1].split(";")).toList())).sum());
    }
    public static boolean exceeds(String cubeSet) {
        return Arrays.stream(cubeSet.split(",")).anyMatch(numCol -> {
            int num = Integer.parseInt(numCol.trim().split(" ")[0]);
            String color = numCol.trim().split(" ")[1];
            return (num > 12 && color.equals("red") || num > 13 && color.equals("green") || num > 14 && color.equals("blue"));
        });
    }
    public static int getPower(List<String> cubeSets) {
        Map<String, Integer> map = new HashMap<>();
        for (String cubeSet: cubeSets) {
            Arrays.stream(cubeSet.trim().split(",")).forEach(s -> {
                String[] numCol = s.trim().split(" ");
                map.put(numCol[1], !map.containsKey(numCol[1]) || map.get(numCol[1]) < Integer.parseInt(numCol[0])
                        ? Integer.parseInt(numCol[0]) : map.get(numCol[1]));
            });
        }
        return map.values().stream().reduce(1, (a, e) -> a * e);
    }
}