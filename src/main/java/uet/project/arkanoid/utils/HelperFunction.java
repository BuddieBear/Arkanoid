package uet.project.arkanoid.utils;

public class HelperFunction {
    public static double clamp(double value, double min, double max) {
        if (value > max) {
            return max;
        } else if (value < min) {
            return min;
        }
        return value;
    }

    public static boolean inBounds(double x, double y, double bx, double by, double bw, double bh) {
        return (x >= bx && x <= bx + bw && y >= by && y <= by + bh);
    }
}
