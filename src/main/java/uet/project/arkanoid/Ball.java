package uet.project.arkanoid;

public class Ball extends MovableObject {
    private int speed = 0;
    private int directionX = 0;
    private int directionY = 0;

    //TODO: Setter and constructor and move(), render(), update(),...

    public boolean bounceOff(GameObject other) {
        return false;
    }
    public boolean checkCollision(GameObject other) {
        return false;
    }
}
