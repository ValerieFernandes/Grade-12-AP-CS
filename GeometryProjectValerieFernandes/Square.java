/**
 * Represents a Square shape
 * @author Valerie Fernandes
 */
public class Square extends Rectangle {

    /**
     * Creates a square with the given coordinates
     * @param xPoints the x value of the square's coordinates
     * @param yPoints the y value of the square's coordinates
     */
    public Square(int[] xPoints, int[] yPoints) {
        super(xPoints, yPoints);
    }

    /**
     * Returns the name Square
     * @return the name of the shape, Square
     */
    @Override
    public String getName(){
        return "Square";
    }

    /**
     * Calculates the area based on a side length and returns it
     * @return a double representing the area of the square
     */
    @Override
    public double getArea() {
        double area = 0.0;

        area = area + Math.sqrt(Math.pow(this.getXPoints()[0] - this.getXPoints()[1], 2) + Math.pow(this.getYPoints()[0] - this.getYPoints()[1], 2));

        area = Math.pow(area, 2);
        return Math.round(area * 100.0) / 100.0;
    }

    /**
     * Calculates the perimeter based on a side length and returns it
     * @return a double representing the area of the square
     */
    @Override
    public double getPerimeter() {
        double perimeter = 0.0;

        perimeter = perimeter + Math.sqrt(Math.pow(this.getXPoints()[0] - this.getXPoints()[1], 2) + Math.pow(this.getYPoints()[0] - this.getYPoints()[1], 2));

        perimeter = perimeter * 4;
        return Math.round(perimeter * 100.0) / 100.0;
    }
}
