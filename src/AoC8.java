import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class AoC8 {
    public static List<String> startingPoints;
    public  static Map<String, LeftRight> map = new HashMap<>();
    public static char[] instructions;
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("resources/eight")).filter(l -> !l.contains("map")).toList();
        instructions = lines.get(0).toCharArray();

        for (String line: lines.subList(2, lines.size())) {
            String right = line.split(" = ")[1].replaceAll("\\(", "").replaceAll("\\)", "").trim();
            map.put(line.split(" = ")[0].trim(), new LeftRight(right.split(",")[0].trim(), right.split(",")[1].trim()));
        }
        System.out.println("Part one: " + getInterval("AAA", 1));

        startingPoints = map.keySet().stream().filter(k -> k.endsWith("A")).toList();
        List<Long> intervals = startingPoints.stream().map(s -> getInterval(s, 2)).toList();
        System.out.println("Part two: " + intervals.stream().map(BigInteger::valueOf).reduce(
                BigInteger.ONE, (a, e) -> a.multiply(e).divide(a.gcd(e))));
    }

    public static long getInterval(String startingPoint, int part) {
        String current = startingPoint;
        long steps = 0;
        for (int j = 0; j < instructions.length; j++) {
            current = instructions[j] == 'L' ? map.get(current).l : map.get(current).r;
            steps++;
            if (part == 1 && current.equals("ZZZ") || part == 2 && current.endsWith("Z"))  {
                break;
            }
            j = j == instructions.length - 1 ? -1 : j;
        }
        return steps;
    }
    public static record LeftRight(String l, String r) { }
}
