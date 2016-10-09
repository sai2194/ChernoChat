import java.util.Scanner;


public class FedorandNewGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
	     int n = scan.nextInt();
	     int m = scan.nextInt();
	     int k = scan.nextInt();
	     int a[] = new int[m+2];
	     for(int i=0;i<=m;i++)
	     {
	    	 a[i] = scan.nextInt();
	     }
	     int res = a[m];
	     int count=0;
	     for(int i=0;i<m;i++)
	     {
	    	 int c = k;
	    	 int val = res;
	    	 while(val!=0 || a[i]!=0)
	    	 {
	    		 if(val%2 != a[i]%2)
	    		 c--;
	    		 val = val/2;
	    		 a[i]/=2;
	    		 if(c<0)
	    			 break;
	    	 }
	    	 if(c>=0)
	    		 count++;
	     }
	     System.out.println(count);

	}

}
