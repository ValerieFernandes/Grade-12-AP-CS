import java.awt.Color;
import java.awt.Font;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;
import java.net.Socket;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Queue;

/** Language Client
 * Represents a client which interacts with a server to deliver language
 * learning services
 * @author Valerie Fernandes
 */

class LanguageClient {

    //Variables for server communication
    /** socket for connection */
    private Socket mySocket;
    /** reader for network input stream */
    private BufferedReader input;
    /** printwriter for network output */
    private PrintWriter output;
    /** boolean representing the thread status*/
    private boolean running = true;
    /** represents a thread for reading server messages */
     private Thread messageReader;

    //Variables for GUI
    /** frame for holding graphics content */
    private JFrame window;
    /** panel for logging into a user account */
    private LoginPanel loginPanel;
    /** panel for connecting to a server */
    private SettingsPanel settingsPanel;
    /** panel for a teacher's home screen */
    private TeacherHomePanel teacherHomePanel;
    /** panel for a student's home screen */
    private StudentHomePanel studentHomePanel;
    /** panel for displaying questions */
    private QuestionPanel questionPanel;

    //variables for client info
    /** represents the scores of the current session */
    private double[] scores;
    /** represents a user and its' information */
    private Person person;
    /** represents a session of questions */
    private Queue<Question> questions = new LinkedList<Question>();

    /** Main
     * @param args parameters from command line
     */
    public static void main(String[] args) {

        new LanguageClient().go();

    }

    /** go
     * Starts the client
     */
    public void go() {

        this.window = new JFrame("DuberLingo");
        this.loginPanel = new LoginPanel(new SettingsListener(), new LoginListener(), new CreateListener());
        this.window.add(loginPanel);
        this.window.setSize(800,600);
        this.window.setLocationRelativeTo(null);
        this.window.setVisible(true);

    }


    /** connect
     * Attempts to connect to the server and creates the socket and streams
     * @param ip the ip address, trying to be connected to
     * @param port the number of the port being connected to
     * @return a Socket for the connection
     */
    public Socket connect(String ip, int port) {

        try {
            this.mySocket = new Socket(ip, port);
            this.input = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            this.output = new PrintWriter(mySocket.getOutputStream());

            this.settingsPanel.setConnected(); // inform user connection succeeded
            this.loginPanel.setLoginError("");

            this.messageReader = new Thread(new ServerThread()); // start a thread to read messages
            this.messageReader.start();

        } catch (IOException e) {
             System.out.println("Connection to Server Failed");
        }

        return mySocket;
    }

    /** readMessagesFromServer
     *  Starts a loop waiting for server input and then processes it accordingly
     */
    public void readMessagesFromServer() {

        while(running) {

             try {

                 if (input.ready()) { // continually checks for message
                     String message;
                     message = input.readLine(); // reads the message
                     System.out.println(message);

                     if(message.equals("Success")) { // receive successful login attempt
                         message = input.readLine();

                         if (message.equals("Teacher")) {
                             initializeTeacher(); // input a teacher's information

                         } else {
                             initializeStudent(); // input a students's information
                         }

                     }else if(message.equals("Failure")) { // inform user of login failure
                         this.loginPanel.setLoginError("Incorrect login info");

                     }else if(message.equals("Initialise")){ // receive a new user
                         message = input.readLine();

                         if (message.equals("Teacher")) {
                             initializeTeacher(); // input new Teacher's Information

                         } else {
                             this.person = new Student(this.loginPanel.getUsername(), this.loginPanel.getPassword()); // represent a new student
                             this.studentHomePanel = new StudentHomePanel(new StartListener(), new QuitListener(), new JoinListener(), (Student) person);
                             input.readLine();
                             JOptionPane.showMessageDialog(window,"Welcome to your diagnostic assessment");
                             receiveSession(); // run the diagnostic session
                         }

                     }else if(message.equals("Session")){ // receive a practice session
                         receiveSession();

                     }else if(message.equals("Graph")){ // receive information for a new graph
                         updateGraph();

                     }else if(message.equals("Course List")) { // receive a list of students in a course
                         String courseList = input.readLine();
                         this.teacherHomePanel.updateStudentList(courseList);

                         if (courseList.split(" ").length > 0){
                             output.println("Student Info"); // update graph to newest student in the list
                             output.println(courseList.split(" ")[0]);
                             output.flush();
                        }
                     }else if(message.equals("New Course")){ // add a new course to teacher's list
                         teacherHomePanel.updateCourseList(input.readLine());

                     }else if(message.equals("Join Failure")){
                        studentHomePanel.setFailureLabel("Course does not exist");
                     }
                 }

             }catch (IOException e) {
                 System.out.println("Failed to receive message from the server");
            }
        }

        try {  // after leaving the main loop  close all the sockets
            input.close();
            output.close();
            mySocket.close();

        }catch (Exception e) {
            System.out.println("Failed to close socket");
        }
    }

