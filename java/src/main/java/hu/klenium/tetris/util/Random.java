package hu.klenium.tetris.util;

public class Random {
    private final static java.util.Random random = new java.util.Random();
    public static int fromRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}