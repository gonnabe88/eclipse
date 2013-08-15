package objects;

public class QSort implements Sort{

	public void sort() {
		
		System.out.println( "****qsort****" );
		int arr[] = new int[10];
		   for(int i = 10 ; i>0 ; i--)
			   arr[10-i] = i;
		 System.out.println("Before Quick Sorting");
	     displayArray(arr);
	     System.out.println();
		   
		quickSort(arr,0,arr.length-1);
		
	
		System.out.println("Before Quick Sorting");
	     displayArray(arr);
	     System.out.println();
		
	}
	public static void quickSort(int[] a, int leftmost, int rightmost)
	 {
	     if(rightmost - leftmost <= 0)   // 배열의 크기가 1 이하이면 리턴
	         return;
	     else                            // 배열의 크기가 2 이상일때 퀵정렬 수행 
	     {
	         int pivot = a[rightmost];   // 피봇값을 오른쪽 마지막 값으로 지정
	         int i = leftmost - 1;    
	         int j = rightmost;

	         while(true)
	         {
	             while (a[++i] < pivot)  // 왼쪽에서 부터 피봇값보다 큰 값 탐색
	                 ;
	             while (j > leftmost && a[--j] > pivot)  // 오른쪽에서 부터 피봇값보다 작은 값 탐색
	                 ;

	             if (i >= j)             // 왼쪽과 오른쪽 위치가 크로스 되면 분할
	                 break;                  
	             else
	                 swap(a, i, j);      // 크로스 되지 않으면 항목끼리 치환
	         }

	         swap(a, i, rightmost);     //  왼쪽에서 탐색한 큰 값과 피봇과 치환

	         quickSort(a, leftmost, i - 1);  // 재귀적으로 퀵정렬 알고리즘 재호출

	         quickSort(a, leftmost + 1, rightmost);  // 재귀적으로 퀵정렬 알고리즘 재호출
	     }
	 }

	static void swap(int[] a, int i, int j)  // 치환
	 {
	     int temp;
	     temp = a[i];
	     a[i] = a[j];
	     a[j] = temp;
	 }

	 static void displayArray(int[] arr)   // 배열의 항목 출력
	 {
	     for (int i=0; i<arr.length; i++)
	     {
	         System.out.print(arr[i] + " ");
	     }
	 }
	 
	 public String toString() {
			return "objects.QSort";
		}
	
}
