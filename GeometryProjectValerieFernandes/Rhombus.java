/**
 * Represents a Rhombus shape
 * @author Valerie Fernandes
 */
public class Rhombus extends Parallelogram {

    /**
     * Creates a new Rhombus with the given coordinates
     * @param xPoints the x values of thr rhombus' coordinates
     * @param yPoints the y values of the rhombus' coordinates
     */
    Rhombus(int[] xPoints, int[] yPoints) {
        super(xPoints, yPoints);
    }

    /**
     * Returns the name Rhombus
     * @return the name of the shape, Rhombus
     */
    @Override
    public String getName(){
        return "Rhombus";
    }

    /**
     * Calculates the perimeter based on one side length and returns it
     * @return a double representing the perimeter of the Rhombus
     */
    @Override
    public double getPerimeter() {
        double perimeter = 0.0;

        perimeter = perimeter + Math.sqrt(Math.pow(this.getXPoints()[0] - this.getXPoints()[1], 2) + Math.pow(this.getYPoints()[0] - this.getYPoints()[1], 2));

        perimeter = perimeter * 4;
        return Math.round(perimeter * 100.0) / 100.0;
    }
}
