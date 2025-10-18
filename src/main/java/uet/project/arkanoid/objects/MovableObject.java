package uet.project.arkanoid.objects;

public abstract class MovableObject extends GameObject {
    private int dx = 0;
    private int dy = 0;

    public MovableObject(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public int getDx() {
        return this.dx;
    }
    public void setDx(int dx) {
        this.dx = dx;
    }
    public int getDy() {
        return this.dy;
    }
    public void setDy(int dy) {
        this.dy = dy;
    }

    public abstract void move();
}
