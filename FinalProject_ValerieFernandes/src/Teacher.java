import java.util.ArrayList;

/** Teacher
 * represents a Teacher user of french language program
 * @author Valerie Fernandes
 */
public class Teacher extends Person {

    /** Student
     * creates a teacher with the given name and password
     * @param username a String representing the teacher's username
     * @param password a String representing the teacher's password
     */
    Teacher(String username, String password){
        super(username, password);
    }

    /** Teacher
     * creates a teacher with the given name and password
     * @param username a String representing the teacher's username
     * @param password a String representing the teacher's password
     * @param courses an ArrayList representing the titles of the courses a teacher has
     */
    Teacher(String username, String password, ArrayList<String> courses){
        super(username, password, courses);
    }

    /** getCourseInfo
     * creates and returns an array of the teacher's courses title's
     * @return a String[] containing the titles of the teacher's courses
     */
    public String[] getCourseInfo(){

        String[] courseInfo = new String[super.getCourseTitles().size()];

        for(int i=0; i<courseInfo.length; i++){
            courseInfo[i] = super.getCourseTitles().get(i);
        }

        return courseInfo;
    }

    /** createCourse
     * creates a new course with the desired title and appends it to a course list
     * @param courses an ArrayList containing Course objects
     * @param title the title of the new course
     */
    public void createCourse(ArrayList<Course> courses, String title){
        courses.add(new Course(title, super.getUsername()));
    }


}
