import java.util.Scanner;

public class PoliceRecruits {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
	     int n = scan.nextInt();
	     int count = 0;
	     int res = 0;
	     for(int i=1;i<=n;i++)
	     {
	    	 int temp = scan.nextInt();
	    	 if(temp>=0)
	    		 res+=temp;
	    	 else
	    	 {
	    		 res--;
	    		 if(res<0)
	    		 {
	    			 count++;
	    			 res=0;
	    		 }
	    	 }
	    		 
	    	 
	     }
	     System.out.println(count);

	}

}
