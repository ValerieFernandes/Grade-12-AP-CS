/** WordTree
 * a tree of words in which paths form sentences
 * @author Valerie Fernandes
 */
public class WordTree {

    /** a WordTreeNode representing the root of the tree */
    private WordTreeNode root;

    /** WordTree
     * Creates a WordTree object
     * @param nodeString the word String to be contained in the root
     */
    WordTree(String nodeString){
        this.root = new WordTreeNode(null, null, nodeString);
    }

    /** getRoot
     * gets and returns the root of the tree
     * @return a WordTreeNode representing the root of the tree
     */
    public WordTreeNode getRoot(){
        return this.root;
    }
}
