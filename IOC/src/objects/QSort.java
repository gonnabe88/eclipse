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
	     if(rightmost - leftmost <= 0)   // �迭�� ũ�Ⱑ 1 �����̸� ����
	         return;
	     else                            // �迭�� ũ�Ⱑ 2 �̻��϶� ������ ���� 
	     {
	         int pivot = a[rightmost];   // �Ǻ����� ������ ������ ������ ����
	         int i = leftmost - 1;    
	         int j = rightmost;

	         while(true)
	         {
	             while (a[++i] < pivot)  // ���ʿ��� ���� �Ǻ������� ū �� Ž��
	                 ;
	             while (j > leftmost && a[--j] > pivot)  // �����ʿ��� ���� �Ǻ������� ���� �� Ž��
	                 ;

	             if (i >= j)             // ���ʰ� ������ ��ġ�� ũ�ν� �Ǹ� ����
	                 break;                  
	             else
	                 swap(a, i, j);      // ũ�ν� ���� ������ �׸񳢸� ġȯ
	         }

	         swap(a, i, rightmost);     //  ���ʿ��� Ž���� ū ���� �Ǻ��� ġȯ

	         quickSort(a, leftmost, i - 1);  // ��������� ������ �˰��� ��ȣ��

	         quickSort(a, leftmost + 1, rightmost);  // ��������� ������ �˰��� ��ȣ��
	     }
	 }

	static void swap(int[] a, int i, int j)  // ġȯ
	 {
	     int temp;
	     temp = a[i];
	     a[i] = a[j];
	     a[j] = temp;
	 }

	 static void displayArray(int[] arr)   // �迭�� �׸� ���
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
