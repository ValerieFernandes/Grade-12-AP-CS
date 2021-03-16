import java.io.PrintWriter;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Color;

/** LoginPanel
 *  represents a panel to login to chat service
 * @author Valerie Fernandes
 */
class LoginPanel extends JPanel{

    /** JButtons for quitting, attempting login, accessing settings */
    private JButton clearButton, loginButton, settingsButton;
    /** JTextFields for filling in username/password */
    private JTextField usernameField, passwordField;
    /** JLabels for identifing username/password fields and errors */
    private JLabel usernameLabel, passwordLabel, errorLabel;

    /** LoginPanel
     *  constructs a LoginPanel object
     * @param picture the duberChat logo
     * @param quitListener a listener for the quit button
     * @param settingsListener a listener for opening settings
     * @param loginListener a listener for logging in
     */
    LoginPanel(JLabel picture, ActionListener quitListener, ActionListener settingsListener, ActionListener loginListener) {
        this.setBackground(new Color(25, 150, 199));
        this.setLayout(null);

        picture.setBounds(-20, 0, 400, 400);

        clearButton = new JButton("Quit");
        clearButton.addActionListener(quitListener);
        clearButton.setBounds(220, 270, 70, 30);

        loginButton = new JButton("Log In");
        loginButton.addActionListener(loginListener);
        loginButton.setBounds(100, 270, 80, 30);

        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(settingsListener);
        settingsButton.setBounds(275, 10, 100, 30);

        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(80, 160, 100, 30);
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(80, 200, 100, 30);
        errorLabel = new JLabel("Not Connected (go to Settings)");
        errorLabel.setBounds(100, 300, 300, 30);

        usernameField = new JTextField(1);
        usernameField.setBounds(150, 160, 150, 30);
        passwordField = new JTextField(1);
        passwordField.setBounds(150, 200, 150, 30);

        this.add(picture);
        this.add(loginButton);
        this.add(usernameLabel);
        this.add(passwordLabel);
        this.add(clearButton);
        this.add(usernameField);
        this.add(passwordField);
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

    /** setLoginError
     *  sets the login error message to the specified value
     * @param error a String representing the error message
     */
    public void setLoginError(String error){
        this.errorLabel.setText(error);
    }
}
