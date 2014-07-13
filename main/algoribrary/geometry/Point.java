package algoribrary.geometry;

import algoribrary.comparators.DoubleComparator;

/**
 * @author Vadim Semenov
 */
public class Point {
    public final double x, y;
    private DoubleComparator comparator = DoubleComparator.DEFAULT;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Point fromPolar(double abs, double angle) {
        return new Point(abs * Math.cos(angle), abs * Math.sin(angle));
    }

    public Point conjugate() {
        return new Point(x, -y);
    }

    public Point add(Point other) {
        return new Point(this.x + other.x, this.y + other.y);
    }

    public Point subtract(Point other) {
        return new Point(this.x - other.x, this.y - other.y);
    }

    public Point multiply(Point other) {
        return new Point(
                this.x * other.x - this.y * other.y,
                this.x * other.y + this.y * other.x
        );
    }

    public Point divide(Point other) {
        return this.conjugate().multiply(other).multiply(this.sqaredAbs());
    }

    public Point multiply(double mul) {
        return new Point(this.x * mul, this.y * mul);
    }

    public Point rotate(double angle) {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        return new Point(
                this.x * cos - this.y * sin,
                this.x * sin + this.y * cos
        );
    }

    public int counterClockWise(Point a, Point b) {
        a = a.subtract(this);
        b = b.subtract(this);
        return comparator.compare(a.vectorProduct(b), 0);
    }

    public double distanceTo(Point other) {
        return Math.sqrt(sqaredDistanceTo(other));
    }

    private double sqaredDistanceTo(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return dx * dx + dy * dy;
    }

    public double abs() {
        return Math.sqrt(sqaredAbs());
    }

    public double sqaredAbs() {
        return x * x + y * y;
    }

    public double angle() {
        return Math.atan2(y, x);
    }

    public double scalarProduct(Point other) {
        return this.x * other.x + this.y * other.y;
    }

    public double vectorProduct(Point other) {
        return this.x * other.y - this.y * other.x;
    }

    public void setComparator(DoubleComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (comparator.compare(x, point.x) != 0) return false;
        if (comparator.compare(y, point.y) != 0) return false;

        return true;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
