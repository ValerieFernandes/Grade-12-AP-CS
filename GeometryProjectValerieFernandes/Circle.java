import java.awt.Point;
import java.lang.Math;

/**
 * Represents a circle shape
 * @author Valerie Fernandes
 */
public class Circle extends Ellipse{

    /**
     * Creates a ne Circle with the given coordinates and radius
     * @param radius the radius of the circle
     * @param centre the coordinates of the centre of the circle
     */
    Circle(int radius, Point centre){
        super(radius, radius, centre);
    }

    /**
     * Returns the name Circle
     * @return the name of the shape, Circle
     */
    @Override
    public String getName(){
        return "Circle";
    }

    /**
     * Returns the perimeter of the circle using pi and the radius
     * @return a double representing the perimeter of the circle
     */
    @Override
    public double getPerimeter() {
        double perimeter = Math.PI * (2 * this.getHorizontalRadius());
        return Math.round(perimeter * 100.0) / 100.0;
    }

}
