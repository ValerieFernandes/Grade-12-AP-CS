/**
 * Creates a rectangle shape
 * @author Valerie Fernandes
 */
public class Rectangle extends Parallelogram {

    /**
     * Creates a new rectangle with the given coordinates
     * @param xPoints the x coordinates of the rectangle
     * @param yPoints the y coordinates of the rectangle
     */
    Rectangle(int[] xPoints, int[] yPoints) {
        super(xPoints, yPoints);
    }

    /**
     * Gets and returns the name rectangle
     * @return the name of the shape, rectangle
     */
    @Override
    public String getName(){
        return "Rectangle";
    }

    /**
     * Calculates and returns the area of the Rectangle
     * @return a double representing the area of the Rectangle
     */
    @Override
    public double getArea() {
        double area = 0.0;
        area = area + Math.sqrt(Math.pow(this.getXPoints()[0] - this.getXPoints()[1], 2) + Math.pow(this.getYPoints()[0] - this.getYPoints()[1], 2));
        area = area * Math.sqrt(Math.pow(this.getXPoints()[1] - this.getXPoints()[2], 2) + Math.pow(this.getYPoints()[1] - this.getYPoints()[2], 2));

        return Math.round(area * 100.0) / 100.0;
    }
}
