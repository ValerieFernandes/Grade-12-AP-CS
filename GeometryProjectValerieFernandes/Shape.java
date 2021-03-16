/**
 * Represents a two dimensional Shape
 * @author Valerie Fernandes
 */
public abstract class Shape {

    /**
     * Creates a shape object
     */
    Shape(){}

    /**
     * Returns the name of the shape
     * @return a String the name of the shape
     */
    abstract String getName();

    /**
     * Calculates and returns the area of the Shape
     * @return a double representing the area of the shape
     */
    abstract double getArea();

    /**
     * Calculates and returns the perimeter of the Shape
     * @return a double representing the perimeter of the shape
     */
    abstract double getPerimeter();

    /**
     * Shifts the Shape horizontally by the number of units specified
     * @param shift the number of units the shape is translated
     */
    abstract void shiftHorizontally(int shift);

    /**
     * Shifts the Shape vertically by the number of units specified
     * @param shift the number of units the shape is translated
     */
    abstract void shiftVertically(int shift);
}
