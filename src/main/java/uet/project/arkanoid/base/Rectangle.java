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

    public Rectangle(double centerX, double centerY, double width, double height, double rotation) {
        this.center = new Point(centerX, centerY);
        this.size = new Vector2D(width, height);
        this.rotation  = rotation;
    }

    public Rectangle(Rectangle hitbox) {
        this.center = hitbox.center;
        this.size = hitbox.size;
        this.rotation = hitbox.rotation;
    }

    public boolean contains(Point p) {
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

    /**
     * Checks if 2 hitboxes are overlapping.
     * Deals with Circle x Rect
     * Basic case of AABB Rect x Rect
     * Special case of rotated Rect x rotated Rect
     *
     * @param other the shape we are comparing to
     * @return true if there exists intersection
     */
    public boolean intersect(Shape other) {
        if (other instanceof Rectangle rect) {

            // Fast path: both axis-aligned
            if (this.rotation == 0 && rect.rotation == 0) {
                double halfW = this.size.getX() / 2.0;
                double halfH = this.size.getY() / 2.0;
                double otherHalfW = rect.size.getX() / 2.0;
                double otherHalfH = rect.size.getY() / 2.0;

                double dx = Math.abs(this.center.getX() - rect.center.getX());
                double dy = Math.abs(this.center.getY() - rect.center.getY());

                return dx <= (halfW + otherHalfW) && dy <= (halfH + otherHalfH);
            }
            // Transform rect B into A’s local coordinate system
            double cos = Math.cos(-this.rotation);
            double sin = Math.sin(-this.rotation);

            // Transform B’s center relative to A
            double dx = rect.center.getX() - this.center.getX();
            double dy = rect.center.getY() - this.center.getY();

            double localBx = dx * cos - dy * sin;
            double localBy = dx * sin + dy * cos;

            double relRotation = rect.rotation - this.rotation;

            // A is axis-aligned in this local space
            double halfAx = this.size.getX() / 2.0;
            double halfAy = this.size.getY() / 2.0;

            // Compute the projection of B’s half-extents onto A’s axes
            double halfBx = rect.size.getX() / 2.0;
            double halfBy = rect.size.getY() / 2.0;

            double cosB = Math.cos(relRotation);
            double sinB = Math.sin(relRotation);

            // Effective projection of B onto A’s x and y axes
            double projBxOnA = Math.abs(halfBx * cosB) + Math.abs(halfBy * sinB);
            double projByOnA = Math.abs(halfBx * sinB) + Math.abs(halfBy * cosB);

            // Check overlap on A's axes
            if (Math.abs(localBx) > halfAx + projBxOnA) return false;
            if (Math.abs(localBy) > halfAy + projByOnA) return false;

            // Now project A onto B's axes (transformed)
            double projAxOnB = Math.abs(halfAx * cosB) + Math.abs(halfAy * sinB);
            double projAyOnB = Math.abs(halfAx * sinB) + Math.abs(halfAy * cosB);

            // Transform A’s center (0,0) into B’s local space
            double invCos = Math.cos(-relRotation);
            double invSin = Math.sin(-relRotation);
            double localAx = -localBx * invCos - -localBy * invSin;
            double localAy = -localBx * invSin + -localBy * invCos;

            if (Math.abs(localAx) > halfBx + projAxOnB) return false;
            if (Math.abs(localAy) > halfBy + projAyOnB) return false;

            return true;
        }

        else if (other instanceof Circle circle) {
            return circle.intersect(this);
        }

        return false;
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

    public void setCenter(double centerX, double centerY) {
        this.center.setX(centerX);
        this.center.setY(centerY);
    }
}
