import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**ChatClient
* Represents a client which chats with other's on the desired serrveer
* @author Valerie Fernandes
 */

class ChatClient {
    /** An ArrayList of all chats belonging to the client */
    private ArrayList<Chat> chats = new ArrayList<Chat>();
    /** Arrays of the  names of all users who are online and offline respectively */
    private String [] online, offline;
    /** The main JFrame used in the GUI */
    private JFrame window;
    /** A Frame used for the current open chat */
    private ChatFrame chatFrame;
    /** A Panel used to get login details */
    private LoginPanel loginPanel;
    /** A panel use to get the server settings*/
    private SettingsPanel settingsPanel;
    /** A Panel used to create and manage chats */
    private ManagerPanel managerPanel;
    /** A socket for connection */
    private Socket mySocket;
    /** A BufferedReader for the network input */
    private BufferedReader input;
    /** A printwriter for the network output*/
    private PrintWriter output;
    /** A boolean showing the status of thread */
    private boolean running = true;
    /** A thread for communicating with the server*/
    private Thread s;
    /** A picure which represent the duberChat logo */
    private JLabel picture;
    /** The id number of the open chat*/
    private int chatId = 0;

    /** Main
     * @param args parameters from command line
     */
    public static void main(String[] args) {
        new ChatClient().go();
    }

    /** go
     * Starts the server
     */
    public void go() {
        window = new JFrame("Chat Client");
        window.setSize(400,400);
        window.setLocation(400, 0);

        try {
            BufferedImage logo = ImageIO.read(new File("DuberChatLogo.png"));
            picture = new JLabel(new ImageIcon(logo));
        } catch (IOException e) {
            System.out.println("Logo Image not found");
        }

        loginPanel = new LoginPanel(picture, new QuitButtonListener(), new SettingsButtonListener(), new LoginButtonListener());
        window.add(loginPanel);
        window.setVisible(true);
        window.repaint();
    }

    /** connect
     * Attempts to connect to the server and creates the socket and streams
     * @param ip the ip address, trying to be connected to
     * @param port the number of the port being connected to
     * @return a Socket for the connection
     */
    //Attempts to connect to the server and creates the socket and streams
    public Socket connect(String ip, int port) {
        System.out.println("Attempting to make a connection..");

        try {
            mySocket = new Socket(ip, port); //attempt socket connection, this will wait until a connection is made

            InputStreamReader stream1= new InputStreamReader(mySocket.getInputStream()); //Stream for network input
            input = new BufferedReader(stream1);
            output = new PrintWriter(mySocket.getOutputStream()); //assign printwriter to network stream
            System.out.println("Connection made.");
            settingsPanel.setConnected();
            loginPanel.setLoginError("");
            s = new Thread(new ServerThread());
            s.start();

        } catch (IOException e) {  //connection error occured
            System.out.println("Connection to Server Failed");
            e.printStackTrace();
        }

        return mySocket;
    }

    /** readMessagesFromServer
     *  Starts a loop waiting for server input and then processes it accordingly
     */
    public void readMessagesFromServer() {
        String sender;
        String[] users;

        while(running) {  // loop unit a message is received
            try {

                if (input.ready()) { //check for an incoming message
                    String msg;
                    msg = input.readLine(); //read the message

                    if(msg.equals("Success")){ //On a login success get information about other users
                        accessAccount();

                    }else if(msg.equals("Failure")){ // Inform user on login failure
                        loginPanel.setLoginError("Incorrect Username and/or Password");

                    }else if(msg.equals("Timeout")){ // If timed out end program
                        running = false;
                        if(chatFrame != null){
                            chatFrame.dispose();
                        }
                        displayEndPanel();

                    }else if(msg.equals("Message")){ // Receive a message
                        sender = input.readLine();
                        msg = input.readLine();
                        users = msg.split(" ");
                        msg = input.readLine();
                        receiveMessage(sender, users, msg);


                    }else if(msg.contains("Online")){ // Change someone's status to online
                        msg = input.readLine();
                        managerPanel.setOfflineNames(managerPanel.getOfflineNames().replace(msg , "").replaceAll("[\n]", "3"));
                        managerPanel.setOfflineNames(managerPanel.getOfflineNames().replace("33", "3").replaceAll("3", "\n"));
                        managerPanel.setOnlineNames(managerPanel.getOnlineNames() + msg + "\n");

                    }else if(msg.contains("Offline")){ // Chane someone's status to offline
                        msg = input.readLine();
                        managerPanel.setOnlineNames(managerPanel.getOnlineNames().replace(msg , "").replaceAll("[\n]", "3"));
                        managerPanel.setOnlineNames(managerPanel.getOnlineNames().replace("33", "3").replaceAll("3", "\n"));
                        managerPanel.setOfflineNames(managerPanel.getOfflineNames() + msg + "\n");
                    }
                }

            }catch (IOException e) {
                System.out.println("Failed to receive msg from the server");
                e.printStackTrace();
            }
        }
        output.println("Offline");
        output.println(loginPanel.getUsername());
        output.flush();
        try {  //after leaving the main loop we need to close all the sockets
            input.close();
            output.close();
            mySocket.close();
        }catch (Exception e) {
            System.out.println("Failed to close socket");
        }
    }

    /** accessAccount
     * Gets the information about user account and displays it through the GUI
     */
    public void accessAccount(){
        String msg = null;
        try {
            msg = input.readLine();
            if(msg.contains("Users")) {
                online = input.readLine().split(" ");
                offline = input.readLine().split(" ");
            }
        } catch (IOException e) {
            System.out.println("Failed to receive msg from the server");
        }
        window.remove(loginPanel);
        managerPanel = new ManagerPanel(picture, online, offline, new QuitButtonListener(), new NewChatButtonListener(), new OpenChatButtonListener());
        window.add(managerPanel);
        window.revalidate();
        window.repaint();
        chatFrame = new ChatFrame(output, chats);

    }

