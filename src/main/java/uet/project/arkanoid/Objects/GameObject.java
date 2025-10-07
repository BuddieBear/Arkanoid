package uet.project.arkanoid.Objects;

public abstract class GameObject {
    private int x;
    private int y;
    private int width;
    private int height;

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
        this.x = x;
    }

    public abstract void update();
    public abstract void render(/*GraphicsContext gc*/);

}
