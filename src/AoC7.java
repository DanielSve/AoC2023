import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AoC7 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("resources/seven")).filter(l -> !l.contains("map")).toList();
        Map<String, String> handToBid = lines.stream().collect(Collectors.toMap(l -> l.split(" ")[0], l -> l.split(" ")[1]));

        Map<Character, Integer> charRank = new HashMap<>(Map.of('A', 14, 'K', 13, 'Q', 12, 'J', 11, 'T', 10));
        List<String> sorted = handToBid.keySet().stream().sorted(new HandComparator( false, charRank)).toList();
        System.out.println("Part one: " + getSum(sorted, handToBid));

        charRank.put('J', 1);
        sorted = handToBid.keySet().stream().sorted(new HandComparator(true, charRank)).toList();

        System.out.println("Part two: " + getSum(sorted, handToBid));
    }

    public static int score(String s, boolean joker) {
        Map<Character, Integer> map = new HashMap<>();
        s.chars().forEach(c -> map.put((char) c, map.containsKey((char) c) ? map.get((char) c) + 1 : 1));
        List<Integer> values = new ArrayList<>(map.values());
        if (map.containsValue(5)) {
            return 6;
        } else if (map.containsValue(4)) {
            return joker && map.containsKey('J') ? 6 : 5;
        } else if (map.containsValue(3) && map.containsValue(2)) {
            return joker && map.containsKey('J') ? 6 : 4;
        } else if (map.containsValue(3)) {
            return joker && map.containsKey('J') ? 5 : 3;
        }else if (values.stream().filter(v -> v.equals(2)).count() == 2) {
            return joker && map.containsKey('J') ? 3 + map.get('J') : 2;
        } else if (map.containsValue(2)) {
            return joker && map.containsKey('J') ? 3 : 1;
        } else  {
            return joker && map.containsKey('J') ? 1 : 0;
        }
    }

    public static class HandComparator implements Comparator<String> {
        boolean joker;
        Map<Character, Integer> charRank;
        public HandComparator(boolean joker, Map<Character, Integer> charRank) {
           this.joker = joker;
           this.charRank = charRank;
        }
        @Override
        public int compare(String o1, String o2) {
            if (score(o1, joker) == score(o2, joker)) {
                for (int i = 0; i < o1.length() ; i++) {
                    char c1 = o1.charAt(i);
                    char c2 = o2.charAt(i);
                    if (!Character.isDigit(c1)) {
                        if (Character.isDigit(c2)) {
                            return joker && c1 == 'J' ? - 1 : 1;
                        } else if (charRank.get(c1) - charRank.get(c2) !=0) {
                            return Integer.compare(charRank.get(c1), charRank.get(c2));
                        }
                    } else if (!Character.isDigit(c2)) {
                        return joker && o2.charAt(i) == 'J' ? 1 : -1;
                    } else if (Integer.parseInt(c1 + "") - Integer.parseInt(c2 + "") != 0) {
                        return Integer.compare(Integer.parseInt(c1 + ""), Integer.parseInt(c2 +""));
                    }
                }
            }
            return Integer.compare(score(o1, joker), score(o2, joker));
        }
    }
    public static int getSum(List<String> sorted, Map<String, String> htb) { return IntStream.range(0, sorted.size())
            .map(i -> (i + 1) * Integer.parseInt(htb.get(sorted.get(i)))).sum(); }
}
