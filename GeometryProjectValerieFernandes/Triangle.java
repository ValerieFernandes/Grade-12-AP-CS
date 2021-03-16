/**
 * Represents a Triangle shape
 * @author Valerie Fernandes
 */
public class Triangle extends Polygon {

    /**
     * Creates a Triangle with the given coordinates
     * @param xPoints the x values of the Triangle's points
     * @param yPoints the y values of the Triangle's points
     */
    Triangle(int[] xPoints, int[] yPoints) {
        super(xPoints, yPoints, 3);
    }

    /**
     * Returns the name Triangle
     * @return the name of the shape, triangle
     */
    @Override
    public String getName() {
        return "Triangle";
    }
}
