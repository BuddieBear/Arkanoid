package uet.project.arkanoid.objects;

import uet.project.arkanoid.base.Vector2D;

public abstract class MovableObject extends GameObject {
    private Vector2D direction;

    public MovableObject(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.direction = new Vector2D(0, 0);
    }

    public void setDx(double dx) {
        this.direction.setX(dx);
    }

    public void setDy(double dy) {
        this.direction.setY(dy);
    }

    public double getDx() {
        return direction.getX();
    }

    public double getDy() {
        return direction.getY();
    }

    public void setDirection(Vector2D direction) {
        this.direction = direction;
    }

    public Vector2D getDirection() {
        return direction;
    }

    public abstract void move();
}
