package uet.project.arkanoid;

public class Brick {
    private int hitPoints;
    private String type;

    public Brick(int hitPoints, String type) {
        this.hitPoints = hitPoints;
        this.type = type;
    }

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
}
