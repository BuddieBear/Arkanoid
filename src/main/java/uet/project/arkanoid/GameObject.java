package uet.project.arkanoid;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    public int x;
    public int y;
    public int width;
    public int height;

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void update();
    public abstract void render(/*GraphicsContext gc*/);

}
