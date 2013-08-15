package objects;

public class BubbleSort implements Sort{

//	public void sort(){
//		System.out.println("***BubbleSort****");
//	}
	public void sort(){
		
		int arr[] = new int[10];
		   for(int i = 10 ; i>0 ; i--)
			   arr[10-i] = i;
		 System.out.println("Before Bubble Sorting");
	     displayArray(arr);
	     System.out.println();
		   
		
		for (int i=arr.length-1; i>0; i--)  // 비교항목에 대한 범위 정함
	     {
	         for(int j=0 ; j<i; j++)         // 범위내 항목 비교
	         {
	             if (arr[j] > arr[j+1])      // 인접한 항목 비교
	             {
	                 swap(arr, j, j+1);      // 큰 값과 작은 값을 치환
	             }
	         }

	         
	     }
		System.out.println("After Bubble Sorting");
		displayArray(arr);
        System.out.println();
	}
	
	 static void swap(int[] arr, int i, int j)  // 치환
	 {
	     int temp = arr[i];
	     arr[i] = arr[j];
	     arr[j] = temp;
	 }

	 static void displayArray(int[] arr)   // 배열의 항목 출력
	 {
	     for (int i=0; i<arr.length; i++)
	     {
	         System.out.print(arr[i] + " ");
	     }
	 }
	 
	 public String toString() {
			return "objects.BubbleSort";
		}
}
