package hu.klenium.tetris.util;

public class Random {
    private Random() {}
    private static final java.util.Random generator = new java.util.Random();
    public static int fromRange(int min, int max) {
        return generator.nextInt(max - min + 1) + min;
    }
}