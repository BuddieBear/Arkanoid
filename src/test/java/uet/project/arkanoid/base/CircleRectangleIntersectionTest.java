package uet.project.arkanoid.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleRectangleIntersectionTest {

    /* BASIC EDGE TOUCH */
    @Test
    void testCircleTouchesRectangleEdge() {
        Circle circle = new Circle(new Point(5, 0), 5);
        Rectangle rect = new Rectangle(new Point(10, 0), new Vector2D(10, 10), 0);

        assertTrue(circle.intersect(rect), "Circle just touching rectangle edge should intersect");
    }

    /* CLEARLY SEPARATED */
    @Test
    void testCircleFarFromRectangle() {
        Circle circle = new Circle(new Point(-50, 0), 5);
        Rectangle rect = new Rectangle(new Point(10, 0), new Vector2D(10, 10), 0);

        assertFalse(circle.intersect(rect), "Circle far from rectangle should not intersect");
    }

    /* CIRCLE OVERLAPS CENTER */
    @Test
    void testCircleCoversRectangleCenter() {
        Circle circle = new Circle(new Point(10, 0), 15);
        Rectangle rect = new Rectangle(new Point(10, 0), new Vector2D(10, 10), 0);

        assertTrue(circle.intersect(rect), "Large circle fully covering rectangle should intersect");
    }

    /* CORNER TOUCH */
    @Test
    void testCircleTouchesRectangleCorner() {
        Circle circle = new Circle(new Point(0, 0), 5);
        Rectangle rect = new Rectangle(new Point(10, 10), new Vector2D(10, 10), 0);

        // The corner of the rectangle is at (5, 5), distance sqrt(5² + 5²) = ~7.07 > 5
        assertFalse(circle.intersect(rect), "Circle not reaching the corner should not intersect");

        // Move slightly closer
        circle = new Circle(new Point(3, 3), 5);
        assertTrue(circle.intersect(rect), "Circle close enough to rectangle corner should intersect");
    }

    /* CIRCLE INSIDE RECTANGLE */
    @Test
    void testCircleInsideRectangle() {
        Circle circle = new Circle(new Point(10, 0), 3);
        Rectangle rect = new Rectangle(new Point(10, 0), new Vector2D(20, 10), 0);

        assertTrue(circle.intersect(rect), "Circle completely inside rectangle should intersect");
    }

    /* RECTANGLE INSIDE CIRCLE */
    @Test
    void testRectangleInsideCircle() {
        Circle circle = new Circle(new Point(0, 0), 50);
        Rectangle rect = new Rectangle(new Point(0, 0), new Vector2D(10, 10), 0);

        assertTrue(circle.intersect(rect), "Small rectangle inside circle should intersect");
    }

    /* ROTATED RECTANGLE */
    @Test
    void testCircleIntersectsRotatedRectangle() {
        Circle circle = new Circle(new Point(5, 0), 5);
        Rectangle rect = new Rectangle(new Point(10, 0), new Vector2D(10, 10), 45);

        assertTrue(circle.intersect(rect), "Circle should still intersect rotated rectangle near edge");
    }

    @Test
    void testCircleDoesNotIntersectRotatedRectangle() {
        Circle circle = new Circle(new Point(-20, -20), 3);
        Rectangle rect = new Rectangle(new Point(10, 0), new Vector2D(10, 10), 45);

        assertFalse(circle.intersect(rect), "Rotated rectangle far from circle should not intersect");
    }

    /* EDGE CASE: EXACT CORNER CONTACT */
    @Test
    void testCircleExactlyTouchesRectangleCorner() {
        Circle circle = new Circle(new Point(0, 0), Math.sqrt(2) * 5);
        Rectangle rect = new Rectangle(new Point(5, 5), new Vector2D(10, 10), 0);

        assertTrue(circle.intersect(rect), "Circle exactly touching rectangle corner should intersect");
    }

    /* EDGE CASE: SMALL GAP */
    @Test
    void testCircleNearlyTouchesRectangleEdge() {
        Circle circle = new Circle(new Point(0, 0), 4.9);
        Rectangle rect = new Rectangle(new Point(10, 0), new Vector2D(10, 10), 0);

        assertFalse(circle.intersect(rect), "Tiny gap between circle and rectangle should not count as intersecting");
    }
}
