import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Color;

/** SettingsPanel
 *  represents a panel of server settings
 * @author Valerie Fernandes
 */
class SettingsPanel extends JPanel {
    /** JButtons for closing the panel, and connecing to server */
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
        this.setBackground(new Color(25, 150, 199));
        this.setLayout(null);

        closeButton = new JButton("X");
        closeButton.addActionListener(closeListener);
        closeButton.setBounds(330, 10, 50, 30);

        connectButton = new JButton("Connect");
        connectButton.addActionListener(connectListener);
        connectButton.setBounds(150, 225, 100, 30);

        addressLabel = new JLabel("IP Address");
        addressLabel.setBounds(75, 130, 100, 30);
        portLabel = new JLabel("Port");
        portLabel.setBounds(105, 170, 80, 30);
        connectedLabel = new JLabel("");
        connectedLabel.setBounds(160, 300, 80, 30);

        addressField = new JTextField(1);
        addressField.setBounds(150, 130, 150, 30);
        portField = new JTextField(1);
        portField.setBounds(150, 170, 150, 30);

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