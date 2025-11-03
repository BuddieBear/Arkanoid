package uet.project.arkanoid.base;

/**
 * Represents a 2D vector with x and y components.
 *
 * <p>This class provides common vector operations such as addition, subtraction,
 * dot product, cross product, normalization, rotation, and distance calculations.
 */
public class Vector2D {

  private double x;
  private double y;

  /**
   * Constructs a new Vector2D with specified components.
   *
   * @param x the x-component of the vector
   * @param y the y-component of the vector
   */
  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Copy constructor that creates a new Vector2D from an existing one.
   *
   * @param other the vector to copy
   */
  public Vector2D(Vector2D other) {
    this.x = other.x;
    this.y = other.y;
  }

  /**
   * Adds this vector to another vector.
   *
   * @param other the vector to add
   * @return a new vector representing the sum
   */
  public Vector2D add(Vector2D other) {
    return new Vector2D(this.x + other.x, this.y + other.y);
  }

  /**
   * Subtracts another vector from this vector.
   *
   * @param other the vector to subtract
   * @return a new vector representing the difference
   */
  public Vector2D subtract(Vector2D other) {
    return new Vector2D(this.x - other.x, this.y - other.y);
  }

  /**
   * Multiplies this vector by a scalar value.
   *
   * @param scalar the scalar value to multiply by
   * @return a new vector representing the scaled vector
   */
  public Vector2D multiply(double scalar) {
    return new Vector2D(this.x * scalar, this.y * scalar);
  }

  /**
   * Divides this vector by a scalar value.
   *
   * @param scalar the scalar value to divide by
   * @return a new vector representing the divided vector
   * @throws ArithmeticException if scalar is zero
   */
  public Vector2D divide(double scalar) {
    try {
      return new Vector2D(this.x / scalar, this.y / scalar);
    } catch (ArithmeticException e) {
      System.err.println("Division by zero");
    }
    return new Vector2D(0, 0);
  }

  /**
   * Calculates the dot product of this vector and another vector.
   *
   * @param other the other vector
   * @return the dot product value
   */
  public double dot(Vector2D other) {
    return this.x * other.x + this.y * other.y;
  }

  /**
   * Calculates the cross product of this vector and another vector.
   *
   * @param other the other vector
   * @return the cross product value (z-component of 3D cross product)
   */
  public double cross(Vector2D other) {
    return this.x * other.y - this.y * other.x;
  }

  /**
   * Calculates the length (magnitude) of this vector.
   *
   * @return the length of the vector
   */
  public double getLength() {
    return Math.sqrt(x * x + y * y);
  }

  /**
   * Returns a normalized version of this vector (unit vector).
   *
   * @return a new normalized vector, or zero vector if length is zero
   */
  public Vector2D normalize() {
    double len = getLength();
    if (len == 0) {
      return new Vector2D(0, 0);
    }
    return new Vector2D(x / len, y / len);
  }

  /**
   * Calculates the distance between this vector and another vector.
   *
   * @param other the other vector
   * @return the distance between the two vectors
   */
  public double distanceTo(Vector2D other) {
    double dx = this.x - other.x;
    double dy = this.y - other.y;
    return Math.sqrt(dx * dx + dy * dy);
  }

  /**
   * Calculates the angle of this vector in radians.
   *
   * @return the angle in radians between -π and π
   */
  public double angle() {
    return Math.atan2(y, x);
  }

  /**
   * Returns the signed angle (in radians) from this vector to the other.
   * The result is between -π and π.
   *
   * @param other the other vector
   * @return the signed angle in radians
   */
  public double angleTo(Vector2D other) {
    return Math.atan2(other.y, other.x) - Math.atan2(this.y, this.x);
  }

  /**
   * Rotates the vector by the given angle (radians).
   *
   * @param radians the angle to rotate by in radians
   * @return a new rotated vector
   */
  public Vector2D rotate(double radians) {
    double cos = Math.cos(radians);
    double sin = Math.sin(radians);
    return new Vector2D(x * cos - y * sin, x * sin + y * cos);
  }

  /**
   * Sets the components of this vector.
   *
   * @param x the new x-component
   * @param y the new y-component
   */
  public void setVector(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x-component of this vector.
   *
   * @return the x-component
   */
  public double getX() {
    return x;
  }

  /**
   * Sets the x-component of this vector.
   *
   * @param x the new x-component
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Gets the y-component of this vector.
   *
   * @return the y-component
   */
  public double getY() {
    return y;
  }

  /**
   * Sets the y-component of this vector.
   *
   * @param y the new y-component
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * Returns a string representation of this vector.
   *
   * @return a string in the format "Vector2D(x, y)"
   */
  @Override
  public String toString() {
    return "Vector2D(" + x + ", " + y + ")";
  }

  /**
   * Checks if this vector is equal to another object.
   *
   * @param obj the object to compare with
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Vector2D other)) {
      return false;
    }
    return Math.abs(this.x - other.x) < 1e-9 && Math.abs(this.y - other.y) < 1e-9;
  }
}