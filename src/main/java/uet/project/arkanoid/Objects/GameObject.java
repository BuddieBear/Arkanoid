package uet.project.arkanoid.Objects;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    protected int width;
    protected int height;
    private int x;
    private int y;

    public GameObject(int x, int y, int width, int height) {
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

    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public abstract void update();

    public abstract void render(GraphicsContext gc);

}
