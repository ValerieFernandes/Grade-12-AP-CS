import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

/** PersonDatabase
 * represents a database of users belonging to the program
 * @author Valerie Fernandes
 */
public class PersonDatabase {

    /** the people belonging to the database */
    private ArrayList<Person> people = new ArrayList<Person>();
    /** the courses belonging to the database */
    private ArrayList<Course> courses = new ArrayList<Course>();

    /** PersonDatabase
     * Creates a database of people and their courses
     */
    PersonDatabase(){

        File dataFile = new  File("PersonDatabase.txt");
        String line;
        String name;
        String password;
        String[] classes;
        String[] scores;

        try {
            Scanner input = new Scanner(dataFile); // get input from the file

            while(input.hasNextLine()){
                line = input.nextLine();

                if(line.equals("Student")){ // create a new Student and add them to the database
                    name = input.nextLine();
                    password = input.nextLine();
                    classes = input.nextLine().split(" ");
                    people.add(new Student(name, password, new ArrayList<String>(Arrays.asList(classes))));

                    while (true){
                        scores = input.nextLine().split(" ");

                        if(scores[0].equals("Done")){
                            break;

                        }else{
                            ((Student) people.get(people.size() - 1)).addLevel(Double.parseDouble(scores[0]), Double.parseDouble(scores[1]), Double.parseDouble(scores[2]));
                        }
                    }

                }else if(line.equals("Teacher")){ // create a new Teacher and add them to the database
                    name = input.nextLine();
                    password = input.nextLine();
                    classes = input.nextLine().split(" ");
                    people.add(new Teacher(name, password, new ArrayList<String>(Arrays.asList(classes))));

                }else if(line.equals("Courses")){ // create a the Courses and add them to the database

                    while (true){
                        classes = input.nextLine().split(" ");

                        if(classes[0].equals("Done")){
                            break;

                        }else{
                            courses.add(new Course(classes[0], classes[1]));
                            classes = input.nextLine().split(" ");

                            for(String student: classes){
                                courses.get(courses.size() - 1).addStudent(student);
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("database does not exist");
        }
    }

    /** addPerson
     * adds a new peron to the database
     * @param person
     */
    public void addPerson(Person person){
        this.people.add(person);
    }

    /** getPerson
     * gets and returns the Person corresponding to the given name/password
     * @param username a String representing the name of the Person
     * @param password a String representing the person's password
     * @return a Person object with the required name and password
     */
    public Person getPerson(String username, String password){
        for (Person current: people) {
            if((current.getUsername().equals(username)) && (current.getPassword().equals(password))){
                return current;
            }
        }
        return null;
    }

    /** getPerson
     * gets and returns the Person corresponding to the given name
     * @param username a String representing the name of the Person
     * @return a Person object with the required name
     */
    public Person getPerson(String username){
        for (Person current: people) {
            if(current.getUsername().equals(username)){
                return current;
            }
        }
        return null;
    }

    /** getCourse
     * gets and returns the course with the requested title
     * @param title a String representing the title of the course
     * @return a Course object with the necessary title
     */
    public Course getCourse(String title){
        for (Course current: courses) {
            if(current.getTitle().equals(title)){
                return current;
            }
        }
        return null;
    }

    /** getCourses
     * gets and returns the courses in the database
     * @return an ArrayList containing the database courses
     */
    public ArrayList<Course> getCourses() {
        return courses;
    }

    /** writeData
     * writes the database information to a text file
     */
    public void writeData(){

        try {
            File dataFile = new File("PersonDatabase.txt");
            PrintWriter output =  new PrintWriter(dataFile);

            for(Person person: this.people){ // loop through each person

                if(person instanceof Teacher){
                    output.println("Teacher");
                }else{
                    output.println("Student");
                }

                output.println(person.getUsername()); // output person name, password, and courses
                output.println(person.getPassword());
                output.println(person.getCourseString());

                if(person instanceof Student) { // output the students score history
                    output.print(((Student)person).getLevelString());
                    output.println("Done");
                }
            }

            output.println("Courses");
            for(Course course: this.courses){ // loop through courses outputting name and participants
                output.println(course.getTitle() + " " + course.getTeacherName());
                output.println(course.getStudentString());
            }
            output.println("Done");

            output.close(); // close the output stream

        } catch (FileNotFoundException e) {
            System.out.println("Person Database file cannot be found");
        }
    }
}
