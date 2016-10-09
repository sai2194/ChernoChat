import java.util.Scanner;
import java.util.Arrays;

public class Businesstrip {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
	     int k = scan.nextInt();
	     int a[] = new int[12];
	     for(int i=0;i<12;i++)
	    	 a[i] = scan.nextInt();
	     Arrays.sort(a);
	     int count=0;
	     for(int i=a.length-1;i>=0;i--)
	     {
	    	 if(k<=0)
	    		 break;
	    	 k-=a[i];
	    	 count++;
	    	
	     }
	     if(k>0)
	    	 System.out.println("-1");
	     else
	     System.out.println(count);
	     
	     
	     

	}

}
