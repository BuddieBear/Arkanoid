package uet.project.arkanoid;

import java.awt.event.MouseAdapter;

public class Ball extends MouseAdapter {
    private int speed = 0;
    private int directionX = 0;
    private int directionY = 0;

    public boolean bounceOff(GameObject other) {
        return false;
    }
    public boolean checkCollision(GameObject other) {
        return false;
    }
}
