import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;

/** ManagerPanel
 *  represents a panel to manage a  user's chats
 * @author Valerie Fernandes
 */
class ManagerPanel extends JPanel {
    /** JButtons for quitting, creating chats, and opening chats */
    private JButton clearButton, chatButton, openButton;
    /** JTextAreas for user names and chat names */
    private JTextArea names, chatNames;
    /** JLabels for instructions and error messages for creating and opening chats */
    private JLabel instructionsLabel, errorLabel, openLabel, openErrorLabel;
    /** JTextFields for entering participant names and chat id names */
    private JTextField participantNames, openField;
    /** Strings representing online user names, offline user names and names of chats */
    private String onlineNames, offlineNames, chatTitles;

    /** ManagerPanel
     *  constructs a ManagerPanel object
     * @param picture the duberChat logo
     * @param online the array of current online users
     * @param offline the array of current offline users
     * @param quitListener a listener for the quit button
     * @param newChatListener a listener for the new chat button
     * @param openChatListener a listener for the open chat button
     */
    ManagerPanel(JLabel picture, String[] online, String[] offline, ActionListener quitListener, ActionListener newChatListener, ActionListener openChatListener) {
        this.setBackground(new Color(25, 150, 199));
        this.setLayout(null);

        picture.setBounds(-20, -40, 400, 400);

        int users = online.length + offline.length;
        onlineNames = "Online: \n";
        for(int i=0; i<online.length; i++){
            onlineNames = onlineNames + online[i] +"\n";
        }

        offlineNames = "\n Offline: \n";
        for(int i=0; i<offline.length; i++){
            offlineNames = offlineNames + offline[i] +"\n";
        }

        names = new JTextArea(onlineNames + offlineNames); // left half of panel is used for user list/ creating chat
        names.setFont(new Font("Cambria", Font.BOLD, 15));
        names.setBackground(new Color(223, 237, 242));
        names.setBounds(30, 80, 150, 25 * users + 30);
        instructionsLabel = new JLabel("Enter names separated by spaces");
        instructionsLabel.setBounds(5, 25 * users + 120, 200, 30);
        errorLabel = new JLabel(""); //One or more incorrect usernames
        errorLabel.setBounds(5, 25 * users + 180, 250, 30);

        chatTitles = ""; // right half of the panel is used for chat id names and opening chats
        chatNames = new JTextArea();
        chatNames.setFont(new Font("Cambria", Font.BOLD, 15));
        chatNames.setBackground(new Color(223, 237, 242));
        chatNames.setBounds(230, 80, 130, 180);
        openLabel = new JLabel("Enter the number of a chat");
        openLabel.setBounds(225, 270, 200, 30);
        openErrorLabel = new JLabel(""); //Invalid chat id
        openErrorLabel.setBounds(245, 330, 250, 30);

        clearButton = new JButton("Quit");
        clearButton.addActionListener(quitListener);
        clearButton.setBounds(310, 0, 70, 30);

        participantNames = new JTextField(1);
        participantNames.setBounds(5, 25* users + 150, 120, 30);
        chatButton = new JButton("New Chat");
        chatButton.addActionListener(newChatListener);
        chatButton.setBounds(130, 25 * users + 150, 90, 30);

        openField = new JTextField(1);
        openField.setBounds(235, 300, 50, 30);
        openButton = new JButton("Open");
        openButton.addActionListener(openChatListener);
        openButton.setBounds(290, 300, 90, 30);

        this.add(openButton);
        this.add(openErrorLabel);
        this.add(openField);
        this.add(openLabel);
        this.add(chatNames);
        this.add(participantNames);
        this.add(errorLabel);
        this.add(instructionsLabel);
        this.add(names);
        this.add(picture);
        this.add(clearButton);
        this.add(chatButton);
    }

    /** getOnlineNames
     *  gets and returns the array of online user names
     * @return a String[] representing the online user names
     */
    public String getOnlineNames() {
        return this.onlineNames;
    }

    /** setOnlineNames
     * updates the online names to the new given values
     * @param onlineNames a String contain the names of online users
     */
    public void setOnlineNames(String onlineNames) {
        this.onlineNames = onlineNames;
        names.setText(this.onlineNames + this.offlineNames);
    }

    /** getOfflineNNames
     *  get and return the names of offline users
     * @return a String containing the names of the offline users
     */
    public String getOfflineNames() {
        return this.offlineNames;
    }

    /** setOfflineNames
     * updates the offline names to the new given values
     * @param offlineNames a String containing the names of the offline users
     */
    public void setOfflineNames(String offlineNames) {
        this.offlineNames = offlineNames;
        names.setText(this.onlineNames + this.offlineNames);
    }

    /** getParticipantNames
     *  gets and returns the names of the new chat participants
     * @return a String containing the participant names
     */
    public String getParticipantNames(){
        return this.participantNames.getText();
    }

    /** setParticipantNames
     *  updates the value of the participant names field
     * @param newNames a String representing the new name list
     */
    public void setParticipantNames(String newNames){
        this.participantNames.setText(newNames);
    }

    /** setError
     * updates the value of the new chat error label
     * @param error the error message to be displayed
     */
    public void setError(String error){
        this.errorLabel.setText(error);
    }

    /** getOpenField
     *  gets and returns the text from the open chat field
     * @return A string representing the chat to be opened
     */
    public String getOpenField(){
        return this.openField.getText();
    }

    /** setOpenError
     *  updates the value of the open chat error label
     * @param error the error message to be displayed
     */
    public void setOpenError(String error){
        this.openErrorLabel.setText(error);
    }

    /** addChat
     * Adds a new chat to the list of chat names
     * @param chat
     */
    public void addChat(Chat chat) {
        String chatTitle = chat.getId() + ".";
        for(int i=0; i<chat.getParticipants().length; i++){
            chatTitle = chatTitle + " " + chat.getParticipants()[i];
        }
        chatTitles = chatTitles + chatTitle +"\n";
        chatNames.setText(chatTitles);
    }
}