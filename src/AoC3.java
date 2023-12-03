import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class AoC3 {
    public static char[][] grid;
    public static void main(String[] args) throws IOException {
        grid = Files.lines(Path.of("resources/three")).map(String::toCharArray).toArray(char[][]::new);
        List<Integer> ints = new ArrayList<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length ; j++) {
                if(isNum(grid[i][j])) {
                    String num = "";
                    boolean hasAdjacent = false;
                    while (j <= grid.length -1) {
                         num += grid[i][j];
                         hasAdjacent = hasAdjacent(i, j) || hasAdjacent;
                         if((j < grid.length -1 && !isNum(grid[i][j+1]))) break;
                         ++j;
                     }
                    if (hasAdjacent) ints.add(Integer.parseInt(num));
                }
            }
        }
        System.out.println("Part one: " + ints.stream().mapToInt(i -> i).sum());

        int sum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == '*') {
                    sum += getAdjacentMultiplied(i,j);
                }
            }
        }
        System.out.println("Part two: " + sum);
    }

    private static int getAdjacentMultiplied(int i, int j) {
        List<Integer> allNums = new ArrayList<>();
        if (i > 0) {
            allNums.addAll(getAdjacent(i, j, i-1));
        }
        if (i < grid.length -1) {
            allNums.addAll(getAdjacent(i, j, i+1));
        }
        if (j > 0 && isNum(grid[i][j-1])) {
            allNums.add(getNumber(i,j-1));
        }
        if (j < grid.length -1 && isNum(grid[i][j+1])) {
            allNums.add(getNumber(i,j+1));
        }
        return allNums.size() == 2 ? allNums.stream().reduce(1, (a, e) -> a * e) : 0;
    }

    private static List<Integer> getAdjacent(int y, int x, int dir) {
        List<Integer> numbers = new ArrayList<>();
        if (isNum(grid[dir][x-1])) {
            numbers.add(getNumber(dir, x-1));
            if (isNum(grid[dir][x+1]) && !isNum(grid[dir][x])) {
                numbers.add(getNumber(dir, x+1));
            }
        } else if (isNum(grid[dir][x])) {
            numbers.add(getNumber(dir, x));
        } else if (isNum(grid[dir][x+1])) {
            numbers.add(getNumber(dir,x+1));
        }
        return numbers;
    }

    private static int getNumber(int y, int x) {
        int start = x;
        for (int j = x; j >= 0; j--) {
            if (isNum(grid[y][j])) {
                start = j;
            } else break;
        }
        String num ="";
        for (int j = start; j < grid.length ; j++) {
            if (isNum(grid[y][j])) {
                num+=grid[y][j];
            } else break;
        }
        return Integer.parseInt(num);
    }

    public static boolean hasAdjacent(int i, int j) {
        if (i > 0 && (isSymbol(grid[i-1][j]) || (j > 0 && (isSymbol(grid[i-1][j-1]))
                || (j < grid.length -1 && isSymbol(grid[i-1][j+1]))))) {
                return true;
        }
        if (i < grid.length -1 && ((isSymbol(grid[i+1][j]) || (j > 0 && isSymbol(grid[i+1][j-1]))
                || (j < grid.length -1 && isSymbol(grid[i+1][j+1]))))) {
                return true;
        }
        return (j > 0 && (isSymbol(grid[i][j-1]) || (j < grid.length - 1 && (isSymbol(grid[i][j+1])))));
    }
    public static boolean isNum(char c) { return "0123456789".contains(c + ""); }
    public static boolean isSymbol(char c) { return !".0123456789".contains(c + ""); }
}