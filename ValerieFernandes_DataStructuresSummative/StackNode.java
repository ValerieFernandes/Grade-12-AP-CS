/**
 * Creates a node for a stack
 * @author Valerie Fernandes
 */
class StackNode<T> {

    /**
     * the item contained in the node
     */
    private T item;

    /**
     * the next node down in the stack
     */
    private StackNode<T> next;

    /**
     * Creates a new StackNode with the given parameters
     * @param item the item contained in the node
     * @param next the node following this node
     */
    public StackNode(T item, StackNode<T> next) {
        this.item=item;
        this.next=next;
    }

    /**
     * Gets and returns the next node
     * @return a StackNode representing the next node
     */
    public StackNode<T> getNext(){
        return this.next;
    }

    /**
     * Sets the next node to the desired value
     * @param next a StackNode representing the next node
     */
    public void setNext(StackNode<T> next){
        this.next = next;
    }

    /**
     * Gets and returns the item of this node
     * @return the item contained in the node
     */
    public T getItem(){
        return this.item;
    }

}