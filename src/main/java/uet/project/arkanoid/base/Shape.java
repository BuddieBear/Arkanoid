package uet.project.arkanoid.base;

public interface Shape {
    boolean intersect(Shape other);
    boolean contains(Point p);
}
