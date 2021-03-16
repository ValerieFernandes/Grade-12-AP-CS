import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.File;
import java.lang.Math;

/**
 * A program to draw and apply functions to different shapes
 */
class GeometryDrawingProgram {

    /**
     * the frame on which the Shapes are drawn
     */
    private static JFrame frame;

    /**
     * the Arraylist containing all the shapes used in the program
     */
    private static ArrayList<Shape> shapes = new ArrayList<Shape>();

    /**
     * A color used to draw all of the shapes
     */
    private static Color myCol = new Color(152, 212, 217);

    public static void main(String[] args) {
        GeometryScreen gs = new GeometryScreen();
        Scanner input = new Scanner(System.in);
        int choice;
        int shape;
        int shift;
        String fileName;

        int red; // Variables for making new colours
        int green;
        int blue;

        int rOne; // Variables for creating new circles and ellipses
        int rTwo;
        int xCoord;
        int yCoord;

        System.out.println("Welcome to the Geometry Drawing Program");

        do {
            System.out.println("Please select an option by choosing an the number of an option from the menu below:"
                                + "\n1. Display all shapes to console"
                                + "\n2. Add a shape"
                                + "\n3. Remove a shape"
                                + "\n4. Translate a shape"
                                + "\n5. Translate the entire drawing"
                                + "\n6. Save drawing to a file"
                                + "\n7. Load drawing from a file"
                                + "\n8. Change colour of the shapes"
                                + "\n9. Quit");
            choice = input.nextInt();

            if (choice == 1){ // Display shapes to console
                printShapeInfo();

            }else if (choice == 2){ // Add a shape
                System.out.println("What type of shape, select a number, would you like to add (1. Circle, 2. Ellipse, 3. Triangle, 4. Trapezoid, 5. Parallelogram, 6. Rhombus, 7. Rectangle, or 8. Square)?");
                shape = input.nextInt();

                if(shape == 1){ // Get information and add a new circle

                    try {
                        Point centre;
                        System.out.println("What is the integer radius of the circle?");
                        rOne = input.nextInt();
                        checkRadius(rOne);
                        System.out.println("What is the x-coordinate of the centre of the circle?");
                        xCoord = input.nextInt();
                        System.out.println("What is the y-coordinate of the centre of the circle?");
                        yCoord = input.nextInt();
                        centre = new Point(xCoord, yCoord);
                        shapes.add(new Circle(rOne, centre));

                    }catch (InvalidRadiusException e){
                        System.out.println("Cannot make a shape with a radius <= 0");
                    }

                }else if (shape == 2) { // Get information and add a new ellipse
                    try {
                        Point centre;
                        System.out.println("What is the integer horizontal radius of the ellipse?");
                        rOne = input.nextInt();
                        checkRadius(rOne);
                        System.out.println("What is the integer vertical radius of the ellipse?");
                        rTwo = input.nextInt();
                        checkRadius(rTwo);
                        System.out.println("What is the x-coordinate of the centre of the ellipse?");
                        xCoord = input.nextInt();
                        System.out.println("What is the y-coordinate of the centre of the ellipse?");
                        yCoord = input.nextInt();
                        centre = new Point(xCoord, yCoord);
                        shapes.add(new Ellipse(rOne, rTwo, centre));
                    } catch (InvalidRadiusException e) {
                        System.out.println("Cannot make a shape with a radius <= 0");
                    }

                }else if (shape == 3) { // Get information and add a new triangle
                    try{
                        int[] triangleX = new int[3]; // variables for creating new triangles
                        int[] triangleY = new int[3];
                        System.out.println("Please list vertices in clockwise order, the shape can have any orientation");
                        for(int i=0; i<3; i++){
                            System.out.println("What is the x-coordinate of vertex " + (i + 1) + "?");
                            triangleX[i] = input.nextInt();
                            System.out.println("What is the y-coordinate of vertex " + (i + 1) + "?");
                            triangleY[i] = input.nextInt();
                        }
                        checkPolygon(triangleX, triangleY, "Triangle");
                        shapes.add(new Triangle(triangleX, triangleY));
                    }catch (InvalidPolygonException e){
                        System.out.println("Shape not created, those points do not make a " + e.getShape());
                    }

                }else if ((shape >= 4) && (shape <= 8)) { // Get the four vertices if shape is a quadrilateral
                     int[] quadX = new int[4]; // variables for creating new quadrilaterals
                     int[] quadY = new int[4];

                     System.out.println("Please list vertices in clockwise order, the shape can have any orientation");
                     for (int i = 0; i < 4; i++) {
                         System.out.println("What is the x-coordinate of vertex " + (i + 1) + "?");
                         quadX[i] = input.nextInt();
                         System.out.println("What is the y-coordinate of vertex " + (i + 1) + "?");
                         quadY[i] = input.nextInt();
                     }

                     if (shape == 4) { //add a new trapezoid
                         try{
                             checkPolygon(quadX, quadY, "Trapezoid");
                             shapes.add(new Trapezoid(quadX, quadY));
                         }catch (InvalidPolygonException e){
                             System.out.println("Shape not created, those points do not make a " + e.getShape());
                         }
                     } else if (shape == 5) { //add a new parallelogram
                         try{
                             checkPolygon(quadX, quadY, "Parallelogram");
                             shapes.add(new Parallelogram(quadX, quadY));
                         }catch (InvalidPolygonException e){
                             System.out.println("Shape not created, those points do not make a " + e.getShape());
                         }
                     } else if (shape == 6) { //add a new rhombus
                         try{
                             checkPolygon(quadX, quadY, "Rhombus");
                             shapes.add(new Rhombus(quadX, quadY));
                         }catch (InvalidPolygonException e){
                             System.out.println("Shape not created, those points do not make a " + e.getShape());
                         }
                     } else if (shape == 7) { //add a new rectangle
                         try{
                             checkPolygon(quadX, quadY, "Rectangle");
                             shapes.add(new Rectangle(quadX, quadY));
                         }catch (InvalidPolygonException e){
                             System.out.println("Shape not created, those points do not make a " + e.getShape());
                         }
                     } else if (shape == 8) { //add a new square
                         try{
                             checkPolygon(quadX, quadY, "Square");
                             shapes.add(new Square(quadX, quadY));
                         }catch (InvalidPolygonException e){
                             System.out.println("Shape not created, those points do not make a " + e.getShape());
                         }
                     }

                }else{
                    System.out.println("Sorry that is not a valid shape type");
                }

            }else if (choice == 3){ // Delete a shape
                System.out.println("Which shape would you like to delete?");
                System.out.println("");
                printShapeInfo();
                shape = input.nextInt();

                if (shape <= shapes.size()){ // If a valid shape is chosen delete it from the ArrayList
                    shapes.remove(shape - 1);

                }else{
                    System.out.println("Sorry, that is not a valid shape index");
                }

            }else if (choice == 4){ // Translate a shape
                System.out.println("Which shape would you like to translate?");
                System.out.println("");
                printShapeInfo();
                shape = input.nextInt();

                if (shape <= shapes.size()){ // If a valid shape is chosen shift it appropriately
                    System.out.println("How many units would you like to translate it horizontally?");
                    shift = input.nextInt();
                    shapes.get(shape - 1).shiftHorizontally(shift);

                    System.out.println("How many units would you like to translate it vertically?");
                    shift = input.nextInt();
                    shapes.get(shape - 1).shiftVertically(shift);
                }else{
                    System.out.println("Sorry, that is not a valid shape index");
                }

            }else if (choice == 5){ // Translate Drawing
                System.out.println("How many units would you like to translate the drawing horizontally?");
                shift = input.nextInt();
                for (int i = 0; i < shapes.size(); i++){
                    shapes.get(i).shiftHorizontally(shift);
                }

                System.out.println("How many units would you like to translate the drawing vertically?");
                shift = input.nextInt();
                for (int i = 0; i < shapes.size(); i++){
                    shapes.get(i).shiftVertically(shift);
                }

            }else if (choice == 6){ // Save data into a file
                System.out.println("What would you like to name your file (remember to end it in .txt)");
                input.nextLine();
                fileName = input.nextLine();
                try {
                    writeDataToFile(fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if (choice == 7){ // Read data from file
                System.out.println("What is the file path (including file name) of the file you would like to load?");
                input.nextLine();
                fileName = input.nextLine();
                try {
                    loadFile(fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (choice == 8){ // Get new color for the shapes
                System.out.println("You will need an RGB value for your new color");
                System.out.println("What is your red value (0-255)");
                red = input.nextInt();
                System.out.println("What is your green value (0-255)");
                green = input.nextInt();
                System.out.println("What is your blue value (0-255)");
                blue = input.nextInt();
                myCol = new Color(red, green, blue);

            }else{
                System.out.println("Thank you for using this program");
                break;
            }

            frame.repaint(); //update the screen
        }while(true);

    }

    /**
     * Prints all of the shapes and their information (area, perimeter, coordinates) to the console
     */
    public static void printShapeInfo(){
        String coordinates;

        for(int i = 0; i < shapes.size(); i++){

            if (shapes.get(i) instanceof Ellipse) { // Print the information for ellipses and circles
                System.out.println((i + 1) + ". " + shapes.get(i).getName() + ": Area = " + shapes.get(i).getArea() + ", Perimeter = " + shapes.get(i).getPerimeter()
                                    + ", Centre = (" + ((Ellipse) shapes.get(i)).getCentre().x + ", "  + ((Ellipse) shapes.get(i)).getCentre().y + ")");

            }else{
                coordinates =  "(";
                for (int j = 0; j < ((Polygon)shapes.get(i)).getXPoints().length; j++){ // Make a string of the polygon's coordinates
                    coordinates = coordinates + "(" + ((Polygon)shapes.get(i)).getXPoints()[j] + ", " + ((Polygon)shapes.get(i)).getYPoints()[j] + ") ";
                }
                coordinates = coordinates + ")";
                System.out.println((i + 1) + ". " + shapes.get(i).getName() + ": Area = " + shapes.get(i).getArea() + ", Perimeter = " + shapes.get(i).getPerimeter()
                                    + ", Coordinates = " + coordinates);
            }
        }
    }

    /**
     * Writes and saves all of the data of the shapes into a text file
     * @param name the name of the file which the data will be written to
     * @throws FileNotFoundException
     */
    public static void writeDataToFile(String name) throws FileNotFoundException {
        File shapeData = new File(name);
        PrintWriter output = null;
        output = new PrintWriter(shapeData);


        for(int i=0; i<shapes.size(); i++){
            if(shapes.get(i) instanceof Circle){
                output.println("Circle");
                output.println(((Circle)shapes.get(i)).getHorizontalRadius());
                output.println(((Circle)shapes.get(i)).getCentre().x);
                output.println(((Circle)shapes.get(i)).getCentre().y);
            }else if(shapes.get(i) instanceof Ellipse) {
                output.println("Ellipse");
                output.println(((Ellipse) shapes.get(i)).getHorizontalRadius());
                output.println(((Ellipse) shapes.get(i)).getVerticalRadius());
                output.println(((Ellipse) shapes.get(i)).getCentre().x);
                output.println(((Ellipse) shapes.get(i)).getCentre().y);
            }else if(shapes.get(i) instanceof Triangle) {
                output.println("Triangle");
                for(int j=0; j<3; j++){
                    output.println(((Triangle) shapes.get(i)).getXPoints()[j]);
                    output.println(((Triangle) shapes.get(i)).getYPoints()[j]);
                }
            }else{
                output.println(shapes.get(i).getName());
                for(int j=0; j<4; j++) {
                    output.println(((Parallelogram) shapes.get(i)).getXPoints()[j]);
                    output.println(((Parallelogram) shapes.get(i)).getYPoints()[j]);
                }
            }
        }
        output.close();
    }

    /**
     * Reads in a text file containing shape data and adds the new shapes to the drawing
     * @param path the file name including the path to be opened
     * @throws FileNotFoundException
     */
    public static void loadFile(String path) throws FileNotFoundException {
        String type;
        int rOne; // Variables for creating new circles and ellipses
        int rTwo;
        int xCoord;
        int yCoord;
        Point centre;

        int[] triangleX = new int[3]; // variables for creating new triangles
        int[] triangleY = new int[3];

        int[] quadX = new int[4]; // variables for creating new quadrilaterals
        int[] quadY = new int[4];


        File shapeData = new File(path);
        Scanner input =  new Scanner(shapeData);

        while(input.hasNextLine()){
            type = input.nextLine();

            if(type.equals("Circle")){ // Get information and add a new circle

                rOne = input.nextInt();
                xCoord = input.nextInt();
                yCoord = input.nextInt();
                if(input.hasNextLine()) {
                    input.nextLine();
                }

                centre = new Point(xCoord, yCoord);
                shapes.add(new Circle(rOne, centre));

            }else if (type.equals("Ellipse")){ // Get information and add a new ellipse
                rOne = input.nextInt();
                rTwo = input.nextInt();
                xCoord = input.nextInt();
                yCoord = input.nextInt();
                if(input.hasNextLine()) {
                    input.nextLine();
                }

                centre = new Point(xCoord, yCoord);
                shapes.add(new Ellipse(rOne, rTwo, centre));

            }else if (type.equals("Triangle")) { // Get information and add a new triangle
                for(int i=0; i<3; i++){
                    triangleX[i] = input.nextInt();
                    triangleY[i] = input.nextInt();
                }
                if(input.hasNextLine()) {
                    input.nextLine();
                }

                shapes.add(new Triangle(triangleX, triangleY));

            }else { // Get the four vertices if shape is a quadrilateral
                for (int i = 0; i < 4; i++) {
                    quadX[i] = input.nextInt();
                    quadY[i] = input.nextInt();
                }
                if(input.hasNextLine()) {
                    input.nextLine();
                }

                if (type.equals("Trapezoid")) { //add a new trapezoid
                    shapes.add(new Trapezoid(quadX, quadY));
                } else if (type.equals("Parallelogram")) { //add a new parallelogram
                    shapes.add(new Parallelogram(quadX, quadY));
                } else if (type.equals("Rhombus")) { //add a new rhombus
                    shapes.add(new Rhombus(quadX, quadY));
                } else if (type.equals("Rectangle")) { //add a new rectangle
                    shapes.add(new Rectangle(quadX, quadY));
                } else if (type.equals("Square")) { //add a new square
                    shapes.add(new Square(quadX, quadY));
                }
            }
        }
        input.close();
    }

    /**
     * Checks if an integer is a valid radius
     * @param radius the integer which is being tested
     * @throws InvalidRadiusException
     */
    public static void checkRadius(int radius) throws InvalidRadiusException{
        if(radius <= 0){
            throw new InvalidRadiusException();
        }
    }

    /**
     * Check if coordinates would make a line and therefore would be an invalid polygon
     * @param xCoords the x values of the polygon's coordinates
     * @param yCoords the y values of the the polygon's coordinates
     * @param type the type of polygon which is being checked
     * @throws InvalidPolygonException
     */
    public static void checkPolygon(int [] xCoords, int[] yCoords, String type) throws InvalidPolygonException {
        double[] slope = new double[2];

        if (type.equals("Triangle")) { // If lines to both points hae same slope then the "triangle" is a line"
            slope[0] = (xCoords[1] - xCoords[0]) / ((yCoords[1] - yCoords[0]) * 1.0);
            slope[1] = (xCoords[2] - xCoords[0]) / ((yCoords[2] - yCoords[0]) * 1.0);

            if(slope[0] == slope[1]){
                throw new InvalidPolygonException(type);
            }

        }else{
            if(isQuadLine(xCoords, yCoords)) { // Make sure no shape has three points on same line
                throw new InvalidPolygonException(type);
            }
        }
    }

    /**
     * Checks if a quadrilateral has three points in a row making a line and is therefore invalid
     * @param xCoords the x values of the quadrilateral coordinates
     * @param yCoords the y values of the quadrilateral's coordinates
     * @return  a boolean, true if the quadrilateral is actually a line
     */
    public static boolean isQuadLine(int[] xCoords,int[] yCoords){
        double[] slope = new double[2];
        slope[0] = (xCoords[0] - xCoords[1]) / ((yCoords[0] - yCoords[1]) * 1.0); //if vertices 1, 2, 3 are a line
        slope[1] = (xCoords[2] - xCoords[1]) / ((yCoords[2] - yCoords[1]) * 1.0);
        if(slope[0] == slope[1]){
            return true;
        }

        slope[0] = (xCoords[1] - xCoords[2]) / ((yCoords[1] - yCoords[2]) * 1.0); //if vertices 2, 3, 4 are a line
        slope[1] = (xCoords[3] - xCoords[2]) / ((yCoords[3] - yCoords[2]) * 1.0);
        if(slope[0] == slope[1]){
            return true;
        }
        return false;
    }

    /**
     * Represents a screen which can contain a graphic panel
     * @author Mangat
     */
    public static class GeometryScreen {

        /**
         * Creates a new Geometry Screen with a graphics panel to display the shapes
         */
        GeometryScreen() {
            frame = new JFrame("Geometry Drawing Program 1.0");

            //Create a new "custom" panel for graphics based on the inner class below
            JPanel graphicsPanel = new GraphicsPanel();

            //Add the panel and the frame to the window
            frame.getContentPane().add(graphicsPanel);

            // Set the frame to full screen
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(520,540);
            frame.setUndecorated(false);  //Set to true to remove title bar
            frame.setVisible(true);

        }

        /**
         * Represents a Graphic panel on which shapes are drawn
         * @author Mangat and Valerie Fernandes
         */
        public static class GraphicsPanel extends JPanel {

            /**
             * the x values of a triangle's coordinates shifted to the axis
             */
            private int[] triangleX = new int[3];

            /**
             * the y values of a triangle's coordinates shifted to the axis
             */
            private int[] triangleY = new int[3];

            /**
             * the x values of a quadrilateral coordinates shifted to the axis
             */
            private int[] quadX = new int[4];

            /**
             * the y values of a quadrilateral coordinates shifted to the axis
             */
            private int[] quadY = new int[4];

            /**
             * Draws the shapes from the Arraylist onto the graphics panel
             * @param g the Graphic to be drawn on
             */
            public void paintComponent(Graphics g) {
                setDoubleBuffered(true);
                g.setColor(Color.BLACK);

                //draw the X/Y Axis
                g.drawLine(250, 0, 250, 500);
                g.drawLine(0, 250, 500, 250);

                g.setColor(myCol);
                for(int i=0; i<shapes.size(); i++){
                    if (shapes.get(i) instanceof Ellipse){
                        g.drawOval(250 + ((Ellipse)shapes.get(i)).getCentre().x,250 + (((Ellipse)shapes.get(i)).getCentre().y * -1), ((Ellipse)shapes.get(i)).getHorizontalRadius(),((Ellipse)shapes.get(i)).getVerticalRadius());

                    }else if (shapes.get(i) instanceof Triangle){
                        for(int j=0; j<3; j++){
                            triangleX[j] = ((Polygon)shapes.get(i)).getXPoints()[j] + 250;
                            triangleY[j] = (((Polygon)shapes.get(i)).getYPoints()[j] * -1) + 250;
                        }
                        g.drawPolygon(triangleX, triangleY, 3);

                    }else{
                        for(int j=0; j<4; j++){
                            quadX[j] = ((Polygon)shapes.get(i)).getXPoints()[j] + 250;
                            quadY[j] = (((Polygon)shapes.get(i)).getYPoints()[j] * -1) + 250;
                        }
                        g.drawPolygon(quadX, quadY, 4);
                    }
                }

            }
        }
    }
}