import java.util.ArrayList;

/** Course
 * represents a course and its members
 * @author Valerie Fernandes
 */

public class Course {

    /** the title of the course */
    private String title;

    /** the name of the course's teacher */
    private String teacherName;

    /**the names of the student's in the course */
    private ArrayList<String> studentNames = new ArrayList<String>();

    /** Course
     * Creates a new course with the given title and teacher
     * @param title the title of the course
     * @param teacher the name of the teacher of the course
     */
    Course(String title, String teacher){
        this.title = title;
        this.teacherName = teacher;
    }

    /** getStudents
     * Gets and returns an array of the names of the students enrolled in the course
     * @return a String array containing the student names
     */
    public String[] getStudents(){
        String[] students = new String[this.studentNames.size()];

        for(int i=0; i<students.length; i++){
            students[i] = this.studentNames.get(i);
        }

        return students;
    }

    /** getStudentString
     * Gets and returns an string of the names of the students enrolled in the course
     * separated by spaces
     * @return a String containing the student names
     */
    public String getStudentString(){
        String students = "";

        for(int i=0; i<this.studentNames.size(); i++){
            students = students + this.studentNames.get(i) + " ";
        }

        return students;
    }

    /** getTitle
     * Gets and returns the title of the course
     * @return a String representing the title of the course
     */
    public String getTitle(){
        return this.title;
    }

    /** addStudent
     * adds a new student to the course
     * @param student a String representing the name of the student
     */
    public void addStudent(String student){
        this.studentNames.add(student);
    }

    /** getTeacherName
     * gets and returns the name of the course's teacher
     * @return a String representing the name of the teacher
     */
    public String getTeacherName() {
        return this.teacherName;
    }
}
