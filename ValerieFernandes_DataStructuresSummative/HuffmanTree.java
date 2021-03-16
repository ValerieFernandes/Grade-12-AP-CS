/**
 * Creates a Huffman Tree
 * @author Valerie Fernandes
 */
public class HuffmanTree{

    /**
     * a HuffmanTreeNode representing the root of the tree
     */
    private HuffmanTreeNode root;

    /**
     * Creates a HuffmanTree based on the information given by the string representation of the tree
     * @param treeString a String representing the values and structure of the HuffmanTree
     */
    HuffmanTree(String treeString){
        boolean fillRight;
        String intString;
        SimpleStack<HuffmanTreeNode> parents = new SimpleStack<HuffmanTreeNode>();

        this.root = new HuffmanTreeNode(null, null, null);
        treeString = treeString.substring(1);
        parents.push(this.root);

        while(parents.size() != 0){
            HuffmanTreeNode currentNode = parents.pop();

            fillRight = false;
            if(treeString.charAt(0) == ' '){ // after spaces the right child is filled
                treeString = treeString.substring(1);
                fillRight = true;
            }

            if((Character.isDigit(treeString.charAt(0))) || (treeString.charAt(0) == '-')) { // create digit leaf
                intString = "";
                do {
                    intString  += treeString.substring(0, 1);
                    treeString = treeString.substring(1);
                }while(Character.isDigit(treeString.charAt(0))); // get and trim the integer from the tree string

                if(!fillRight) { // fill the desired child and push node back into stack
                    currentNode.setLeft(new HuffmanTreeNode(Integer.parseInt(intString), null, null));
                    parents.push(currentNode);
                }else{
                    currentNode.setRight(new HuffmanTreeNode(Integer.parseInt(intString), null, null));
                    parents.push(currentNode);
                }

            }else if(treeString.charAt(0) == '('){ //attach new branch to the tree
                treeString = treeString.substring(1);
                if(!fillRight){ // add new empty child and push both self and child into stack
                    currentNode.setLeft(new HuffmanTreeNode(null, null, null));
                    parents.push(currentNode); //push parent
                    parents.push(currentNode.getLeft());

                }else{
                    currentNode.setRight(new HuffmanTreeNode(null, null, null));
                    parents.push(currentNode);
                    parents.push(currentNode.getRight());
                }

            }else if(treeString.charAt(0) == ')'){ //Move upwards for next rotation by not pushing node back into the stack
                treeString = treeString.substring(1);
            }
        }
    }

    /**
     * Gets and returns the root of the tree
     * @return a HuffmanTreeNode representing the root of the tree
     */
    public HuffmanTreeNode getRoot(){
        return this.root;
    }

}
