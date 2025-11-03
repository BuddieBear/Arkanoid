package uet.project.arkanoid.base;

public class Point {
  private double x;
  private double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Point(Point other) {
    this.x = other.x;
    this.y = other.y;
  }

  public double distanceTo(Point other) {
    double dx = this.x - other.x;
    double dy = this.y - other.y;
    return Math.sqrt(dx * dx + dy * dy);
  }


  public Vector2D vectorTo(Point endPoint) {
    return new Vector2D(endPoint.x - this.x, endPoint.y - this.y);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Point)) {
      return false;
    }
    Point other = (Point) obj;
    return Double.compare(this.x, other.x) == 0 &&
        Double.compare(this.y, other.y) == 0;
  }


  public String toString() {
    return "(" + x + ", " + y + ")";
  }


  public void set(double x, double y) {
    this.x = x;
    this.y = y;
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
}