package uet.project.arkanoid;

public abstract class MovableObject extends GameObject{
    private int dx = 0;
    private int dy = 0;

    public MovableObject(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public abstract void move();
}
