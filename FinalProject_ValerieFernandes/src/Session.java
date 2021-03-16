import java.io.PrintWriter;
import java.util.ArrayList;

/** Session
 * represents a french languaage practice session of questions
 * @author Valerie Fernandes
 */
public class Session {

    /** the sentence structure level of the session */
    private int structure;
    /** the vocabulary level of the session */
    private int vocab;
    /** the verb difficulty level of the session */
    private int verb;
    /** the Questions which make up the session */
    private ArrayList<Question> questions = new ArrayList<Question>();

    Session(int vocab, int structure, int verb, QuestionDatabase questionDatabase){
        this.vocab = vocab;
        this.structure = structure;
        this.verb = verb;

        if(structure != 0) { // zero scores only occur in non instantiated people
            for (int i = 0; i < 10; i++) { // add questions of the persons level
                this.questions.add(questionDatabase.developQuestion(vocab, structure, verb));
            }

            if (structure >= 10) { // prevent any score at 10 from going out of bounds
                structure--;
            }

            if (vocab >= 10) {
                vocab--;
            }

            if (verb >= 10) {
                verb--;
            }
            for (int i = 0; i < 10; i++) { // add questions at one level above current
                this.questions.add(questionDatabase.developQuestion(structure + 1, vocab + 1, verb + 1));
            }

        }else{ // generate a diagnostic session
            for(int i=2; i<22; i++){ // genrate two questions with each level
                this.questions.add(questionDatabase.developQuestion(i/2, i/2, i/2));
            }
        }
    }

    /** sendSessionInfo
     * outputs the information of the session questions
     * @param output the PrintWriter to which the output goes
     */
    public void sendSessionInfo(PrintWriter output) {
        output.println("Session");
        for(Question question : this.questions){ // loop through each question and send all the pertaining info
            output.println(question.getQuestion());
            output.println(question.getVocabAnswer());
            output.println(question.getVerbAnswer());
            output.println(question.getOptions());
            output.println(question.getVocab());
            output.println(question.getStructure());
            output.println(question.getVerb());
        }
        output.flush();
    }
}
