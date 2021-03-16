/**
 * a custom error for shapes which are not valid polygons
 */
public class InvalidPolygonException extends Exception{
    /**
     * The name of the shape type
     */
    private String shape;

    /**
     * Creates a new erroe with the given shape name
     * @param shape, the name of the shape based on its' properties
     */
    public InvalidPolygonException(String shape){
        this.shape = shape;
    }

    /**
     * Gets and returns the name of the shape
     * @return the type of shape which the error applies to
     */
    public String getShape(){
        return shape;
    }
}
