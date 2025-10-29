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
        return false;
    }

    public Vector2D[] getCorners() {
        double hw = size.getX() / 2.0;
        double hh = size.getY() / 2.0;

        // From Center = (0, 0) + Centered
        Vector2D[] localCorners = new Vector2D[]{
                new Vector2D(-hw, -hh),
                new Vector2D(hw, -hh),
                new Vector2D(hw, hh),
                new Vector2D(-hw, hh)
        };

        // From (0, 0) + Rotated
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
