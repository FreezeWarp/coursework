import java.util.NoSuchElementException;

public class LinkedQueue<E> implements QueueInterface<E>{
	
	private Node<E> front;
	private Node<E> rear;
	private int manyNodes;
	
	public LinkedQueue(){
		this.manyNodes = 0;
		this.front = null;
		this.rear = null;
	}
	
	
	@Override
	public void add(E item) {
		if (isEmpty()){
			//insert first item
			front = new Node<E>(item,null);
			rear = front;
		}else{
			rear.addNodeAfter(item);
			rear = rear.getLink();
		}
		manyNodes++;	
	}

	@Override
	public E remove() {
		E answer;
		
		if (manyNodes == 0)
			throw new NoSuchElementException("Queue Underflow");
		answer = front.getData();
		front = front.getLink();
		manyNodes--;
		if (manyNodes == 0)
			rear = null;
		return answer;
	}

	public int size() {
		return this.manyNodes;
	}
	@Override
	public boolean isEmpty() {
		return (this.manyNodes == 0);
	}
	
	public String toString(){
		
		String output  = "[\t";
		
		Node<E> cursor = front;
		
		while (cursor != null){
			output += cursor.getData()+"\t";
			cursor = cursor.getLink();
		}
		
		output +="]";
		return output;
	}
	
	

}
