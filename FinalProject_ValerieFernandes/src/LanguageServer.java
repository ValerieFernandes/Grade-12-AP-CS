import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

/** LanguageServer
 * Represents a server which holds and communicates the language program information
 * @author Valerie Fernandes
 */
class LanguageServer {

    /** server socket for communication */
    private ServerSocket serverSock;
    /** boolean which controls if server is accepting clients */
    static Boolean running = true;
    /** the database of DuberLingo users */
    private PersonDatabase users = new PersonDatabase();
    /** the database to create questions */
    private QuestionDatabase questionDatabase = new QuestionDatabase();

    /** Main
     * @param args parameters from command line
     */
    public static void main(String[] args) {
        new LanguageServer().go(); //start the server
    }

    /** Go
     * Starts the server
     */
    public void go() {

        System.out.println("Waiting for a client connection");
        Socket client = null;

        try {
            this.serverSock = new ServerSocket(5000);  // assigns an port to the server
            this.serverSock.setSoTimeout(120000);  // 120 second timeout

            while(running) {

                client = serverSock.accept();
                System.out.println("Client connected");
                Thread t = new Thread(new ConnectionHandler(client));
                t.start(); //start the new thread
            }

        }catch(Exception e) {
            System.out.println("Error accepting connection");

            try {
                client.close();
            }catch (Exception e1) {
                System.out.println("Failed to close socket");
            }

            this.users.writeData(); // write the current user database into text files
            System.exit(-1);
        }
    }
  
    //***** Inner class for thread for client connection
    /** ConnectionHandler
     * handles a client (input, output, functions) who has connected to the server
     * @author Valerie Fernandes
     */
    class ConnectionHandler implements Runnable {

        /** A printwriter for the network output*/
        private PrintWriter output;
        /** A BufferedReader for the network input */
        private BufferedReader input;
        /** socket for connection */
        private Socket client;
        /** boolean representing the thread status*/
        private boolean running;
        /** a person representing the client logged in */
        private Person user;

        /** ConnectionHandler
         * represents a connection handler object to deal with a connected client
         * @param s a Socket for connection
         */
        ConnectionHandler(Socket s) {

            this.client = s;
            try {
                this.output = new PrintWriter(this.client.getOutputStream());
                InputStreamReader stream = new InputStreamReader(this.client.getInputStream());
                this.input = new BufferedReader(stream);

            }catch(IOException e) {
                System.out.println("Cannot handle client connection");
            }
            running = true;
        }

        /** run
         * checks for and deals with client inputs
         */
        public void run() {

            String message ="";

            while(running) {  // wait for a message to be sent

                try {

                    if (input.ready()) { // check if a message is ready
                        message = input.readLine();  // get the message
                        System.out.println("Received: " + message);

                        if(message.equals("Login")){ // attempt login to a user account
                            attemptLogin();

                        }else if (message.equals("New User")){ // create a new user account
                            createAccount();

                        }else if(message.equals("Session")){ // generate and send a practice session
                            sendSession();

                        }else if(message.equals("Join Course")){ // add a student to a course

                            String title = input.readLine(); // get course title
                            Course course;
                            course = users.getCourse(title);

                            if(course != null){ // if course exists add the student
                                this.user.addCourse(title);
                                course.addStudent(this.user.getUsername());
                            }else{
                                output.println("Join Failure");
                                output.flush();
                            }

                        }else if(message.equals("Create Course")){ // creates a new course

                            String title = input.readLine(); // get new course title
                            Course course;
                            course = users.getCourse(title);

                            if(course == null){ // check that course does not already exist

                                this.user.addCourse(title);
                                ((Teacher)user).createCourse(users.getCourses(), title);
                                output.println("New Course");
                                output.println(title);
                                output.flush();
                            }

                        }else if(message.equals("Score Update")){ // get a student's score update

                            double vocab = Double.parseDouble(input.readLine());
                            double structure = Double.parseDouble(input.readLine());
                            double verb = Double.parseDouble(input.readLine());
                            ((Student) this.user).updateLevel(vocab, structure, verb); // update the student's score

                        }else if(message.equals("Course Info")){ // request for list of students in a course

                            Course course = users.getCourse(input.readLine());
                            output.println("Course List");
                            output.println(course.getStudentString()); // send the student list to client
                            output.flush();

                        }else if(message.equals("Student Info")){ // request for information about a student
                            sendStudentInfo();
                        }
                    }
                }catch (IOException e) {
                    System.out.println("Failed to receive message from the client");
                }
            }
            try {
                input.close(); // close streams
                output.close();
                client.close();

            }catch (Exception e) {
            System.out.println("Failed to close socket");
            }
        }

        /** attemptLogin
         * tries to login to a new user account
         */
        public void attemptLogin(){

            try {
                this.user = users.getPerson(input.readLine(), input.readLine());
            } catch (IOException e) {
                System.out.println("Message failed to be received");
            }

            if(user != null){ // check if the user exists
                output.println("Success");

                if(user instanceof Student) { // send the student's courses to the client
                    output.println("Student");
                    for(String courseName: user.getCourseTitles()){
                        output.println(courseName);
                    }

                    output.println("Scores");

                    for(double[] scores : ((Student)user).getLevels()){ // send the student's scores to the client
                        output.println(scores[0]);
                        output.println(scores[1]);
                        output.println(scores[2]);
                    }

                }else{

                    output.println("Teacher");

                    for(String courseName: user.getCourseTitles()){ // send the teacher's courses to the client
                        output.println(courseName);
                    }
                }
                output.println("Done");

            }else{
                output.println("Failure");
            }

            output.flush();
        }

        /** sendStudentInfo
         * sends information about a student to the client
         */
        public void sendStudentInfo(){

            try {
                Student student = (Student)users.getPerson(input.readLine());

                if(student != null) { // check if the student exists

                    output.println("Graph");
                    for (String courseName : student.getCourseTitles()) { // send student's course names
                        output.println(courseName);
                    }

                    output.println("Scores");
                    for (double[] scores : ((Student) student).getLevels()) { // send student's scores
                        output.println(scores[0]);
                        output.println(scores[1]);
                        output.println(scores[2]);
                    }

                    output.println("Done");
                }
                output.flush();

            } catch (IOException e) {
                System.out.println("Messages not received");
            }
        }

        /** createAccount
         * creates a new user account
         */
        public void createAccount(){

            try {
                String username = input.readLine(); // get initial user information
                String password = input.readLine();
                String type = input.readLine();

                this.user = users.getPerson(username, password);

                if(this.user == null){ // check that the person does not already exist

                    if(type.equals("Teacher")){
                        this.user = new Teacher(username, password); // create a new student

                    }else{
                        this.user = new Student(username, password); // create a new student
                    }
                    users.addPerson(user);
                }
                output.println("Initialise"); // send new user information to client

                if(this.user instanceof Student) {
                    output.println("Student");
                    Session session = new Session(0, 0, 0, questionDatabase); // send diagnostic assessment for new student
                    session.sendSessionInfo(output);

                }else{
                    output.println("Teacher");
                    output.flush();
                }

            } catch (IOException e) {
                System.out.println("Data transmitted incorrectly");
            }
        }

        /** sendSession
         * sends the information about a practice session to the client
         */
        public void sendSession(){
            Student student = (Student) this.user;
            double[] levelProgress = student.getLevels().get(student.getLevels().size() - 1);
            Session session = new Session((int)levelProgress[0], (int)levelProgress[1], (int) levelProgress[2], questionDatabase); // develop session based on student's levels
            session.sendSessionInfo(output); //send the questions from the session
        }
    }
}