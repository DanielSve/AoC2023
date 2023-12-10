import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class AoC10 {
    public static char[][] grid;
    public static List<List<Integer>> allPositions = List.of(List.of(-1, 0), List.of(0, -1), List.of(0, 1), List.of(1, 0));
    public static void main(String[] args) throws IOException {
        grid = Files.lines(Path.of("resources/ten")).map(String::toCharArray).toArray(char[][]::new);
        System.out.println("Part one "+ loop(1));

        loop(2);
        for (int i = 0; i < grid.length ; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if ("*".contains(grid[i][j] + "")) {
                    fill(i,j);
                }
            }
        }
        long stars = Arrays.stream(grid).flatMapToInt(row -> new String(row).chars()).filter(c -> c == '*').count();
        System.out.println("Part two " + stars);
    }

    public static int loop(int round) {
        for (int i = 0; i <grid.length ; i++) {
            for (int j = 0; j <grid[0].length ; j++) {
                if (grid[i][j] == 'S')  {
                    return getSteps(i, j+1, i, j, round);
                }
            }
        }
        return 0;
    }

    public static void fill(int i, int j) {
        if (i > 0 && ("JL|-7F.".contains(grid[i-1][j] + ""))) {
            grid[i-1][j]= '*';
            fill(i-1,j);
        }
        if (i < grid.length-1 && "JL|-7F.".contains(grid[i+1][j] + "")) {
            grid[i+1][j]= '*';
            fill(i+1,j);
        }
        if (j > 0 && ("JL|-7F.".contains(grid[i][j-1]+ ""))) {
            grid[i][j-1]= '*';
            fill(i,j-1);
        }
        if (j < grid[0].length -1 && ("JL|-7F.".contains(grid[i][j+1]+ ""))) {
            grid[i][j+1]= '*';
            fill(i,j+1);
        }
    }

    public static int getSteps(int i, int j, int prevI, int prevJ, int round) {
        int steps = 1;
        String direction = "leftup";
        checkDirection(prevI, prevJ, direction, round);
        direction = "rightup";
        char cur = grid[i][j];
        while (cur != 'S') {
            for (List<Integer> p: allPositions) {
                if (!(i + p.get(0) == prevI && j + p.get(1) == prevJ) && !getDirection(direction, i + p.get(0), j + p.get(1), i, j).isEmpty()) {
                    direction = getDirection(direction, i + p.get(0), j + p.get(1), i, j);
                    char newChar = switch(grid[i][j]) {
                        case 'F', 'J', 'L' -> (grid[i][j] + "").toLowerCase().charAt(0);
                        case '-' -> '_';
                        case '|' -> 'i';
                        case '7' -> '>';
                        default -> grid[i][j];
                    };
                    grid[i][j] = newChar;
                    prevI = i;
                    prevJ = j;
                    i = i + p.get(0);
                    j = j + p.get(1);
                    checkDirection(i, j, direction, round);
                    cur = grid[i][j];
                    steps++;
                    break;
                }
            }
        }
        return steps / 2;
    }

    private static void checkDirection(int i, int j, String direction, int round) {
        if(i > 0 && direction.equals("rightup") || direction.equals("leftup")) {
            if (grid[i - 1][j] == '.' || (round == 2 && ".JL7-|F".contains(grid[i - 1][j] + ""))) {
                grid[i - 1][j] = '*';
            }
            if (direction.equals("rightup")) {
                if (j < grid[0].length - 1 && (grid[i - 1][j + 1] == '.' || round == 2 && ".JL7-|F".contains(grid[i - 1][j + 1] + ""))) {
                    grid[i - 1][j + 1] = '*';
                }
                if (j < grid[0].length - 1 && (grid[i][j + 1] == '.' || round == 2 && ".JL7-|F".contains(grid[i][j + 1] + ""))) {
                    grid[i][j + 1] = '*';
                }
            }
            if (direction.equals("leftup")) {
                if (j > 0 && (grid[i-1][j-1] == '.' || round == 2 && ".JL7-|F".contains(grid[i-1][j-1] +""))) {
                    grid[i-1][j-1] = '*';
                }
                if (j > 0 && grid[i][j-1] == '.' || round == 2 && ".JL7-|F".contains(grid[i][j-1] +"")) {
                    grid[i][j-1] = '*';
                }
            }
        }
        if (i < grid.length-1 && direction.equals("leftdown") || direction.equals("rightdown")) {
            if (grid[i+1][j] == '.' || round == 2 && ".JL7-|F".contains(grid[i+1][j] +"")) {
                grid[i+1][j] = '*';
            }
            if (direction.equals("leftdown")) {
                if (j > 0 && grid[i+1][j-1] == '.' || round == 2 && ".JL7-|F".contains(grid[i+1][j-1] +"")) {
                    grid[i+1][j-1] = '*';
                }
                if (j > 0 && grid[i][j-1] == '.' || round == 2 && ".JL7-|F".contains(grid[i][j-1] +"")) {
                    grid[i][j-1] = '*';
                }
            }
            if (direction.equals("rightdown")) {
                if (j < grid[0].length -1 && grid[i+1][j+1] == '.' || round == 2 && ".JL7-|F".contains(grid[i+1][j+1] +"")) {
                    grid[i+1][j+1] = '*';
                }
                if (j < grid[0].length -1 && grid[i][j+1] == '.' || round == 2 && ".JL7-|F".contains(grid[i][j+1] +"")) {
                    grid[i][j+1] = '*';
                }
            }
        }
        if (i > 0 && direction.contains("up") && ((grid[i-1][j] == '.') || (round == 2 && ".JL7-|F".contains(grid[i-1][j] + "")))) {
            grid[i-1][j] = '*';
        }
        if (i < grid.length-1 && direction.contains("down") && ((grid[i+1][j] == '.') || (round == 2 && ".JL7-|F".contains(grid[i+1][j] + "")))) {
            grid[i+1][j] = '*';
        }
        if (j > 0 && direction.contains("left") && ((grid[i][j-1] == '.') || (round == 2 && ".JL7-|F".contains(grid[i][j-1] + "")))) {
            grid[i][j-1] = '*';
        }
        if (j < grid[0].length-1 && direction.contains("right")  && ((grid[i][j+1] == '.') || (round == 2 && ".JL7-|F".contains(grid[i][j+1] + "")))){
            grid[i][j+1] = '*';
        }
    }

    public static String getDirection(String prevDir, int i, int j, int prevI, int prevJ) {
        if (i < 0 || i > grid.length-1 || j < 0 || j > grid[i].length-1 || (prevI == i && prevJ == j) || grid[i][j] == '*')  {
            return "";
        }
        char prev = grid[prevI][prevJ];
        char cur = grid[i][j];
        if (i > prevI && "|7FSi>fs".contains(prev + "") && "|LJSiljs".contains(cur + "")) {
            if (prevDir.equals("left") || prevDir.equals("leftup")) {
                if (cur == '|' || cur == 'i') return "left";
                if (cur == 'L' || cur == 'l') return "leftdown";
                if (cur == 'J'|| cur =='j') return "up";
            }
            if (prevDir.equals("right") || prevDir.equals("rightup")) {
                if (cur == '|'|| cur =='i') return "right";
                if (cur == 'L'|| cur =='l') return "up";
                if (cur == 'J'|| cur =='j') return "rightdown";
            }
        }
        if (i < prevI && "|LJSiljs".contains(prev + "") && "|7FSi>fs".contains(cur + "")) {
            if (prevDir.equals("left") || prevDir.equals("leftdown")) {
                if (cur == '|'|| cur =='i') return "left";
                if (cur == '7'|| cur =='>') return "down";
                if (cur == 'F'|| cur =='f') return "leftup";
            }
            if (prevDir.equals("right") || prevDir.equals("rightdown")) {
                if (cur == '|'|| cur =='i') return "right";
                if (cur == '7'|| cur =='>') return "rightup";
                if (cur == 'F'|| cur =='f') return "down";
            }
        }
        if (j > prevJ && "L-FSl_fs".contains(prev + "") && "7-JS>_js".contains(cur + "")) {
            if (prevDir.equals("up") || prevDir.equals("leftup")) {
                if (cur == '-'|| cur =='_') return "up";
                if (cur == 'J'|| cur =='j') return "left";
                if (cur == '7'|| cur =='>') return "rightup";
            }
            if (prevDir.equals("down") || prevDir.equals("leftdown")) {
                if (cur == '-'|| cur =='_') return "down";
                if (cur == 'J'|| cur =='j') return "rightdown";
                if (cur == '7'|| cur =='>') return "left";
            }
        }
        if (j < prevJ && "7-JS>_js".contains(prev + "") && "L-FSl_fs".contains(cur + "")) {
            if (prevDir.equals("down") || prevDir.equals("rightdown")) {
                if (cur == '-'|| cur =='_') return "down";
                if (cur == 'F'|| cur =='f') return "right";
                if (cur == 'L'|| cur =='l') return "leftdown";
            }
            if (prevDir.equals("up") || prevDir.equals("rightup") ) {
                if (cur == '-'|| cur =='_') return "up";
                if (cur == 'F'|| cur =='f') return "leftup";
                if (cur == 'L'|| cur =='l') return "right";
            }
        }
        if(cur == 'S'|| cur =='s') return "any";
        return "";
    }
}
