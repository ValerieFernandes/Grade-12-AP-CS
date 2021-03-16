/** WordTreeNode
 * represents a node which is part of a WordTree
 * @author Valerie Fernandes
 */
public class WordTreeNode {

    /** the WordTreeNode that is the left child of this node */
    private WordTreeNode left;
    /** the WordTreeNode that is the right child of this node */
    private WordTreeNode right;
    /** the item contained in the node */
    private String item;

    /** WordTreeNode
     * Creates a WordTreeNode object with the following parameters
     * @param left WordTreeNode that is the left child of this node
     * @param right WordTreeNode that is the right child of this node
     * @param item the String contained in the node
     */
    public WordTreeNode(WordTreeNode left, WordTreeNode right, String item) {
        this.left = left;
        this.right = right;
        this.item = item;
    }

    /** getLeft
     * gets and returns the left child of this node
     * @return WordTreeNode representing the left child
     */
    public WordTreeNode getLeft() {
        return this.left;
    }

    /** setLeft
     * sets the left child of this node
     * @param left WordTreeNode representing the left child
     */
    public void setLeft(WordTreeNode left) {
        this.left = left;
    }

    /** getRight
     * gets and returns the right child of this node
     * @return WordTreeNode representing the right child
     */
    public WordTreeNode getRight() {
        return this.right;
    }

    /** setRight
     * sets the right child of this node
     * @param right WordTreeNode representing the right child
     */
    public void setRight(WordTreeNode right) {
        this.right = right;
    }

    /** getItem
     * gets the item contained in the node
     * @return a String representing the node's item
     */
    public String getItem() {
        return this.item;
    }
}
