package uet.project.arkanoid.base;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    // Distance between 2 points
    public double distanceTo(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Create a vector with this as the root
    public Vector2D vectorTo(Point endPoint) {
        return new Vector2D(endPoint.x - this.x, endPoint.y - this.y);
    }

    /**
     * Compares 2 points to see if they are the same.
     *
     * @param obj   the reference object with which to compare.
     * @return true if so, else false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Point)) {
            return false;
        }
        Point other = (Point) obj;
        return Double.compare(this.x, other.x) == 0 &&
            Double.compare(this.y, other.y) == 0;
    }

    // Print out, help debugging
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Vector2D toVector() {
        return new Vector2D(this.x, this.y);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
