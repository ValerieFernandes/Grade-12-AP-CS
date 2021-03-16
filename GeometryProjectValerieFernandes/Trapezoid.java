/**
 * Represents a Trapezoid shape
 */
public class Trapezoid extends Polygon {

    /**
     * Creates a Trapezoid with the given coordinates
     * @param xPoints the x values of the Trapezoid's coordinates
     * @param yPoints the y values of the Trapezoid's coordinates
     */
    Trapezoid(int [] xPoints, int[] yPoints) {
        super(xPoints, yPoints, 4);
    }

    /**
     * Returns the name Trapezoid
     * @return the name of the shape, Trapezoid
     */
    @Override
    public String getName(){
        return "Trapezoid";
    }

}
