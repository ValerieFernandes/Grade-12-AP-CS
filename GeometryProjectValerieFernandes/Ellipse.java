import java.awt.Point;
import java.lang.Math;

/**
 * Creates a new ellipse shape
 * @author Valerie Fernandes
 */
public class Ellipse extends Shape{

    /**
     * the length of the horizontal radius of the Ellipse
     */
    private int horizontalRadius;

    /**
     * the length of the vertical radius of the Ellipse
     */
    private int verticalRadius;

    /**
     * a Point representing the centre of the Ellipse
     */
    private Point centre;

    /**
     * Creates a new Ellipse with the given radii and centre
     * @param horizontalRadius the length of the horizontal radius
     * @param verticalRadius the length of the vertical radius
     * @param centre the coordinates of the centre
     */
     Ellipse(int horizontalRadius, int verticalRadius, Point centre) {
        super();
        this.horizontalRadius = horizontalRadius;
        this.verticalRadius = verticalRadius;
        this.centre = centre;
    }

    /**
     * Gets and returns the horizontal radius
     * @return the length of the horizontal radius
     */
    public int getHorizontalRadius() {
        return this.horizontalRadius;
    }

    /**
     * Gets and returns the vertical radius
     * @return the length of the vertical radius
     */
    public int getVerticalRadius() {
        return this.verticalRadius;
    }

    /**
     * Gets and returns the centre of the Ellipse
     * @return the coordinates of the centre of the Ellipse
     */
    public Point getCentre() {
        return this.centre;
    }

    /**
     * Returns the name Ellipse
     * @return the name of the shape, Ellipse
     */
    @Override
    public String getName(){
         return "Ellipse";
    }

    /**
     * Calculates and returns the area of the Ellipse
     * @return a double representing the area of the Ellipse
     */
    @Override
    public double getArea() {
         double area = Math.PI * this.horizontalRadius * this.verticalRadius;
         return Math.round(area * 100.0) / 100.0;
    }

    /**
     * Calculates and returns the perimeter of the Ellipse
     * @return a double representing the perimeter of the Ellipse
     */
    @Override
    public double getPerimeter() {
        double perimeter = 3 * (this.horizontalRadius + this.verticalRadius);
        perimeter = perimeter -  Math.sqrt((3 * this.horizontalRadius + this.verticalRadius) * (3 * this.verticalRadius + this.horizontalRadius));
        perimeter = perimeter * Math.PI;
        return Math.round(perimeter * 100.0) / 100.0;
    }

    /**
     * Shifts the Ellipse horizontally by the number of units specified
     * @param shift an int representing the number of units to shift the Ellipse
     */
    @Override
    public void shiftHorizontally(int shift){
        this.centre.x = this.centre.x + shift;
    }

    /**
     * Shifts the Ellipse vertically by the number of units specified
     * @param shift an int representing the number of units to shift the Ellipse
     */
    @Override
    public void shiftVertically(int shift){
        this.centre.y = this.centre.y + shift;
    }
}
