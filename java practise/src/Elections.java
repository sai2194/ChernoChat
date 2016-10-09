import java.util.Scanner;

public class Elections {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Scanner scan = new Scanner(System.in);
	     int n = scan.nextInt();
	     int m = scan.nextInt();
	     int a[] = new int[n+1];
	     int max = -1;
    	 int index=0;
	     for(int i=1;i<=m;i++)
	     {
	    	 max = -1;
	    	 for(int j=1;j<=n;j++)
	    	 {
	    		 int temp = scan.nextInt();
	    		 if(temp>max)
	    		 {
	    			 index = j;
	    			 max = temp;
	    		 }
	    	 }
	    	 a[index] = a[index]+1;
	     }
	     max = -1;
    	 for(int j=1;j<=n;j++)
    	 {
    		 
    		 if(a[j]>max)
    		 {
    			 index = j;
    			 max = a[j];
    		 }
    	 }
    	 System.out.println(index);
	     
	     

	}

}
