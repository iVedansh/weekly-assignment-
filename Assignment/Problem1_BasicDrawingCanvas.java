abstract class Shape {
    private static int counter = 0;
    private final String shapeId;
    protected Shape() { shapeId = "SH-" + (++counter); }
    public abstract double calculateArea();
    public void scale(double factor) { scale(factor, factor); }
    public void scale(double xFactor, double yFactor) {
        double factor = (xFactor + yFactor) / 2.0;
        scaleDimension(factor);
    }
    protected abstract void scaleDimension(double factor);
    public String getShapeId() { return shapeId; }
    public static void printArea(Shape s) { System.out.println(s.calculateArea()); }
}
class CircleShape extends Shape {
    private double radius;
    public CircleShape(double radius) {
        if (radius <= 0) throw new IllegalArgumentException("Radius must be positive");
        this.radius = radius;
    }
    public double calculateArea() { return Math.PI * radius * radius; }
    protected void scaleDimension(double factor) {
        if (factor <= 0) throw new IllegalArgumentException("Scale factor must be positive");
        radius *= factor;
    }
}
class SquareShape extends Shape {
    private double side;
    public SquareShape(double side) {
        if (side <= 0) throw new IllegalArgumentException("Side must be positive");
        this.side = side;
    }
    public double calculateArea() { return side * side; }
    protected void scaleDimension(double factor) {
        if (factor <= 0) throw new IllegalArgumentException("Scale factor must be positive");
        side *= factor;
    }
}
public class Problem1_BasicDrawingCanvas {
    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);
        SquareShape sq = new SquareShape(4.0);
        System.out.printf("Circle area: %.2f%n", c.calculateArea());
        System.out.println("Square area: " + sq.calculateArea());
        sq.scale(2.0);
        System.out.println("Scaled square area: " + sq.calculateArea());
        System.out.print("Circle through Shape reference: ");
        Shape.printArea(c);
    }
}