    /** initializeTeacher
     *  receives and stores a teacher's information, and allows actions to be performed to account
     */
    public void initializeTeacher(){

        try {
            this.person  = new Teacher(loginPanel.getUsername(), loginPanel.getPassword()); // create a Teacher object
            String course;

            while(input.ready()){ // read in and add the teacher's courses
                course = input.readLine();

                if(course.equals("Done")){
                    break;
                }else{
                    this.person.addCourse(course);
                }
            }

            this.teacherHomePanel = new TeacherHomePanel(new CreateCourseListener(), new QuitListener(), new UpdateCourseListener(), new UpdateStudentListener(), (Teacher) person);
            window.remove(loginPanel); // switch GUI to teacher home screen
            window.add(teacherHomePanel);
            window.revalidate();
            window.repaint();

            if(this.person.getCourseTitles().size() > 0){
                output.println("Course Info"); // set initial dropdown
                output.println(person.getCourseTitles().get(0));

                output.println("Student Info"); // set initial graph
                output.println(teacherHomePanel.getStudentName());
                output.flush();
            }

        } catch (IOException e) {
            System.out.println("Error transmitting user data");
        }
    }

    /** initializeStudent
     *  receives and stores a student's information, and allows actions to be performed to account
     */
    public void initializeStudent(){

        int value;
        int count = 0;

        try {
            this.person = new Student(loginPanel.getUsername(), loginPanel.getPassword()); // create a Student object
            String course;

            while(true){ // read in and add the Student's courses
                course = input.readLine();
                if(course.equals("Scores")){
                    break;
                }else{
                    this.person.addCourse(course);
                }
            }

            String score;
            double[] scores = new double[3];

            while(true){ // read in and add the student's scores
                score = input.readLine();

                if(score.equals("Done")){
                    break;

                }else{
                   scores[0] = Double.parseDouble(score) ;
                   score = input.readLine();
                   scores[1] = Double.parseDouble(score);
                   score = input.readLine();
                   scores[2] = Double.parseDouble(score);
                   ((Student) this.person).updateLevel(scores[0], scores[1], scores[2]);
                }
            }

            studentHomePanel = new StudentHomePanel(new StartListener(), new QuitListener(), new JoinListener(), (Student) person);
            window.remove(loginPanel); // switch GUI to student home screen
            window.add(studentHomePanel);
            window.revalidate();
            window.repaint();

        } catch (IOException e) {
            System.out.println("Error transmitting user data");
        }
    }

    /** receiveSession
     * receive's a practice session and displays the first question
     */
    public void receiveSession(){

        String question;
        String vocabAnswer;
        String verbAnswer;
        String options;
        int vocab;
        int structure;
        int verb;

        try{

            for (int i = 0; i < 20; i++) { // reads in the question information and adds them to a queue
                question = input.readLine();
                vocabAnswer = input.readLine();
                verbAnswer = input.readLine();
                options = input.readLine();
                vocab = Integer.parseInt(input.readLine());
                structure = Integer.parseInt(input.readLine());
                verb = Integer.parseInt(input.readLine());
                this.questions.add(new Question(vocab, structure, verb, question, vocabAnswer, verbAnswer, options));
            }

            this.scores = new double[]{0.0, 0.0, 0.0};
            this.questionPanel = new QuestionPanel(new SubmitListener(), new NextListener(), questions.remove());
            this.window.getContentPane().removeAll();
            this.window.add(questionPanel); // display the first question from the queue
            this.window.revalidate();
            this.window.repaint();

        } catch (IOException e) {
            System.out.println("Error in receiving session");
        }
    }

    /** updateGraph
     *  updates a teacher's graph to represent a different student's information
     */
    public void updateGraph(){

        int value;
        int count = 0;

        try {
            Student student = new Student(this.teacherHomePanel.getStudentName(), ""); // create a Student object
            String course;

            while(true){ // read in the student's courses
                course = input.readLine();
                if(course.equals("Scores")){
                    break;
                }else{
                    student.addCourse(course);
                }
            }

            String score;
            double[] scores = new double[3];

            while(true){ // read in the student's scores
                score = input.readLine();

                if(score.equals("Done")){
                    break;

                }else{
                    scores[0] = Double.parseDouble(score) ;
                    score = input.readLine();
                    scores[1] = Double.parseDouble(score);
                    score = input.readLine();
                    scores[2] = Double.parseDouble(score);
                    ((Student) student).updateLevel(scores[0], scores[1], scores[2]);
                }
            }

            this.teacherHomePanel.updateGraph(student);

        } catch (IOException e) {
            System.out.println("Error transmitting student's data");
        }
    }

