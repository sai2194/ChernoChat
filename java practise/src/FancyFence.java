import java.util.Scanner;


public class FancyFence {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
	     int t = scan.nextInt();
	     while(t!=0)
	     {
	    	 int n = scan.nextInt();
	    	 double res1 = (double)360/(180-n);
	    	 int res2 = 360/(180-n);
	    	 if(res1==res2)
	    		 System.out.println("YES\n");
	    	 else
	    		 System.out.println("NO\n");
	    	 t--;
	     }

	}

}
