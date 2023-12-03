import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class AoC1 {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> lines = new BufferedReader(new FileReader("resources/one")).lines().toList();
        int partOne = lines.stream().mapToInt(s -> getNum(s.replaceAll("[a-zA-Z]", ""))).sum();
        System.out.println(partOne);

        List<String> num = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
        int partTwo = lines.stream().mapToInt(s -> {
            String s2 = "";
            for (int i = 0; i < s.length(); i++) {
                for (int j = 0; j < num.size(); j++) {
                    if (Character.isDigit(s.charAt(i))) {
                        s2 = s2.isBlank() ? s.charAt(i) + "" : s2.charAt(0) + s.substring(i, i+1);
                    } else if (s.length() - i + 1 > num.get(j).length() && s.startsWith(num.get(j), i)) {
                        s2 = s2.isBlank() ? j + 1 + "" : s2.substring(0, 1) + (j + 1);
                    }
                }
            } return getNum(s2);
        }).sum();
        System.out.println(partTwo);
    }
    public static int getNum(String s) { return Integer.parseInt(s.substring(0, 1) + s.charAt(s.length() > 1 ? s.length() - 1 : 0)); }
}
