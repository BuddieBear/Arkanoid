package uet.project.arkanoid.base;

public class Vector2D {

    private double x;
    private double y;


    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
    }


    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }


    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }


    public Vector2D multiply(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }


    public Vector2D divide(double scalar) {
        try {
            return new Vector2D(this.x / scalar, this.y / scalar);
        } catch (ArithmeticException e) {
            System.err.println("Division by zero");
        }
        return new Vector2D(0, 0);
    }

    public double dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }


    public double cross(Vector2D other) {
        return this.x * other.y - this.y * other.x;
    }


    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }



    public Vector2D normalize() {
        double len = getLength();
        if (len == 0) {
            return new Vector2D(0, 0);
        }
        return new Vector2D(x / len, y / len);
    }


    public double distanceTo(Vector2D other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double angle() {
        return Math.atan2(y, x);
    }


    public double angleTo(Vector2D other) {
        return Math.atan2(other.y, other.x) - Math.atan2(this.y, this.x);
    }

    /**
     * Rotates the vector by the given angle (radians).
     *
     * @param radians the angle to rotate by in radians
     * @return a new rotated vector
     */
    public Vector2D rotate(double radians) {
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        return new Vector2D(x * cos - y * sin, x * sin + y * cos);
    }

    /**
     * Change Vector2D to point.
     *
     * @return point that has the same X, Y cord
     */
    public Point toPoint() {
        return new Point(this.x, this.y);
    }

    public void setVector(double x, double y) {
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

    @Override
    public String toString() {
        return "Vector2D(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector2D other)) {
            return false;
        }
        return Math.abs(this.x - other.x) < 1e-9 && Math.abs(this.y - other.y) < 1e-9;
    }

}