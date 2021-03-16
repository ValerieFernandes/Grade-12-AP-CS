/**
 * Creates a node for a Huffman Tree object
 * @author Valerie Fernandes
 */
public class HuffmanTreeNode{

    /**
     * the left child of the node
     */
    private HuffmanTreeNode left;

    /**
     * the right child of the node
     */
    private HuffmanTreeNode right;

    /**
     * the item contained in the node
     */
    private Integer item;

    /**
     * Creates a new HuffmanTreeNode with the following item and children
     * @param item an Integer representing the item contained in the node
     * @param left a HuffmanTreeNode representing the left child of the node
     * @param right a HuffmanTreeNode representing the right child of the node
     */
    HuffmanTreeNode(Integer item, HuffmanTreeNode left, HuffmanTreeNode right){
        this.item = item;
        this.left = left;
        this.right = right;
    }

    /**
     * Gets and returns the left child
     * @return a HuffmanTreeNode representing the left child of the node
     */
    public HuffmanTreeNode getLeft() {
        return this.left;
    }

    /**
     * Sets the left child as the desired node
     * @param left a HuffmanTreeNode to be assigned as the left child of the node
     */
    public void setLeft(HuffmanTreeNode left) {
        this.left = left;
    }

    /**
     * Gets and returns the right child
     * @return a HuffmanTreeNode representing the right child of the node
     */
    public HuffmanTreeNode getRight() {
        return this.right;
    }

    /**
     * Sets the right child as the desired node
     * @param right a HuffmanTreeNode to be assigned as the right child of the node
     */
    public void setRight(HuffmanTreeNode right) {
        this.right = right;
    }

    /**
     * Gets and returns the item
     * @return the Integer contained in the node
     */
    public Integer getItem() {
        return item;
    }
}
