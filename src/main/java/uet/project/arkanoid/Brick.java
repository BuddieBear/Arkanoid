package uet.project.arkanoid;

public class Brick extends GameObject{
    private int hitPoints;
    private String type;

    public Brick(int x, int y, int width, int height, int hitPoints, String type) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
    }

    //TODO: Setter

    public int takeHit() {
        if (hitPoints > 0) {
            hitPoints--;
        } else {
            hitPoints = 0;
        }
        return hitPoints;
    }

    public boolean isDestroy() {
        return hitPoints <= 0;
    }

    public void render() {

    }

    public void update() {

    }
}
