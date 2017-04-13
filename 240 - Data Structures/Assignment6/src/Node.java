
public class Node<E extends Comparable> {
	
	private E data;
	private Node<E> link;
	
	
	public Node(E data, Node<E> link){
		
		this.data = data;
		this.link = link;
	}
	public E getData(){ return this.data; }
	public Node<E> getLink(){ return this.link;}
	public void setData(E element){ this.data = element; }
	public void setLink(Node<E> link){ this.link = link; }
	
	public void addNodeAfter(E element){
		this.link = new Node (element, this.link);
	}
	
	public void removeNodeAfter(){
		
		this.link = this.link.link;
	}
	
	public static <E extends Comparable> int listLength(Node<E> head){
		
		int output = 0;
		Node<E> cursor = head;
		while (cursor !=null){
			output++;
			cursor = cursor.getLink();
		}
		return output;
	}
	
	
	public static <E extends Comparable> void display(Node<E> head){
		
		Node<E> cursor = head;
		
		while (cursor !=null){
			System.out.println(cursor.getData());
			cursor = cursor.getLink();
		}
		
	}
	

}
