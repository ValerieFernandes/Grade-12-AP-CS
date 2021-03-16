import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;

/** QuestionPanel
 * represents a panel on which to display a question as part of a practice session
 * @author Valerie Fernandes
 */
public class QuestionPanel extends JPanel{
    /** JLabels for the question content, verb question, and vocab question */
    private JLabel questionLabel, verbLabel, vocabLabel, numberLabel;
    /** JComboBox for the potential verb answers */
    private JComboBox answerList;
    /** JTextField for the vocabulary answer */
    private JTextField answerField;
    /** JButtons for submitting answers and moving to the next question */
    private JButton submitButton, nextButton;
    /** the Question object being presented */
    private Question question;

    /** QuestionPanel
     * constructs a QuestionPanel object
     * @param submitListener listener for submitting answers
     * @param nextListener listener for moving to next question
     * @param question the Question being displayyed
     */
    QuestionPanel(ActionListener submitListener, ActionListener nextListener, Question question){
        this.question = question;
        this.setBackground(new Color(197, 237, 204));
        this.setLayout(null);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(submitListener);
        submitButton.setBounds(630, 350, 80, 30);

        nextButton = new JButton("Next");
        nextButton.addActionListener(nextListener);
        nextButton.setBounds(630, 410, 80, 30);

        questionLabel = new JLabel(question.getQuestion());
        questionLabel.setFont(new Font("serif", Font.PLAIN, 30));
        questionLabel.setBounds(100, 150, 900, 40);

        verbLabel = new JLabel("What is the correct conjugation of the verb");
        verbLabel.setBounds(100, 350, 300, 30);

        answerList = new JComboBox(this.question.getOptionList());
        answerList.setBounds(400, 350, 100, 30);

        numberLabel = new JLabel("1");
        numberLabel.setFont(new Font("serif", Font.PLAIN, 30));
        numberLabel.setBounds(25, 25, 100, 40);

        String[] vocab = question.getVocabAnswer().split(" ");
        vocabLabel = new JLabel("What is the english meaning of " + vocab[vocab.length - 2]);
        vocabLabel.setBounds(100, 410, 300, 30);
        answerField = new JTextField(1);
        answerField.setBounds(400, 410, 100, 30);

        this.add(submitButton);
        this.add(nextButton);
        this.add(questionLabel);
        this.add(numberLabel);
        this.add(verbLabel);
        this.add(vocabLabel);
        this.add(answerField);
        this.add(answerList);
    }

    /** setQuestion
     * resets the question which is being displayed on the panel
     * @param question the Question to be displayed
     */
    public void setQuestion(Question question){

        this.question = question;
        questionLabel.setText(question.getQuestion());

        this.remove(answerList); // put new answer list
        answerList = new JComboBox(this.question.getOptionList());
        answerList.setBounds(400, 350, 100, 30);
        this.add(answerList);

        String[] vocab = question.getVocabAnswer().split(" ");
        vocabLabel.setText("What is the english meaning of " + vocab[vocab.length - 2]);
        answerField.setText(""); // clear the answer field

        this.numberLabel.setText(Integer.toString(Integer.parseInt(this.numberLabel.getText()) + 1)); // increment question number by one
    }

    /** displayAnswer
     * display the correct answers for the question
     */
    public void displayAnswer(){

        this.verbLabel.setText("The answer is " + this.question.getVerbAnswer());
        String[] vocab = this.question.getVocabAnswer().split(" ");
        this.vocabLabel.setText("The answer is " + vocab[vocab.length - 1]);
    }

    /** getVerbScore
     * calculates and returns the verb score achieved on the question
     * @return an int representing the score obtained
     */
    public int getVerbScore(){
        if((this.answerList.getSelectedItem()).equals(question.getVerbAnswer())){
            return question.getVerb();
        }
        return question.getVerb() - 1; // reduce by a level if incorrect
    }

    /** getVocabScore
     * calculates and returns the vocab score achieved on the question
     * @return an int representing the score obtained
     */
    public int getVocabScore(){
        String[] vocab = question.getVocabAnswer().split(" ");

        if((this.answerField.getText()).equalsIgnoreCase(vocab[vocab.length - 1])){
            return question.getVocab();
        }
        return question.getVocab() - 1; // reduce by a level if incorrect
    }

    /** getQuestionNumber
     * gets and returns the number of the current question
     * @return an int representing the question number
     */
    public int getQuestionNumber(){
        return Integer.parseInt(this.numberLabel.getText());
    }

    /** getQuestion
     * gets and returns the current question
     * @return a Question representing the question currently being displayed
     */
    public Question getQuestion(){
        return this.question;
    }

    /** changeSubmitStatus
     * enables or disables the submit button according to boolean
     * @param status true or false for enabling/disabling the button
     */
    public void changeSubmitStatus(boolean status){
        this.submitButton.setEnabled(status);
    }
}
