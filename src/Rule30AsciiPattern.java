public class Rule30AsciiPattern {

    public static void main(String[] args) throws InterruptedException {
        int width = 140;
        //"on" and "off" states
        char onChar = '#';
        char offChar = ' ';

        boolean[] cells = new boolean[width];

        cells[width / 2] = true;


        while (true) {

            printGeneration(cells, onChar, offChar);

            cells = calculateNextGeneration(cells);

            // Pause to control the speed
            Thread.sleep(100);
        }
    }


    public static void printGeneration(boolean[] cells, char onChar, char offChar) {
        char[] line = new char[cells.length];
        for (int i = 0; i < cells.length; i++) {
            line[i] = cells[i] ? onChar : offChar;
        }
        System.out.println(new String(line));
    }


    public static boolean[] calculateNextGeneration(boolean[] currentGeneration) {
        int width = currentGeneration.length;
        boolean[] nextGeneration = new boolean[width];

        for (int i = 0; i < width; i++) {
            // Get the state of the three cells, wrap around if at end of array
            boolean left = currentGeneration[(i - 1 + width) % width];
            boolean center = currentGeneration[i];
            boolean right = currentGeneration[(i + 1) % width];

            // Apply Rule 30
            nextGeneration[i] = left ^ (center || right);
        }
        return nextGeneration;
    }
}
