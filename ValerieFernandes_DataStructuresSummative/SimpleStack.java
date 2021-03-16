/**
 * Creates a stack with simple methods
 * @author Valerie Fernandes
 */
class SimpleStack<E> {

    /**
     * a StackNode representing the head of the stack
     */
    private StackNode<E> head;

    /**
     * This method pushes a new item to the top of the stack
     * @param item the item to be pushed into the stack
     */
    public void push(E item){
        StackNode<E> tempNode;

        if(this.head == null){
            this.head = new StackNode<E>(item, null);
        }else{
            tempNode = this.head;
            this.head = new StackNode<E>(item, tempNode);
        }
    }

    /**
     * This method gets and returns the item at the head of the queue
     * @return the E item at the head of the queue
     */
    public E pop(){
        E item = this.head.getItem();
        this.head = this.head.getNext();
        return item;
    }

    /**
     * This method counts and returns the size of the queue
     * @return an int representing the size of the queue
     */
    public int size() {
        int size = 1;
        StackNode<E> tempNode = head;

        if(head == null){ // if there is no  head the stack is empty
            return 0;
        }

        while (tempNode.getNext() != null){
            tempNode = tempNode.getNext();
            size++;
        }
        return size;
    }
}