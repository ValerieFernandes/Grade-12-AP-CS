import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/** Question
 * Represents a question object, with answers and ranking
 * @author Valerie Fernandes
 */
public class Question {

    /** the vocabulary level of the question */
    private int vocab;
    /** the sentence structure level of the question */
    private int structure;
    /** the verb difficulty level of the question */
    private int verb;
    /** the question being asserted */
    private String question;
    /** the answer to the vocabulary section of the question */
    private String vocabAnswer;
    /** the answer to the verb section of the question */
    private String verbAnswer;
    /** the options for the verb answer*/
    private String options;

    /** Question
     * creates a question with the following attributes
     * @param vocab an int representing the vocabulary level
     * @param structure an int representing the sentence structure level
     * @param verb an int representing the verb difficulty level
     * @param question the content of the question
     * @param vocabAnswer the answer to the vocabulary question
     * @param verbAnswer the answer to the verb question
     * @param options the options for the verb answer
     */
    public Question(int vocab, int structure, int verb, String question, String vocabAnswer, String verbAnswer, String options) {
        this.vocab = vocab;
        this.structure = structure;
        this.verb = verb;
        this.question = question;
        this.vocabAnswer = vocabAnswer;
        this.verbAnswer = verbAnswer;
        this.options = options;
    }

    /** getQuestion
     * gets and returns the question contents
     * @return a String representing the question
     */
    public String getQuestion() {
        return this.question;
    }

    /** getVocab
     * gets and returns the vocabulary level
     * @return an int representing the vocabulary level
     */
    public int getVocab() {
        return this.vocab;
    }

    /** getStructure
     * gets and returns the sentence structure level
     * @return an int representing the sentence structure level
     */
    public int getStructure() {
        return this.structure;
    }

    /** getVerb
     * gets and returns the verb difficulty level
     * @return an int representing the verb difficulty level
     */
    public int getVerb() {
        return this.verb;
    }

    /** getVocabAnswer
     * gets and returns the vocabulary answer
     * @return a String representing the answer to the vocab question
     */
    public String getVocabAnswer() {
        return this.vocabAnswer;
    }

    /** getVerbAnswer
     * gets and returns the verb answer
     * @return a String representing the answer to the verb question
     */
    public String getVerbAnswer() {
        return this.verbAnswer;
    }

    /** getOptions
     * gets and returns the options for the verb answer
     * @return a String representing the options for the answer to the verb question
     */
    public String getOptions() {
        return this.options;
    }

    /** getOptionList
     * gets and returns an array of the options for the verb answer
     * @return a Object[] representing the options for the answer to the verb question
     */
    public Object[] getOptionList() {
        List<String> optionList = Arrays.asList(this.options.split(" "));
        Collections.shuffle(optionList);
        optionList = new ArrayList<>(new HashSet<String>(optionList));
        return optionList.toArray();
    }
}
