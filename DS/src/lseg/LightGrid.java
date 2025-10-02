package lseg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Coordinate {
    int x;
    int y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Instruction {
    String operation;
    Coordinate bottomLeft;
    Coordinate topRight;

    Instruction(String operation, Coordinate bottomLeft, Coordinate topRight) {
        this.operation = operation;
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
    }
}

public class LightGrid {
    protected int rows;
    protected int cols;
    protected int[][] matrix;
    protected String instrFilePath;

    public LightGrid(int rows, int cols, String instrFilePath) {
        this.rows = rows;
        this.cols = cols;
        this.instrFilePath = instrFilePath;
        this.matrix = new int[rows][cols]; // initialized to zero
    }

    public List<Instruction> parseInstructions() throws IOException {
        List<Instruction> instructions = new ArrayList<>();
        Pattern pattern = Pattern.compile("(turn on|turn off|toggle) (\\d+,\\d+) through (\\d+,\\d+)");

        try (BufferedReader br = new BufferedReader(new FileReader(instrFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line.trim());
                if (matcher.matches()) {
                    String op = matcher.group(1);
                    Coordinate bl = coordConv(parseCoord(matcher.group(2)));
                    Coordinate tr = coordConv(parseCoord(matcher.group(3)));
                    instructions.add(new Instruction(op, bl, tr));
                }
            }
        }
        return instructions;
    }

    private int[] parseCoord(String coordStr) {
        String[] parts = coordStr.split(",");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }

    /**
     * Converts 'natural' coordinates to Java array indices
     */
    public Coordinate coordConv(int x, int y) {
        // new origin is at (0, rows-1)
        int originX = 0;
        int originY = rows - 1;

        // shift coordinates
        int shiftedX = x - originX;
        int shiftedY = y - originY;

        // rotate coordinates by 90 degrees counterclockwise using matrix:
        // [0 -1]
        // [1  0]
        int newX = 0 * shiftedX + -1 * shiftedY; // = -shiftedY
        int newY = 1 * shiftedX + 0 * shiftedY;  // = shiftedX

        return new Coordinate(newX, newY);
    }

    public void turnOn(int x1, int y1, int x2, int y2) {
        for (int i = x2; i <= x1; i++) {
            for (int j = y1; j <= y2; j++) {
                matrix[i][j] = 1;
            }
        }
    }

    public void turnOff(int x1, int y1, int x2, int y2) {
        for (int i = x2; i <= x1; i++) {
            for (int j = y1; j <= y2; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public void toggle(int x1, int y1, int x2, int y2) {
        for (int i = x2; i <= x1; i++) {
            for (int j = y1; j <= y2; j++) {
                matrix[i][j] = 1 - matrix[i][j];
            }
        }
    }

    public int run() throws IOException {
        List<Instruction> instructions = parseInstructions();

        for (Instruction instr : instructions) {
            int x1 = instr.bottomLeft.x;
            int y1 = instr.bottomLeft.y;
            int x2 = instr.topRight.x;
            int y2 = instr.topRight.y;

            switch (instr.operation) {
                case "turn on" -> turnOn(x1, y1, x2, y2);
                case "turn off" -> turnOff(x1, y1, x2, y2);
                case "toggle" -> toggle(x1, y1, x2, y2);
            }
        }

        return getTotalLightsOn();
    }

    protected int getTotalLightsOn() {
        int sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }
}

class LightGridUpgraded extends LightGrid {

    public LightGridUpgraded(int rows, int cols, String instrFilePath) {
        super(rows, cols, instrFilePath);
    }

    @Override
    public void turnOn(int x1, int y1, int x2, int y2) {
        for (int i = x2; i <= x1; i++) {
            for (int j = y1; j <= y2; j++) {
                matrix[i][j] += 1;
            }
        }
    }

    @Override
    public void turnOff(int x1, int y1, int x2, int y2) {
        for (int i = x2; i <= x1; i++) {
            for (int j = y1; j <= y2; j++) {
                matrix[i][j] = Math.max(matrix[i][j] - 1, 0);
            }
        }
    }

    @Override
    public void toggle(int x1, int y1, int x2, int y2) {
        for (int i = x2; i <= x1; i++) {
            for (int j = y1; j <= y2; j++) {
                matrix[i][j] += 2;
            }
        }
    }

    @Override
    protected int getTotalLightsOn() {
        int sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }
}

