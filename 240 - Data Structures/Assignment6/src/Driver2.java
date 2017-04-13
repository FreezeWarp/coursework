
public class Driver2 {
	
	public static void main(String[] args){
		
		QueueInterface<Integer> q1 = new ArrayQueue<Integer>(5);
		
		q1.add(1);
		q1.add(2);
		q1.add(3);
		q1.remove();
		q1.add(4);
		q1.add(5);
		q1.add(6);
		q1.remove();
		q1.add(7);
		
		System.out.println(q1);
		
		
		QueueInterface<Integer> q2 = new LinkedQueue<Integer>();
		
		q1.add(1);
		q1.add(2);
		q1.add(3);
		q1.remove();
		q1.add(4);
		q1.add(5);
		q1.add(6);
		q1.remove();
		q1.add(7);
		
		System.out.println(q1);
		
		
	}

}
