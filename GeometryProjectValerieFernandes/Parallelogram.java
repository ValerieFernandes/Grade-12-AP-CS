/**
 * Represents a parallelogram shape using coordinates
 * @author Valerie Fernandes
 */
public class Parallelogram extends Trapezoid{

    /**
     * Creates a new Parallelogram with the given coordinates
     * @param xPoints an array representing all the x values of the parallelogram's points
     * @param yPoints an array representing all the y values of the parallelogram's points
     */
    Parallelogram(int [] xPoints, int[] yPoints) {
        super(xPoints, yPoints);
    }

    /**
     * Gets and returns the name Parallelogram
     * @return the name parallelogram
     */
    @Override
    public String getName(){
        return "Parallelogram";
    }

    /**
     * Calculates and returns the perimeter of the Parallelogram
     * @return the perimeter of the Parallelogram
     */
    @Override
    public double getPerimeter() {
        double perimeter = 0.0;
        for (int i=0; i < 2 ; i++) {
            perimeter = perimeter + Math.sqrt(Math.pow(this.getXPoints()[i] - this.getXPoints()[i + 1], 2) + Math.pow(this.getYPoints()[i] - this.getYPoints()[i + 1], 2));
        }
        perimeter = perimeter * 2;
        return Math.round(perimeter * 100.0) / 100.0;
    }
}
