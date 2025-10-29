package uet.project.arkanoid.base;

public class Rectangle implements Shape {
    private Point center;
    private Vector2D size;
    private double rotation; // radian, 0 = no rotation

    public Rectangle(Point center, Vector2D size, double rotation) {
        this.center = center;
        this.size = size;
        this.rotation = rotation;
    }

    public boolean contains(Point p) {
        // Transform point into rectangle’s local space
        double cos = Math.cos(-rotation);
        double sin = Math.sin(-rotation);
        double dx = p.getX() - center.getX();
        double dy = p.getY() - center.getY();

        double localX = dx * cos - dy * sin;
        double localY = dx * sin + dy * cos;

        double halfW = size.getX() / 2.0;
        double halfH = size.getY() / 2.0;

        return Math.abs(localX) <= halfW && Math.abs(localY) <= halfH;
    }

    public boolean intersect(Shape other) {
        if (other instanceof Rectangle rect) {
            return intersectsRectangle(rect);
        }
        // you can later add Circle support, e.g. `else if (other instanceof Circle circle)`
        return false;
    }

    // ===== Utility Methods =====
    public boolean intersectsRectangle(Rectangle other) {
        // Simple version: use Separating Axis Theorem (SAT) for rotated rectangles
        Vector2D[] axes = getAxes(other);

        for (Vector2D axis : axes) {
            if (!overlapOnAxis(axis, this, other)) {
                return false; // gap found → no collision
            }
        }
        return true; // all projections overlap
    }

    private Vector2D[] getAxes(Rectangle other) {
        Vector2D[] axes = new Vector2D[4];
        Vector2D[] corners = getCorners();

        // 2 unique axes for this rect (edges)
        axes[0] = corners[1].subtract(corners[0]).normalize();
        axes[1] = corners[3].subtract(corners[0]).normalize();

        // 2 for other rect
        Vector2D[] otherCorners = other.getCorners();
        axes[2] = otherCorners[1].subtract(otherCorners[0]).normalize();
        axes[3] = otherCorners[3].subtract(otherCorners[0]).normalize();

        return axes;
    }

    private boolean overlapOnAxis(Vector2D axis, Rectangle a, Rectangle b) {
        double[] aProj = a.projectOnto(axis);
        double[] bProj = b.projectOnto(axis);
        return !(aProj[1] < bProj[0] || bProj[1] < aProj[0]);
    }

    private double[] projectOnto(Vector2D axis) {
        Vector2D[] corners = getCorners();
        double min = corners[0].dot(axis);
        double max = min;
        for (int i = 1; i < 4; i++) {
            double p = corners[i].dot(axis);
            if (p < min) min = p;
            if (p > max) max = p;
        }
        return new double[]{min, max};
    }

    public Vector2D[] getCorners() {
        double hw = size.getX() / 2.0;
        double hh = size.getY() / 2.0;

        Vector2D[] localCorners = new Vector2D[]{
                new Vector2D(-hw, -hh),
                new Vector2D(hw, -hh),
                new Vector2D(hw, hh),
                new Vector2D(-hw, hh)
        };

        Vector2D[] worldCorners = new Vector2D[4];
        double cos = Math.cos(rotation);
        double sin = Math.sin(rotation);

        for (int i = 0; i < 4; i++) {
            double x = localCorners[i].getX();
            double y = localCorners[i].getY();
            double worldX = center.getX() + x * cos - y * sin;
            double worldY = center.getY() + x * sin + y * cos;
            worldCorners[i] = new Vector2D(worldX, worldY);
        }

        return worldCorners;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public Vector2D getSize() {
        return size;
    }

    public void setSize(Vector2D size) {
        this.size = size;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }
}
