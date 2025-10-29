package uet.project.arkanoid.objects;

public abstract class MovableObject extends GameObject {
    private Vector2D direction;

    public MovableObject(int x, int y, double width, double height) {
        super(x, y, width, height);
    }

    public abstract void move();
}
