package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    protected double width;
    protected double height;
    private int x;
    private int y;

    public GameObject(int x, int y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int X) {
        this.x = X;
    }

    public int getY() {
        return y;
    }

    public void setY(int Y) {
        this.y = Y;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean checkCollision(GameObject other) {
        return this.getX() < other.getX() + other.getWidth()
                && this.getX() + this.getWidth() > other.getX()
                && this.getY() < other.getY() + other.getHeight()
                && this.getY() + this.getHeight() > other.getY();
    }

    public abstract void update();

    public abstract void render(GraphicsContext gc);

}
