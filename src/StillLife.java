import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StillLife {

    // ANSI escape code to clear the console screen
    public static final String CLEAR_SCREEN = "\033[H\033[2J";

    // --- Scene Dimensions ---
    private static final int WIDTH = 80;
    private static final int HEIGHT = 20;
    private static final int HORIZON_Y = 7;

    // --- Animation Speed ---
    private static final int FRAME_DELAY_MS = 200; // 5 frames per second

    private static class Snowflake {
        int x, y;
        Snowflake(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        char[][] canvas = new char[HEIGHT][WIDTH];

        String[] penguinArt = {
            "Penguin",
            "  .--.",
            " |o_o |",
            " |/_/ |",
            "//   \\ \\",
            "(|     | )",
            "/'\\_   _/`\\",
            "\\___)=(___/ AH"
        };

        String[] iglooArt = {
            "Igloo",
            "   .--.   ",
            "  /    \\  ",
            " /      \\ ",
            "|   __   |",
            "|  |  |  |"
        };

        int penguinX = 35;
        int penguinY = 10;
        int iglooX = 5;
        int iglooY = 13;

        List<Snowflake> snowflakes = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            snowflakes.add(new Snowflake(random.nextInt(WIDTH), random.nextInt(HEIGHT)));
        }

        while (true) {
            try {
                System.out.print(CLEAR_SCREEN);

                // Move penguin randomly
                int move = random.nextInt(5); // 0:Left, 1:Right, 2:Up, 3:Down, 4:Stay
                int nextX = penguinX;
                int nextY = penguinY;

                if (move == 0) nextX--;
                if (move == 1) nextX++;
                if (move == 2) nextY--;
                if (move == 3) nextY++;

                // Boundary checks
                if (nextX > 0 && (nextX + getArtWidth(penguinArt)) < WIDTH) {
                    penguinX = nextX;
                }
                if (nextY >= HORIZON_Y && (nextY + penguinArt.length) < HEIGHT) {
                    penguinY = nextY;
                }

                // Animate snowflakes
                for (Snowflake flake : snowflakes) {
                    flake.y++;
                    if (flake.y >= HEIGHT - 1) {
                        flake.y = 0;
                        flake.x = random.nextInt(WIDTH);
                    }
                }


                clearCanvas(canvas);

                drawHorizon(canvas);
                drawGround(canvas);

                // Draw objects based on their Y position to create depth
                if ((penguinY + penguinArt.length) < (iglooY + iglooArt.length)) {
                    drawArt(canvas, penguinArt, penguinX, penguinY);
                    drawArt(canvas, iglooArt, iglooX, iglooY);
                } else {
                    drawArt(canvas, iglooArt, iglooX, iglooY);
                    drawArt(canvas, penguinArt, penguinX, penguinY);
                }

                // Draw snowflakes on top
                for (Snowflake flake : snowflakes) {
                    drawArt(canvas, new String[]{"*"}, flake.x, flake.y);
                }

                StringBuilder screenBuffer = new StringBuilder();
                for (int i = 0; i < HEIGHT; i++) {
                    screenBuffer.append(canvas[i]);
                    screenBuffer.append('\n');
                }
                System.out.print(screenBuffer.toString());
                System.out.flush();

                Thread.sleep(FRAME_DELAY_MS);

            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private static void clearCanvas(char[][] canvas) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                canvas[i][j] = ' ';
            }
        }
    }


    private static void drawHorizon(char[][] canvas) {
        for (int i = 0; i < WIDTH; i++) {
            canvas[HORIZON_Y][i] = '-';
        }
    }


    private static void drawGround(char[][] canvas) {
        for (int i = 0; i < WIDTH; i++) {
            canvas[HEIGHT - 1][i] = '=';
        }
    }


    private static void drawArt(char[][] canvas, String[] art, int startX, int startY) {
        for (int i = 0; i < art.length; i++) {
            for (int j = 0; j < art[i].length(); j++) {
                int y = startY + i;
                int x = startX + j;
                if (y >= 0 && y < HEIGHT && x >= 0 && x < WIDTH) {
                    canvas[y][x] = art[i].charAt(j);
                }
            }
        }
    }


    private static int getArtWidth(String[] art) {
        int maxWidth = 0;
        for (String line : art) {
            if (line.length() > maxWidth) {
                maxWidth = line.length();
            }
        }
        return maxWidth;
    }
}