    /** displayEndPanel
     * this function displays a goodbye panel once the user quits the program
     */
    public void displayEndPanel(){

        this.window.getContentPane().removeAll(); //remove anything being displayed

        JPanel endPanel = new JPanel();
        endPanel.setLayout(null);
        endPanel.setBackground(new Color(197, 237, 204));

        JLabel endLabel = new JLabel("Thank you for using");
        endLabel.setFont(new Font("serif", Font.PLAIN, 30));
        endLabel.setBounds(260, 100, 300, 40);
        endPanel.add(endLabel);

        try {
            BufferedImage logo = ImageIO.read(new File("FinalLogo.png"));
            JLabel picture = new JLabel(new ImageIcon(logo));
            picture.setBounds(20, 200, 800, 500);
            endPanel.add(picture);

        } catch (IOException e) {
            System.out.println("Logo Image not found");
        }

        JLabel returnLabel = new JLabel("See you again soon!");
        returnLabel.setFont(new Font("serif", Font.PLAIN, 30));
        returnLabel.setBounds(270, 400, 300, 40);
        endPanel.add(returnLabel);

        this.window.add(returnLabel);
        this.window.add(endPanel);
        this.window.revalidate();
        this.window.repaint();
    }

    //****** Inner Classes for Action Listeners ****
    /** LoginButtonListener
     *  Listener for making a new user account
     * @author Valerie Fernandes
     */
    class CreateListener implements ActionListener {

        /** actionPerformed
         * send new user information to Server when action is performed
         * @param event an ActionEvent which occurred
         */
        public void actionPerformed(ActionEvent event) {

            output.println("New User");
            output.println(loginPanel.getUsername());
            output.println(loginPanel.getPassword());
            output.println(loginPanel.getType());
            output.flush();
        }
     }

    /** LoginButtonListener
     *  Listener for making login attempts
     * @author Valerie Fernandes
     */
    class LoginListener implements ActionListener {

        /** actionPerformed
         * login attempt when action is performed
         * @param event an ActionEvent which occurred
         */
         public void actionPerformed(ActionEvent event) {

             output.println("Login");
             output.println(loginPanel.getUsername());
             output.println(loginPanel.getPassword());
             output.flush();
         }
    }

    /** SettingsButtonListener
     * listener for opening settings panel
     * @author Valerie Fernandes
     */
    class SettingsListener implements ActionListener {

        /** actionPerformed
         * Opens settings panel when action is performed
         * @param event an ActionEvent which occurred
         */
        public void actionPerformed(ActionEvent event) {

            window.remove(loginPanel);
            settingsPanel = new SettingsPanel(new CloseListener(), new ConnectListener());
            window.add(settingsPanel);
            window.revalidate();
            window.repaint();
        }
    }

    /** QuitButtonListener
     * listener for quitting the language program
     * @author Valerie Fernandes
     */
    class QuitListener implements ActionListener {

        /** actionPerformed
         * quit running and display the end panel
         * @param event an Action event which occurred
         */
        public void actionPerformed(ActionEvent event)  {
            running = false;
            displayEndPanel();
        }
    }

    /** ConnectButtonListener
     * listener for connecting to server
     * @author Valerie Fernandes
     */
    class ConnectListener implements ActionListener{

        /** actionPerformed
         *  attempt to connect using given information
         * @param event an ActionEvent which occurred
         */
        public void actionPerformed(ActionEvent event)  {

            try{
                String address = settingsPanel.getAddress();
                int port = Integer.parseInt(settingsPanel.getPort());
                connect(address, port);

            }catch (Exception e) {
                System.out.println("Port number not an Integer");
            }
        }
    }

    /** CreateCourseListener
     * listener for creating a new course
     * @author Valerie Fernandes
     */

    class CreateCourseListener implements ActionListener {

        /** actionPerformed
         * quit running and  close chats on end
         * @param event an Action event which occurred
         */
        public void actionPerformed(ActionEvent event) {

            output.println("Create Course");
            output.println(teacherHomePanel.getNewCourseName());
            output.flush();
        }
    }

    /** StartListener
     * listener for user asking to start a session
     * @author Valerie Fernandes
     */
    class StartListener implements ActionListener {

        /** actionPerformed
         * send prompt for a new session to the server
         * @param event an Action event which occurred
         */
        public void actionPerformed(ActionEvent event) {

            output.println("Session");
            output.flush();
        }
    }

    /** JoinListener
     * listener for student to join a course
     * @author Valerie Fernandes
     */
    class JoinListener implements ActionListener {

        /** actionPerformed
         * sends the name of joined course to server
         * @param event an Action event which occurred
         */
        public void actionPerformed(ActionEvent event) {

            studentHomePanel.setFailureLabel("");
            output.println("Join Course");
            output.println(studentHomePanel.getCourseName());
            output.flush();
         }
     }

    /** CloseListener
     * listener for closing a settings panel
     * @author Valerie Fernandes
     */
    class CloseListener implements ActionListener {

