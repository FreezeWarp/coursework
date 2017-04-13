
public class SearchAndSort {
	
	public static void main(String[] args){
		
		int[] data = {8,4,0,1,5,4,9,6,7};
		
		int pos1 = iterativeBinarySearch(data,5);
		System.out.println("5 found at position "+pos1);
		
		//selectionSort(data);
		insertionSort(data);
		
		for (int i=0 ; i < data.length; i++)
			System.out.println(data[i]);
		
		int pos2 = linearSearch(data,9);
		System.out.println("9 found at position "+pos2);
		
		
	}
	
	public static void selectionSort(int[] data){
		int small;
		int n = data.length;
		for ( int i = 0; i < n; i++){
			//calculate small as the index of the smallest element in data[i]..data[n-1] 
			small = i;
			for (int j = i;  j < n; j++){
				if (data[small] > data[j])
				small = j;
			}
			//swap data[i] and data[small]
			int temp = data[i];
			data[i] = data[small];
			data[small] = temp;	
		}
	}
	
	public static void insertionSort(int[] data){
		
		for ( int i = 1; i < data.length; i++){
			int element = data[i];
			int j = i;
			//shift elements to the right as long as they are greater than element
			for (j = i; j > 0 && data[j-1] > element ; j--){ 
				//System.out.println(i+"\t"+j);
				data[j] = data[j-1];
			}
			//System.out.println(i+"\t"+j);
			data[j] = element;
		}
	}
	
	//return the index of the first occurrence
	//of target in list or -1 if target not present in  list

	public static int linearSearch(int[] data, int target) {
	    for(int i = 0; i < data.length; i++)
	        if( data[i] == target )
	            return i;
	    return -1;
	}
	
	public static int iterativeBinarySearch(int[] data, int target)
	{	
		int result = -1;
		int low = 0;
		int high = data.length - 1;
		int mid;
		
		while( result == -1 && low <= high ){	
			mid = low + ((high - low) / 2);
			if( target == data[mid] )
				result = mid;
			else if(target > data[mid])
				low = mid + 1;
			else
				high = mid - 1;
		}
		
		return result;
	}
		





}

