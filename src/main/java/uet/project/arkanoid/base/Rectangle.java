package uet.project.arkanoid.base;

/**
 * Represents a rectangular shape with position, size, and rotation capabilities.
 *
 * <p>This class implements the Shape interface and provides methods for collision detection
 * and geometric transformations. The rectangle is defined by its center point, size, and rotation.
 */
public class Rectangle implements Shape {

  private Point center;
  private Vector2D size;
  private double rotation; // radian, 0 = no rotation

  /**
   * Constructs a new Rectangle with specified center, size, and rotation.
   *
   * @param center the center point of the rectangle
   * @param size the dimensions of the rectangle (width and height)
   * @param rotation the rotation angle in radians
   */
  public Rectangle(Point center, Vector2D size, double rotation) {
    this.center = center;
    this.size = size;
    this.rotation = rotation;
  }

  /**
   * Constructs a new Rectangle with specified coordinates, dimensions, and rotation.
   *
   * @param centerX the x-coordinate of the center point
   * @param centerY the y-coordinate of the center point
   * @param width the width of the rectangle
   * @param height the height of the rectangle
   * @param rotation the rotation angle in radians
   */
  public Rectangle(double centerX, double centerY, double width, double height, double rotation) {
    this.center = new Point(centerX, centerY);
    this.size = new Vector2D(width, height);
    this.rotation = rotation;
  }

  /**
   * Copy constructor that creates a new Rectangle from an existing one.
   *
   * @param hitbox the rectangle to copy
   */
  public Rectangle(Rectangle hitbox) {
    this.center = hitbox.center;
    this.size = hitbox.size;
    this.rotation = hitbox.rotation;
  }

  /**
   * Checks if this rectangle contains the specified point.
   *
   * @param p the point to check
   * @return true if the point is inside the rectangle, false otherwise
   */
  public boolean contains(Point p) {
    double cos = Math.cos(-rotation);
    double sin = Math.sin(-rotation);
    double dx = p.getX() - center.getX();
    double dy = p.getY() - center.getY();

    double localX = dx * cos - dy * sin;
    double localY = dx * sin + dy * cos;

    double halfW = size.getX() / 2.0;
    double halfH = size.getY() / 2.0;

    return Math.abs(localX) <= halfW && Math.abs(localY) <= halfH;
  }

  /**
   * Checks if this rectangle intersects with another shape.
   *
   * @param other the other shape to check for intersection
   * @return true if the shapes intersect, false otherwise
   */
  public boolean intersect(Shape other) {
    if (other instanceof Rectangle rect) {
      double halfW = this.size.getX() / 2.0;
      double halfH = this.size.getY() / 2.0;
      double otherHalfW = rect.size.getX() / 2.0;
      double otherHalfH = rect.size.getY() / 2.0;

      double dx = Math.abs(this.center.getX() - rect.center.getX());
      double dy = Math.abs(this.center.getY() - rect.center.getY());

      return dx <= (halfW + otherHalfW) && dy <= (halfH + otherHalfH);
    } else if (other instanceof Circle circle) {
      return circle.intersect(this);
    }
    return false;
  }

  /**
   * Gets the rotation angle of the rectangle.
   *
   * @return the rotation angle in radians
   */
  public double getRotation() {
    return rotation;
  }

  /**
   * Sets the rotation angle of the rectangle.
   *
   * @param rotation the new rotation angle in radians
   */
  public void setRotation(double rotation) {
    this.rotation = rotation;
  }

  /**
   * Gets the size of the rectangle.
   *
   * @return the dimensions of the rectangle as a Vector2D
   */
  public Vector2D getSize() {
    return size;
  }

  /**
   * Sets the size of the rectangle.
   *
   * @param size the new dimensions of the rectangle
   */
  public void setSize(Vector2D size) {
    this.size = size;
  }

  /**
   * Gets the center point of the rectangle.
   *
   * @return the center point
   */
  public Point getCenter() {
    return center;
  }

  /**
   * Sets the center point of the rectangle.
   *
   * @param center the new center point
   */
  public void setCenter(Point center) {
    this.center = center;
  }

  /**
   * Sets the center point of the rectangle using coordinates.
   *
   * @param centerX the x-coordinate of the new center
   * @param centerY the y-coordinate of the new center
   */
  public void setCenter(double centerX, double centerY) {
    this.center.setX(centerX);
    this.center.setY(centerY);
  }
}