    /** receiveMessage
     * Attach a message to appropriate chat, and display if necessary
     * @param sender a String representing the name of the sender
     * @param receivers a String[] representing the names of the other message receivers
     * @param content a String representing the content of the message
     */
    public void receiveMessage(String sender, String[]receivers, String content){
        boolean exists = false;
        Arrays.sort(receivers); //sort alphabetically, matching format of chat participant arrays
        for(int i=0; i<chats.size(); i++){
            if(Arrays.equals(receivers, chats.get(i).getParticipants())){ // if chat exists append the message
                exists = true;
                chats.get(i).addMessage(sender, content);
                if(chatFrame.getChatId() == i + 1){ // if the chat is currently open update display
                    chatFrame.updateMessages(sender, content);
                }
            }
        }if(!exists){ // If chat doesn't exist create a new chat and append message
            chats.add(new Chat(chats.size() + 1, receivers));
            chats.get(chats.size() - 1).addMessage(sender, content);
            managerPanel.addChat(chats.get(chats.size() - 1));
        }
    }

    /** displayEndPanel
     * this function displays the panel when the chatting finishes
     */
    public void displayEndPanel(){
        window.getContentPane().removeAll(); //remove anything

        JPanel endPanel = new JPanel();
        endPanel.setLayout(null);
        endPanel.setBackground(new Color(25, 150, 199));

        JLabel endLabel = new JLabel("Thank you for using");
        endLabel.setBounds(132, 100, 200, 30);
        endPanel.add(endLabel);

        picture.setBounds(-20, 100, 400, 400);
        endPanel.add(picture);

        window.add(endPanel);
        window.revalidate();
        window.repaint();
    }


    //****** Inner Classes for Action Listeners****
    /** LoginButtonListener
     *  Listener ffor making login attempts
     * @author Valerie Fernandes
     */
    class LoginButtonListener implements ActionListener{

        /** actionPerformed
         * login attempt when action is performed
         * @param event an ActionEvent which occured
         */
        public void actionPerformed(ActionEvent event)  {
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
    class SettingsButtonListener implements ActionListener {

        /** actionPerformed
         * Opens settings panel when action is performed
         * @param event an ActionEvent
         */
        public void actionPerformed(ActionEvent event)  {
            window.remove(loginPanel);
            settingsPanel = new SettingsPanel(new CloseButtonListener(), new ConnectButtonListener());
            window.add(settingsPanel);
            window.revalidate();
            window.repaint();
        }
    }

    /** QuitButtonListener
     * listener for quitting the chatting program
     * @author Valerie Fernandes
     */
    class QuitButtonListener implements ActionListener {

        /** actionPerformed
         * quit running and  close chats on end
         * @param event an Action event
         */
        public void actionPerformed(ActionEvent event)  {
            running = false;
            if(chatFrame != null){
                chatFrame.dispose();
            }
            displayEndPanel();
        }
    }

    /** CloseButtonListener
     *  listener for closing settings
     * @author Valerie Fernandes
     */
    class CloseButtonListener implements ActionListener{

        /** actionPerformed
         * remove settings and display login panel
         * @param event ActionEvent
         */
        public void actionPerformed(ActionEvent event)  {
            window.remove(settingsPanel);
            window.add(loginPanel);
            window.revalidate();
            window.repaint();
        }
    }

    /** ConnectButtonListener
     * listener for connecting to server
     * @author Valerie Fernandes
     */
    class ConnectButtonListener implements ActionListener{

        /** actionPerformed
         *  attempt to connect using given information
         * @param event an ActionEvent
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

    /** NewChatButtonListener
     *  listener for creating new chats
     * @author Valerie Fernandes
     */
    class NewChatButtonListener implements ActionListener{

        /** actionPerformed
         *  attempts to create a new chat with given users
         * @param event an ActionEvent
         */
        public void actionPerformed(ActionEvent event)  {
            boolean valid = true;
            String[] chatPeople = managerPanel.getParticipantNames().split(" ");

            for(int i=0; i<chatPeople.length; i++) { //check if all names are of real users
                if(! (managerPanel.getOnlineNames().contains(chatPeople[i]) || managerPanel.getOfflineNames().contains(chatPeople[i]))){
                    valid = false;
                    break;
                }
            }
            if(valid){ // create a new chat and ad it to the display
                Arrays.sort(chatPeople);
                chats.add(new Chat(chats.size() + 1,chatPeople));
                managerPanel.setError("");
                managerPanel.setParticipantNames("");
                managerPanel.addChat(chats.get(chats.size() - 1));
            }else{
                managerPanel.setError("One or more incorrect usernames");
            }
        }
    }

    /** OpenChatButtonListener
     *  listener to open the desired chat on button click
     * @author Valerie Fernandes
     */
    class OpenChatButtonListener implements ActionListener{

        /** actionPerformed
         *  attempts to open a chat through the given id
         * @param event an ActionEvent
         */
        public void actionPerformed(ActionEvent event)  {
            chatId = 0;
            try {
                 chatId = Integer.parseInt(managerPanel.getOpenField());
            }catch (Exception e) {
                System.out.println("Not an Integer");
            }

            if(chatId <= chats.size() && chatId > 0){ // Display chat information and adjust id number
                chatFrame.setMessages(chats.get(chatId - 1).getMessages());
                chatFrame.setChatId(chatId);
                managerPanel.setOpenError("");
                chatFrame.repaint();
                chatFrame.revalidate();
            }else{
                managerPanel.setOpenError("Invalid chat id");
            }
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