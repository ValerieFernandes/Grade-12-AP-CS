import java.io.PrintWriter;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** ChatFrame
 *  represents a frame for displaying chats
 * @author Valerie Fernandes
 */
public class ChatFrame extends JFrame {
    private PrintWriter output;  //printwriter for network output
    private ArrayList<Chat> chats = new ArrayList<Chat>();
    private JButton sendButton;
    private JTextField typeField;
    private JTextArea msgArea;
    private JPanel southPanel;
    private JLabel chatLabel;
    private int chatId = 0;

    /** ChatFrame
     *  constructs a new ChatFrame object
     * @param output a PrintWriter for sending messages to the chat
     * @param chats an ArrayList of Chat objects
     */
    ChatFrame(PrintWriter output, ArrayList<Chat> chats) {
        this.output = output;
        this.chats = chats;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2, 0));

        sendButton = new JButton("SEND");
        sendButton.addActionListener(new SendButtonListener());

        typeField = new JTextField(10);

        msgArea = new JTextArea();
        msgArea.setBackground(new Color(223, 237, 242));
        chatLabel = new JLabel("");
        southPanel.add(typeField);
        southPanel.add(sendButton);
        southPanel.add(chatLabel);


        this.add(BorderLayout.CENTER, msgArea);
        this.add(BorderLayout.SOUTH, southPanel);

        this.setSize(400, 400);
        this.setVisible(true);
    }

    /** updateMessages
     *  adds a new message to the display area
     * @param user the name of the user who sent the message
     * @param message the content of the message
     */
    public void updateMessages(String user, String message) {
        msgArea.append(user + ":" + "\n");
        msgArea.append(message + "\n");
    }

    /** getMessage
     *  returns and clears the type field
     * @return a String containing the message to be sent
     */
    public String getMessage(){
        String message = typeField.getText();
        typeField.setText("");
        return message;
    }

    /** setMessages
     * sets the message area to a certain text
     * @param message the message displayed in the area
     */
    public void setMessages(String message){
        this.msgArea.setText(message);
    }

    /** setChatId
     *  sets the id of the chat to the given number
     * @param id the id number to set the chat to
     */
    public void setChatId(int id){
        this.chatId = id;
        this.chatLabel.setText("Chat: " + id);
    }

    /** getChatId
     *  gets and returns the id of the current chat
     * @return an int representing the id of the current chat
     */
    public int getChatId(){
        return this.chatId;
    }

    /** SendButtonListener
     *  a listener to send messages
     * @author Valerie Fernandes
     */
    class SendButtonListener implements ActionListener {

        /** actionPerformed
         *  sends the message from the field to the server
         * @param event
         */
        public void actionPerformed(ActionEvent event)  {
            if(chatId > 0) {
                output.println("Message");
                String recipients = "";

                for(int i=0; i<chats.get(chatId - 1).getParticipants().length; i++){
                    recipients = recipients + chats.get(chatId - 1).getParticipants()[i] + " ";
                }

                output.println(recipients);
                output.println(getMessage());
                output.flush();
            }
        }
    }
}