package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.base.Shape; // Import new Shape interface

public abstract class GameObject {

  protected double width;
  protected double height;
  private double x;
  private double y;

  public GameObject(double x, double y, double width, double height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public abstract Shape getHitbox();

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public abstract void update(double deltaTime);

  public abstract void render(GraphicsContext gc);

}