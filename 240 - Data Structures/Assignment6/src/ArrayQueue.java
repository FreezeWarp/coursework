import java.util.NoSuchElementException;

/**
* Invariant of the ArrayQueue class:
*1- The number of items in the queue is stored in the instance variable manyItems
*2- for a non-empty queue, the items are stored in a circular array beginning at data[front] 
*   and continuing through data[rear]
*3- for an empty queue, manyItems is zero and data is a reference to an array, 
*   but we do not care about front and rear
*/

public class ArrayQueue<E> implements QueueInterface<E> {
	
	private Object[] data;
	private int front;
	private int rear;
	private int manyItems;
	
	public ArrayQueue(int cap){	
		this.front = 0;
		this.rear = 0;
		this.manyItems = 0;
		this.data = new Object[cap];
	}
	
	public void add(E item) {
		
		if (this.manyItems == 0){
			this.front = 0;
			this.rear = 0;
		}else{
			rear = nextIndex(rear);
		}
		System.out.println("inserting at "+rear);
		
		this.data[rear] = item;
		this.manyItems++;
		
	}
	/**This method is used to implement the circular array
	 * The method checks if there are empty slots in the array
	 * if rear index reaches length, then the following index will
	 * be the start of the array again
	 * @param i -- current index
	 * @return next index in a circualr array
	 */
	private int nextIndex(int i){
		
		int next = i + 1;
		
		if (next == data.length)
			next = 0;
		
		return next;
		
	}
	public E remove() {
		E answer;
		if (this.manyItems == 0)
			throw new NoSuchElementException("Queue is empty.");
		
		answer = (E) this.data[front];
		this.front = nextIndex(front);
		this.manyItems--;
		return answer;
	}
	
	
	
	public int size() {
		return this.manyItems;
	}
	
	
	
	public boolean isEmpty() {	
		return (this.manyItems == 0);
	}
	
	public String toString() {
    	String output = "[ ";
    	
    	if (front <= rear){
    		for (int i= front; i <= rear; i++)
    			output += this.data[i]+"\t";
    	}else{
    		for (int i=front; i < this.data.length; i++)
    			output += this.data[i]+"\t";
    		for (int i = 0; i <= rear; i++)
    			output += this.data[i]+"\t";
    	}
    	
    	output  += " ] \n";
    	return output;
    }
	
	
	public void ensureCapacity(int minimumCapacity){
		
		Object[] biggerArray;
		
		if (data.length >= minimumCapacity)
			return; //No change Needed
		else if (manyItems == 0)
			//just increase the size of the array because the queue is empty
			data = new Object[minimumCapacity];
		else if (front <= rear){
			//create a larger array and copy data[front]..data[rear]
			biggerArray = new Object[minimumCapacity];
			for (int i=front; i <= rear; i++)
				biggerArray[i] = data[i];
		} else{
			//create a bigger array 
			//the data in the queue occur in two seqments
			//data[front] .........data[length-1]
			//data[0]...data[rear]
			biggerArray = new Object[minimumCapacity];
			
			int pos = 0;
			for (int i= front; i < data.length; i++){
				biggerArray[pos] = data[i];
				pos++;
			}
			for (int i=0 ; i <= rear; i++){
				biggerArray[pos] = data[i];
				pos++;
			}
			
			this.front = 0;
			this.rear = pos;
			this.data = biggerArray;
		}
	}

}
