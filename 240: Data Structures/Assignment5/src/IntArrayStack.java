import java.util.EmptyStackException;

/**
 * A stack of Integers implemented using an array
 * Invariant of the ArrayStack Class:
 * 1- The number of items in the stack is stored in the instance variable top
 * 2- The items in the stack are stored in a partially filled array called data
 * with the bottom of the stack at data[0], the nest item is at data[1], and so on, and the top of the stack 
 * at data[top-1]
 * @author Thanaa Ghanem
 *
 */


public class IntArrayStack implements IntStackInterface {


    private int top;
    private int[] data;

    /** no-arguments default constructor creates an empty stack 
     * 	default size is set to 10
     */
    public IntArrayStack() {
        this.top = 0;		// empty stack
        this.data = new int[10];	// always have room for at least 10 items
    }

    /** A constructor to create a stack with an initial capacity 
     * 	@param intialCap - initial capacity to the 
     */
    public IntArrayStack(int initialCap) {
        this.top = 0;		// empty stack
        this.data = new int[initialCap];	// always have room for at least 10 items
    }

    /**
     *  @return	true if the stack is empty and false otherwise
     */
    public boolean isEmpty() {
        return (top == 0);
    }

    /** Add a new value to the school
     * @param	value to push onto the stack 
     * */
    public void push(int value) {

        if (this.data.length == top ) {
            int[] newArray = new int[this.data.length * 2];
            System.arraycopy(data, 0, newArray, 0, this.data.length);
            this.data = newArray;
        }
        this.data[this.top] = value;
        this.top++;
    }

    /**
     * @return	the top value on the stack 
     * */
    public int pop() {

        if (isEmpty()) {
            // EmptyStackException is from java.util and its constructor has no argument.
            throw new EmptyStackException( );
        }
        this.top--;
        return this.data[this.top];
    }

    /**
     * @return the top item of the stack without removing it
     * @exception EmptyStackException Indicates that this stack is empty.
     **/
    public int peek( )   {
        if ( top == 0)
            // EmptyStackException is from java.util and its constructor has no argument.
            throw new EmptyStackException( );
        return this.data[top-1];
    }


    /** convert the stack to a printable string
     * @return	a string representing the stack
     */
    public String toString() {
        String output = "[ ";

        for (int i= this.top-1; i >= 0; i--)
            output += this.data[i]+"\t";

        output  += " ] \n";
        return output;
    }

    /**
     * Accessor method to determine the number of items in this stack.
     * @return the number of items in this stack
     * **/

    public int size(){
        return top;
    }




}