        /** actionPerformed
         * closes the settings panel and displays the login panel
         * @param event an Action event which occurred
         */
        public void actionPerformed(ActionEvent event) {

            window.remove(settingsPanel);
            window.add(loginPanel);
            window.revalidate();
            window.repaint();
        }
    }

    /** UpdateClassListener
     * listener for requesting information about a new course
     * @author Valerie Fernandes
     */
    class UpdateCourseListener implements ActionListener{

        /** actionPerformed
         * sends name of course to server to request information
         * @param event an Action event which occurred
         */
        public void actionPerformed(ActionEvent event) {

            output.println("Course Info");
            output.println(teacherHomePanel.getCourseName());
            output.flush();
        }
    }

    /** UpdateStudentListener
     * listener for requesting student information from server
     * @author Valerie Fernandes
     */
    class UpdateStudentListener implements ActionListener{

        /** actionPerformed
         * sends name of student to server to request information for graph
         * @param event
         */
        public void actionPerformed(ActionEvent event)  {
            output.println("Student Info");
            output.println(teacherHomePanel.getStudentName());
            output.flush();
        }

    }

    /** SubmitListener
     * listener for submitting question answers
     * @author Valerie Fernandes
     */
    class SubmitListener implements ActionListener {

        /** actionPerformed
         * updates scores given current user answer's for questions
         * @param event an Action event which occurred
         */
        public void actionPerformed(ActionEvent event) {
            questionPanel.changeSubmitStatus(false); // only allow one submission per question

            int number = questionPanel.getQuestionNumber();

            if(((Student)person).getLevels().size() == 0){ //check f its the diagnostic session

                if(questionPanel.getVerbScore() == questionPanel.getQuestion().getVerb()){ // if its first time, only weigh correct answers
                    scores[0] = scores[0] *  ((number - 1.0) / number) + questionPanel.getVerbScore() * (1.0 / number); // keep weighting equal to previous questions
                    scores[2] = scores[2] + 0.5; // add  to tally if correct

                }if(questionPanel.getVocabScore() == questionPanel.getQuestion().getVocab()){
                    scores[1] = scores[1] *  ((number - 1.0) / number) + questionPanel.getVocabScore() * (1.0 / number);
                    scores[2] = scores[2] + 0.5;
                }

            }else {

                scores[0] = scores[0] * ((number - 1.0) / number) + questionPanel.getVerbScore() * (1.0 / number); // add to score, each question has a lower weight
                scores[1] = scores[1] * ((number - 1.0) / number) + questionPanel.getVocabScore() * (1.0 / number); // so that the cumulative score weighs all questions equally

                if(questionPanel.getVerbScore() == questionPanel.getQuestion().getVerb()) { // add to tally if correct
                    scores[2] = scores[2] + 0.5;

                }if(questionPanel.getVocabScore() == questionPanel.getQuestion().getVocab()){
                    scores[2] = scores[2] + 0.5;
                }
            }
            questionPanel.displayAnswer();
        }
    }

    /** NextListener
     * listener for switching to the next question in the session
     * @author Valerie Fernandes
     */
    class NextListener implements ActionListener{

        /** actionPerformed
         * switches display question and ends session on last question
         * @param event an Action event which occurred
         */
        public void actionPerformed(ActionEvent event) {

            questionPanel.changeSubmitStatus(true); // enable submission for next question

            if(!questions.isEmpty()){ // switch to the next question
                questionPanel.setQuestion(questions.remove());

            }else{ // end the session when queue is empty
                window.remove(questionPanel);
                window.add(studentHomePanel); // switch back to home panel
                window.revalidate();
                window.repaint();
                JOptionPane.showMessageDialog(window,"Your overall score was " + scores[2] + "/20"); // inform user of their scores
                ((Student) person).updateLevel(scores[1], (scores[0] + scores[1]) / 2, scores[0]); // update Student object's score
                sendScores(); // send the scores to the server
                studentHomePanel.updateGraph(((Student) person).getLevels().get(((Student) person).getLevels().size() -  1)); // update the student's graph with the new scores
            }
        }

        /** sendScores
         * sends the scores to the server to update the database
         */
        private void sendScores(){
            output.println("Score Update"); // send new scores to the server
            scores = ((Student) person).getLevels().get(((Student) person).getLevels().size() - 1);
            output.println(scores[1]); //vocab score
            output.println((scores[0] + scores[1]) / 2); // structure score
            output.println(scores[0]); // verb score
            output.flush();
        }
    }

    //****** Inner Classes for Threads****
    /** ServerThread
     *  a thread to interact with server (input/output)
     * @author Valerie Fernandes
     */
    class ServerThread implements Runnable {
        /** run
        *  when run starts function to read server messages
        */
        public void run() {
            readMessagesFromServer();
        }
    }
}