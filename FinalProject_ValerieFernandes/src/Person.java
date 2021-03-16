import java.util.ArrayList;

/** Person
 * represents a person object, and the courses to which it belongs
 * @author Valerie Fernandes
 */
abstract public class Person {

    /** represents the persons username */
    private String username;
    /** represents the perosn's password */
    private String password;
    /** represents the titles' of the courses to which the person belongs */
    private ArrayList<String> courseTitles = new ArrayList<String>();

    /** Person
     * creates a person with the given name and password
     * @param username a String representing the person's username
     * @param password a String representing the person's password
     */
    Person(String username, String password){
        this.username = username;
        this.password = password;
    }

    /** Person
     * creates a person with the given name and password
     * @param username a String representing the person's username
     * @param password a String representing the person's password
     * @param courseTitles an ArrayList representing the titles of the courses a person is in
     */
    Person(String username, String password, ArrayList<String> courseTitles){
        this.username = username;
        this.password = password;
        this.courseTitles = courseTitles;
    }

    /** getCourseTitles
     * gets and returns the titles to which the person belongs
     * @return and ArrayList containing the course titles
     */
    public ArrayList<String> getCourseTitles(){
        return this.courseTitles;
    }

    /** getCourseString
     * creates and returns a string of the titles to which the person belongs, separated by spaces
     * @return a String containing the course titles
     */
    public String getCourseString(){
        String courses = "";
        for(String courseTitle: this.courseTitles){
            courses = courses + courseTitle + " ";
        }
        return courses;
    }

    /** getUsername
     * gets and returns the name of the person
     * @return a String representing the username of the person
     */
    public String getUsername(){
        return this.username;
    }

    /** getPassword
     * gets and returns the person's password
     * @return a String representing the person's password
     */
    public String getPassword(){
        return this.password;
    }

    /** addCourse
     * adds another course to the person's list of courses
     * @param title a String representing the title of the course that is being joined
     */
    public void addCourse(String title){
        this.courseTitles.add(title);
    }
}
