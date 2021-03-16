import java.lang.Math;

/**
 * Represents a polygon, which is a shape with multiple straight edges
 * @author Valerie Fernandes
 */
public abstract class Polygon extends Shape{
    /**
     * the x coordinates of the Polygon's points
     */
    private int[] xPoints;

    /**
     * The y coordinates of the Polygon's points
     */
    private int[] yPoints;

    /**
     * The number of points the Polygon contains
     */
    private int nPoints;

    /**
     * Creates a new polygon with the given points at the given coordinates
     * @param xPoints the x values of the Polygon's coordinates
     * @param yPoints the y values of the Polygon's coordinates
     * @param nPoints the number of points the Polygon contains
     */
    Polygon(int[] xPoints, int[] yPoints, int nPoints){
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.nPoints = nPoints;
    }

    /**
     * Gets and returns the x values of the Polygon
     * @return an int array representing the x values of the Polygon
     */
    public int[] getXPoints() {
        return this.xPoints;
    }

    /**
     * Gets and returns the y values of the Polygon
     * @return an int array representing the y values of the Polygon
     */
    public int[] getYPoints() {
        return this.yPoints;
    }

    /**
     * Gets and returns the number of points in the Polygon
     * @return an int representing the number of points in the Polygon
     */
    public int getNPoints() {
        return this.nPoints;
    }

    /**
     * Calculates and returns the area of the Polygon
     * @return a double representing the area of the Polygon
     */
    @Override
    public double getArea() {
        double area = 0.0;
        for (int i=0; i < this.nPoints; i++){
            if(i == nPoints - 1){
                area = area + this.xPoints[i] * this.yPoints[0];
                area = area - this.yPoints[i] * this.xPoints[0];
            }else {
                area = area + this.xPoints[i] * this.yPoints[i + 1];
                area = area - this.yPoints[i] * this.xPoints[i + 1];
            }
        }
        area = Math.abs(area / 2.0);
        return Math.round(area * 100.0) / 100.0;
    }

    /**
     * Calculates and returns the perimeter of the Polygon
     * @return a double representing the perimeter of the Polygon
     */
    @Override
    public double getPerimeter() {
        double perimeter = 0.0;
        for (int i=0; i < this.nPoints ; i++) {
            if (i == this.nPoints - 1) {
                perimeter = perimeter + Math.sqrt(Math.pow(this.xPoints[i] - this.xPoints[0], 2) + Math.pow(this.yPoints[i] - this.yPoints[0], 2));
            }else{
                perimeter = perimeter + Math.sqrt(Math.pow(this.xPoints[i] - this.xPoints[i + 1], 2) + Math.pow(this.yPoints[i] - this.yPoints[i + 1], 2));
            }
        }
        return Math.round(perimeter * 100.0) / 100.0;
    }

    /**
     * Shifts the polygon horizontally by the number of units specified
     * @param shift the amount of units the Polygon is to be shifted
     */
    @Override
    public void shiftHorizontally(int shift){
        for (int i = 0; i < this.xPoints.length; i++) {
            this.xPoints[i] = this.xPoints[i] + shift;
        }
    }

    /**
     * Shifts the polygon vertically by the number of units specified
     * @param shift the amount of units the Polygon is to be shifted
     */
    @Override
    public void shiftVertically(int shift){
        for (int i = 0; i < this.yPoints.length; i++) {
            this.yPoints[i] = this.yPoints[i] + shift;
        }
    }
}
