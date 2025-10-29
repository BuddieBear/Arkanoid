package uet.project.arkanoid.base;

public class Circle implements Shape {
    private Point center;
    private double radius;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public boolean intersect(Shape other) {
        if (other instanceof Circle circle) { // Ball vs Ball
            // Circle–Circle intersection
            double distanceBetweenCenters = this.center.distanceTo(circle.center);
            return distanceBetweenCenters <= this.radius + circle.radius;
        }
        else if (other instanceof Rectangle rect) { // Ball vs Brick
            // Circle–Rectangle intersection
            return intersectsRectangle(rect);
        }
        // Unknown shape type
        return false;
    }

    public boolean contains(Point p) {
        double distanceToPoint = p.distanceTo(center);
        return distanceToPoint <= radius;
    }

    private boolean intersectsRectangle(Rectangle rect) {
        // For now assume rectangle is axis-aligned (no rotation)
        if (rect.getRotation() == 0) {
            double rx = rect.getCenter().getX() - rect.getSize().getX() / 2.0;
            double ry = rect.getCenter().getY() - rect.getSize().getY() / 2.0;
            double rw = rect.getSize().getX();
            double rh = rect.getSize().getY();

            // Find closest point on rectangle to circle center
            double closestX = clamp(center.getX(), rx, rx + rw);
            double closestY = clamp(center.getY(), ry, ry + rh);

            double dx = center.getX() - closestX;
            double dy = center.getY() - closestY;

            return dx * dx + dy * dy <= radius * radius;
        }
    }

    private double clamp(double value, double min, double max) {
        if (value > max) {
            return max;
        } else if (value < min) {
            return min;
        }
        return value;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setCircle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

}
