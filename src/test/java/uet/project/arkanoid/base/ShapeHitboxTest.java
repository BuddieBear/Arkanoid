package uet.project.arkanoid.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShapeHitboxTest {

    @Test
    void testPointCoordinates() {
        Point p = new Point(3.5, 4.5);
        assertEquals(3.5, p.getX());
        assertEquals(4.5, p.getY());
    }

    @Test
    void testVectorLengthAndNormalize() {
        Vector2D v = new Vector2D(3, 4);
        assertEquals(5.0, v.getLength(), 1e-9);

        Vector2D n = v.normalize();
        assertEquals(1.0, n.getLength(), 1e-9);
        assertEquals(0.6, n.getX(), 1e-9);
        assertEquals(0.8, n.getY(), 1e-9);
    }

    @Test
    void testDotProduct() {
        Vector2D v1 = new Vector2D(1, 0);
        Vector2D v2 = new Vector2D(0, 1);
        Vector2D v3 = new Vector2D(1, 1);

        assertEquals(0.0, v1.dot(v2));  // perpendicular
        assertEquals(1.0, v1.dot(v3));  // 1×1 + 0×1 = 1
    }

    /* ---------- CIRCLE ---------- */
    @Test
    void testCircleIntersectsAnotherCircle() {
        Circle c1 = new Circle(new Point(0, 0), 10);
        Circle c2 = new Circle(new Point(15, 0), 10);

        assertTrue(c1.intersect(c2), "Circles overlap since centers 15 apart and radii sum = 20");
    }

    @Test
    void testCircleDoesNotIntersect() {
        Circle c1 = new Circle(new Point(0, 0), 10);
        Circle c2 = new Circle(new Point(30, 0), 10);

        assertFalse(c1.intersect(c2), "Circles too far apart (distance 30, radii sum 20)");
    }

    @Test
    void testRectangleIntersection() {
        Rectangle r1 = new Rectangle(new Point(0, 0), new Vector2D(20, 10), 0);
        Rectangle r2 = new Rectangle(new Point(5, 0), new Vector2D(20, 10), 0);

        assertTrue(r1.intersect(r2), "Overlapping rectangles should intersect");
    }

    @Test
    void testRectangleNoIntersection() {
        Rectangle r1 = new Rectangle(new Point(0, 0), new Vector2D(20, 10), 0);
        Rectangle r2 = new Rectangle(new Point(40, 0), new Vector2D(20, 10), 0);

        assertFalse(r1.intersect(r2), "Separated rectangles should not intersect");
    }

    @Test
    void testCircleIntersectsRectangleEdge() {
        Circle circle = new Circle(new Point(5, 0), 5);
        Rectangle rect = new Rectangle(new Point(10, 0), new Vector2D(10, 10), 0);

        assertTrue(circle.intersect(rect), "Circle touching rectangle edge should intersect");
    }

    @Test
    void testCircleDoesNotIntersectRectangle() {
        Circle circle = new Circle(new Point(0, 0), 5);
        Rectangle rect = new Rectangle(new Point(20, 0), new Vector2D(10, 10), 0);

        assertFalse(circle.intersect(rect), "Circle far from rectangle should not intersect");
    }
}
