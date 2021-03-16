import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.Color;

/** LoginPanel
 *  represents a panel to login to chat service
 * @author Valerie Fernandes
 */
class LoginPanel extends JPanel{

    /** JButtons for quitting, attempting login, accessing settings */
    private JButton clearButton, loginButton, createButton, settingsButton;
    /** JTextFields for filling in username/password */
    private JTextField usernameField, passwordField;
    /** JLabel for notifying about errors */
    private JLabel errorLabel;
    /** JComboBox for selecting the type of the user */
    private JComboBox typeBox;

    /** LoginPanel
     *  constructs a LoginPanel object
     * @param settingsListener a listener for opening settings
     * @param loginListener a listener for logging in
     * @param createListener a listener for creating a new account
     */
    LoginPanel(ActionListener settingsListener, ActionListener loginListener, ActionListener createListener) {

        this.setBackground(new Color(197, 237, 204));
        this.setLayout(null);

        createButton = new JButton("Create Account");
        createButton.addActionListener(createListener);
        createButton.setBounds(500, 325, 125, 30);

        loginButton = new JButton("Log In");
        loginButton.addActionListener(loginListener);
        loginButton.setBounds(500, 275, 125, 30);

        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(settingsListener);
        settingsButton.setBounds(675, 10, 100, 30);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(125, 260, 100, 30);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(125, 300, 100, 30);
        JLabel typeLabel = new JLabel("I am a...");
        typeLabel.setBounds(125, 340, 100, 30);

        errorLabel = new JLabel("Not Connected; Go to Settings");
        errorLabel.setBounds(310, 440, 300, 30);

        usernameField = new JTextField(1);
        usernameField.setBounds(200, 260, 200, 30);
        passwordField = new JTextField(1);
        passwordField.setBounds(200, 300, 200, 30);
        typeBox = new JComboBox(new String[]{"Student", "Teacher"});
        typeBox.setBounds(200, 340, 200, 30);

        try {
            BufferedImage logo = ImageIO.read(new File("FinalLogo.png")); // load the image logo
            JLabel picture = new JLabel(new ImageIcon(logo));
            picture.setBounds(20, 100, 800, 500);
            this.add(picture);

        } catch (IOException e) {
            System.out.println("Logo Image not found");
        }

        this.add(loginButton);
        this.add(createButton);
        this.add(settingsButton);
        this.add(usernameLabel);
        this.add(passwordLabel);
        this.add(typeLabel);
        this.add(usernameField);
        this.add(passwordField);
        this.add(typeBox);
        this.add(settingsButton);
        this.add(errorLabel);
    }

    /** getUsername
     * gets and returns the content of the username field
     * @return a String representing the username
     */
    public String getUsername(){
        return this.usernameField.getText();
    }

    /** getPassword
     * gets and returns the content of the password field
     * @return a String representing the password
     */
    public String getPassword(){
        return this.passwordField.getText();
    }

    /** getType
     * gets and returns the content of the typeBox
     * @return a String representing the user type
     */
    public String getType(){
        return (String)this.typeBox.getSelectedItem();
    }

    /** setLoginError
     *  sets the login error message to the specified value
     * @param error a String representing the error message
     */
    public void setLoginError(String error){
        this.errorLabel.setText(error);
    }
}
