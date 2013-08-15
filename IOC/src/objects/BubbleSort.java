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
		   
		
		for (int i=arr.length-1; i>0; i--)  // ���׸� ���� ���� ����
	     {
	         for(int j=0 ; j<i; j++)         // ������ �׸� ��
	         {
	             if (arr[j] > arr[j+1])      // ������ �׸� ��
	             {
	                 swap(arr, j, j+1);      // ū ���� ���� ���� ġȯ
	             }
	         }

	         
	     }
		System.out.println("After Bubble Sorting");
		displayArray(arr);
        System.out.println();
	}
	
	 static void swap(int[] arr, int i, int j)  // ġȯ
	 {
	     int temp = arr[i];
	     arr[i] = arr[j];
	     arr[j] = temp;
	 }

	 static void displayArray(int[] arr)   // �迭�� �׸� ���
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
