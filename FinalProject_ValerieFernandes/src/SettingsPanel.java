import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;

/** SettingsPanel
 *  represents a panel of server settings
 * @author Valerie Fernandes
 */
class SettingsPanel extends JPanel {
    /** JButtons for closing the panel, and connecting to server */
    private JButton closeButton, connectButton;
    /** JTextFields for filling in a server address and port */
    private JTextField addressField, portField;
    /** JLabels describing address, port, and connection status */
    private JLabel addressLabel, portLabel, connectedLabel;

    /** SettingsPanel
     * constructs a SettingsPanel objects
     * @param closeListener a listener for quit button
     * @param connectListener a listener for the connect button
     */
    SettingsPanel(ActionListener closeListener, ActionListener connectListener) {
        this.setBackground(new Color(197, 237, 204));
        this.setLayout(null);

        closeButton = new JButton("X");
        closeButton.addActionListener(closeListener);
        closeButton.setBounds(700, 25, 50, 30);

        connectButton = new JButton("Connect");
        connectButton.addActionListener(connectListener);
        connectButton.setBounds(350, 355, 100, 30);

        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("serif", Font.PLAIN, 40));
        titleLabel.setBounds(330, 100, 200, 50);

        addressLabel = new JLabel("IP Address");
        addressLabel.setBounds(225, 250, 100, 30);
        portLabel = new JLabel("Port");
        portLabel.setBounds(255, 300, 80, 30);

        connectedLabel = new JLabel("Not Connected");
        connectedLabel.setBounds(365, 460, 100, 30);

        addressField = new JTextField(1);
        addressField.setBounds(320, 250, 160, 30);
        portField = new JTextField(1);
        portField.setBounds(320, 300, 160, 30);

        this.add(titleLabel);
        this.add(connectedLabel);
        this.add(closeButton);
        this.add(connectButton);
        this.add(addressField);
        this.add(portField);
        this.add(addressLabel);
        this.add(portLabel);
    }

    /** getAddress
     *  gets and returns the text from the address field
     * @return a String representing the text of the address field
     */
    public String getAddress(){
        return this.addressField.getText();
    }

    /** getPort
     *  gets and returns the text from the port field
     * @return a String representing the text of the port field
     */
    public String getPort(){
        return this.portField.getText();
    }

    /** setConnected
     *  sets the server status to connected
     */
    public void setConnected(){
        connectedLabel.setText("Connected");
    }
}