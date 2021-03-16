import java.util.ArrayList;

/** Student
 * represents a Student user of french language program
 * @author Valerie Fernandes
 */
public class Student extends Person{

    /** represents the levels after each completed session */
    private ArrayList<double[]> levelProgress = new ArrayList<double[]>();

    /** Student
     * creates a student with the given name and password
     * @param username a String representing the student's username
     * @param password a String representing the student's password
     */
    Student(String username, String password){
        super(username, password);
    }

    /** Student
     * creates a Student with the given name, password, and courses
     * @param username a String representing the student's username
     * @param password a String representing the student's password
     * @param courses an ArrayList representing the titles of the courses a student is in
     */
    Student(String username, String password, ArrayList<String> courses){
        super(username, password, courses);
    }

    /** getLevels
     * gets and returns the students levels overtime
     * @return and ArrayList containing the student's level history
     */
    public ArrayList<double[]> getLevels(){
        return this.levelProgress;
    }

    /** addLevel
     * adds new level scores from a completed session
     * @param vocab an int representing the vocabulary level
     * @param structure an int representing the sentence structure level
     * @param verb an int representing the verb difficulty level
     */
    public void addLevel(double vocab, double structure, double verb){
        levelProgress.add(new double[]{vocab, structure, verb});
    }

    /** updateLevel
     * updates by factoring in new level scores from a completed session
     * @param vocab an int representing the vocabulary level
     * @param structure an int representing the sentence structure level
     * @param verb an int representing the verb difficulty level
     */
    public void updateLevel(double vocab, double structure, double verb){
        if(levelProgress.size() == 0){ // give initial scores
            levelProgress.add(new double[] {Math.max(1, vocab), Math.max(1, structure), Math.max(1, verb)}); //they must begin at a non zero level

        }else {
            int sessions = levelProgress.size();
            double[] past = levelProgress.get(sessions - 1); // get session number to reduce session weight to overall score ration so one session does not have a greater impact
            levelProgress.add(new double[]{(vocab + past[0] * sessions) / (sessions + 1), (structure + past[1] * sessions) / (sessions + 1), (verb + past[2] * sessions) / (sessions + 1)});
        }
    }

    /** getLevelString
     * creates and returns a String representing the student's level history
     * @return
     */
    public String getLevelString(){
        String courses = "";
        for(double[] levels: this.levelProgress){
            courses = courses + levels[0] + " " + levels[1] + " " + levels[2] + "\n";
        }
        return courses;
    }
}
