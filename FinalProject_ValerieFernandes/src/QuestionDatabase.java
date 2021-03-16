import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/** QuestionDatabase
 * represents a database which holds words to create french sentence questions
 * @author Valerie Fernandes
 */
public class QuestionDatabase {

    /** the potential person of a sentence */
    String[] people;
    /** the potential verbs in a sentence */
    String[] verbs;
    /** trees representing the word paths to create sentences */
    HashMap<String, WordTree> wordPaths = new HashMap<String, WordTree> ();

    /** QuestionDatabase
     * Creates a Question database which has the capacity to generate french language questions
     */
    QuestionDatabase(){
        File dataFile = new  File("WordTrees.txt");
        String line;
        String root;
        WordTreeNode node;
        String[] info;

        try {
            Scanner input = new Scanner(dataFile);
            people = input.nextLine().split(","); // fill people and verb arrays
            verbs = input.nextLine().split(" ");
            input.nextLine();

            for(String verb : verbs){ // a tree is created for each unique verb
                root = input.nextLine();
                wordPaths.put(verb, new WordTree(root)); // put the tree in a map with the verb as the key

                while (input.hasNextLine()){
                    line = input.nextLine();

                    if(line.equals("")){
                        break;
                    }

                    info = line.split(","); // represents one sentence path along the word tree
                    node = wordPaths.get(verb).getRoot();

                    while(node.getLeft() != null){ // find the first empty left node
                        node = node.getLeft();
                    }

                    node.setLeft(new WordTreeNode(null, null, info[0])); // place a sentence object in the node
                    node.setRight(new WordTreeNode(null, null, info[1])); // place the adjective
                    node = node.getRight(); // place the other parts of the path in a subtree rooted at the other child of the object node's parent
                    node.setLeft(new WordTreeNode(null, null, info[2])); // place the second verb/place
                    node.setRight(new WordTreeNode(null, null, info[3])); // place the final adjectivw
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("database does not exist");
        }
    }

    /** developQuestion
     * creates a question using the word paths and the criteria of the parameters
     * @param vocab an int representing the vocabulary level
     * @param structure an int representing the level of sentence structure
     * @param verb an int representing the verb difficulty level
     * @return a Question object which corresponds to the parameters
     */
    public Question developQuestion(int vocab, int structure, int verb){

        String question;
        String options;

        int person = (int) (((vocab + verb) / 2) - Math.round(Math.random())); // the person is a combination of vocab and tense since it affects both

        if(person > 9){ //person are only 0-10 as there are 10 difficulty levels
            person = 9;
        }

        question = this.people[person].split(" ")[0]; // append person to question
        String vocabAnswer = this.people[person]; //make vocab answer person's english translation

        String verbType = this.verbs[(verb - 1) + (int)Math.round(Math.random()*2)]; //pick verb based on level, within a range of three
        WordTree verbTree = wordPaths.get(verbType); // get the word tree corresponding to the chosen verb
        WordTreeNode node = verbTree.getRoot();
        question = question + " (" + verbType + ")"; // add un-conjugated verb to the question
        options = node.getItem();
        String verbAnswer = options.split(" ")[person] ; // get verb conjugation corresponding to chosen person

        if(structure > 2){ //past structure level 2 and object is added
            int level = vocab / 6 + (int) Math.round(Math.random() * 2); // level based on vocab score within a range

            for(int i=0; i< level; i++){ // follow path to chosen object
                node = node.getLeft();
            }

            question = question + " " + node.getLeft().getItem().split(" ")[0]; // add article and object
            question = question + " " + node.getLeft().getItem().split(" ")[1];
            vocabAnswer = node.getLeft().getItem(); // change the vocabulary answer to the latest word

            if(structure > 4){ // past level 4 the object is assigned an adjective
                node = node.getRight();
                question = question + " " + node.getItem().split(" ")[0];
                vocabAnswer = node.getItem(); // change the vocabulary answer to the latest word
            }

            if(structure > 6){ // past level 6 a location or a second verb is added
                question = question + " " + node.getLeft().getItem().split(" ")[0];
                question = question + " " + node.getLeft().getItem().split(" ")[1];
                vocabAnswer = node.getLeft().getItem(); // change the vocabulary answer to the latest word
            }

            if(structure > 8){ // past level 6 the verb/location is given an adjective
                node = node.getRight();
                question = question + " " + node.getItem().split(" ")[0];
                vocabAnswer = node.getItem(); // change the vocabulary answer to the latest word
            }
        }
        question = question + ".";

        return new Question(vocab, structure, verb, question, vocabAnswer, verbAnswer, options); // create and return the developed question
    }
}
