package tasks;

public class ShootingGame {
    public double findProbability(int p) {
        int denominator = 1_000_000;
        if (p + p > denominator) return -1.0;
        return (double) p / (denominator - p);
    }
}
