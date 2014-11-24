package algoribrary.geometry;

import algoribrary.comparators.DoubleComparator;

/**
 * @author Vadim Semenov
 */
public class Circle {
    public final Point center;
    public final double radius;
    private DoubleComparator comparator = DoubleComparator.DEFAULT;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public double length() {
        return 2 * Math.PI * radius;
    }

    public double area() {
        return Math.PI * radius * radius;
    }

    public Point[] intersect(Circle that) {
        double distance = this.center.distanceTo(that.center);
        if (comparator.compare(distance, this.radius + that.radius) > 0 ||
                comparator.compare(distance + Math.min(this.radius, that.radius), Math.max(this.radius, that.radius)) < 0) {
            return new Point[0];
        }
        if (comparator.compare(distance, 0) == 0) {
            return new Point[3];
        }
        double d = ((this.radius * this.radius - that.radius * that.radius) / distance + distance) * .5;
        Point temp = that.center.subtract(this.center);
        Point base = temp.multiply(d / temp.abs()).add(this.center);
        double mul = this.radius * this.radius - d * d;
        if (comparator.compare(mul, 0) == 0) {
            return new Point[]{base};
        }
        mul = Math.sqrt(mul);
        Point shift = new Point(-temp.y, temp.x).multiply(mul / temp.abs());
        return new Point[]{base.add(shift), base.subtract(shift)};
    }

    public void setComparator(DoubleComparator comparator) {
        this.comparator = comparator;
        this.center.setComparator(comparator);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Circle circle = (Circle) o;

        if (Double.compare(circle.radius, radius) != 0) return false;
        if (!center.equals(circle.center)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = center.hashCode();
        temp = Double.doubleToLongBits(radius);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "(" + center + ", " + radius + ")";
    }
}
