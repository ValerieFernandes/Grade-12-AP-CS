/** Chat
 *  Represents a Chat between users
 */
public class Chat{
    /** A String[] representing the participants in the chat */
    private String[] participants;
    /** An in integer representing the id number of the chat */
    private int id;
    /** A string containing the messages sent in the chat */
    private String messages;

    /** Chat
     *  constructor for chat object
     * @param id an int representing the
     * @param participants a String[] representing the names of the participants
     */
    Chat(int id, String[] participants){
        this.id = id;
        this.participants = participants;
        this.messages = "";
    }

    /** getParticipants
     *  gets and returns the array of participant names
     * @return a String[] representing the participant names
     */
    public String[] getParticipants(){
        return this.participants;
    }

    /** getId
     * gets and returns the id number of the chat
     * @return a int representing the id number
     */
    public int getId(){
        return this.id;
    }

    /** getMessages
     * gets and returns all the chat messages
     * @return a String representing messages pertaining to the chat
     */
    public String getMessages(){
        return this.messages;
    }

    /** addMessage
     *  adds a new message to the chat
     * @param sender the name of the user who sent the message
     * @param content the content of the message
     */
    public void addMessage(String sender, String content){
        this.messages = this.messages + sender + ":" + "\n" + content + "\n";
    }
}
