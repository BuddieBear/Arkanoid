package uet.project.arkanoid.base;

import uet.project.arkanoid.utils.HelperFunction;

/**
 * Represents a circular shape with a center point and radius.
 *
 * <p>This class implements the Shape interface and provides methods for collision detection
 * with other shapes including circles and rectangles.
 */
public class Circle implements Shape {

  private Point center;
  private double radius;

  /**
   * Constructs a new Circle with specified center and radius.
   *
   * @param center the center point of the circle
   * @param radius the radius of the circle
   */
  public Circle(Point center, double radius) {
    this.center = center;
    this.radius = radius;
  }

  /**
   * Checks if this circle intersects with another shape.
   *
   * @param other the other shape to check for intersection
   * @return true if the shapes intersect, false otherwise
   */
  public boolean intersect(Shape other) {
    if (other instanceof Circle circle) { // Ball vs Ball
      // Circle–Circle intersection
      double distanceBetweenCenters = this.center.distanceTo(circle.center);
      return distanceBetweenCenters <= this.radius + circle.radius;
    } else if (other instanceof Rectangle rect) { // Ball vs Brick
      // Circle–Rectangle intersection
      return intersectsRectangle(rect);
    }
    // Unknown shape type
    return false;
  }

    public boolean contains(Point p) {
        double distanceToPoint = p.distanceTo(center);
        return distanceToPoint <= radius;
    }

    /**
     * Checks for intersection with a rotated rectangle.
     * This works by centering the rectangle at (0,0),
     * and rotate so that the edge of rect is paralel to the axis.
     * Transform the ball circle to the local rectangle
     *
     * @return true if intersection occurs
     */
    private boolean intersectsRectangle(Rectangle rect) {
        // Get rectangle properties
        Point rectCenter = rect.getCenter();
        Vector2D rectSize = rect.getSize();
        double rectRotation = rect.getRotation();

    // Vector of Center to Center
    double dx = this.center.getX() - rectCenter.getX();
    double dy = this.center.getY() - rectCenter.getY();

    // Rotate the rect to align with the Oxy (Undo rotation) and centered at (0, 0)
    double cos = Math.cos(-rectRotation);
    double sin = Math.sin(-rectRotation);

    double localCircleX = dx * cos - dy * sin;
    double localCircleY = dx * sin + dy * cos;

    // Find the closest point on the AABB (centered at 0,0) to the local circle center (hinh chieu)
    double closestX = HelperFunction.clamp(localCircleX, -rectSize.getX() / 2, rectSize.getX() / 2);
    double closestY = HelperFunction.clamp(localCircleY, -rectSize.getY() / 2, rectSize.getY() / 2);

    // Calculate distance from local circle center to this closest point (duong cao)
    double distDx = localCircleX - closestX;
    double distDy = localCircleY - closestY;
    double distance = Math.sqrt((distDx * distDx) + (distDy * distDy));

    return distance <= this.radius;
  }

    // Getter and setter
    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setCircle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

}
