package uet.project.arkanoid.objects;

public abstract class MovableObject extends GameObject {
    private double dx = 0;
    private double dy = 0;

    public MovableObject(int x, int y, double width, double height) {
        super(x, y, width, height);
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public abstract void move();
}
