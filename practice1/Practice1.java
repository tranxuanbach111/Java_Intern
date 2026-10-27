package practice1;

class Shape {
    protected double width;
    protected double height;

    public Shape(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void showInfo() {
        System.out.println("Shape: width = " + width + ", height = " + height);
    }
}

class Rectangle extends Shape {
    public Rectangle(double width, double height) {
        super(width, height);
    }

    public double getArea() {
        return width * height;
    }

    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public void showInfo() {
        System.out.println("Rectangle: w = " + width + ", h = " + height
                + ", area = " + getArea() + ", perimeter = " + getPerimeter());
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        super(radius * 2, radius * 2);
        this.radius = radius;
    }

    public double getArea() {
        return 3.14 * radius * radius;
    }

    public double getCircumference() {
        return 2 * radius * 3.14;
    }

    @Override
    public void showInfo() {
        System.out.println("Circle: r = " + radius + ", w = " + width
                + ", h = " + height + ", area = " + getArea()
                + ", circumference = " + getCircumference());
    }
}

public class Practice1 {
    public static void main(String[] args) {
        Shape shape = new Shape(4, 5);
        Rectangle rectangle = new Rectangle(4, 5);
        Circle circle = new Circle(3);

        shape.showInfo();
        rectangle.showInfo();
        circle.showInfo();
    }
}
