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

    public boolean intersect(Shape other) {
        if (other instanceof Rectangle rect) {
                double halfW = this.size.getX() / 2.0;
                double halfH = this.size.getY() / 2.0;
                double otherHalfW = rect.size.getX() / 2.0;
                double otherHalfH = rect.size.getY() / 2.0;

                double dx = Math.abs(this.center.getX() - rect.center.getX());
                double dy = Math.abs(this.center.getY() - rect.center.getY());

                return dx <= (halfW + otherHalfW) && dy <= (halfH + otherHalfH);
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
