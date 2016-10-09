import java.util.*;
import java.lang.Math;

public class sumoftwo {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int a = scan.nextInt();
        int b = scan.nextInt();
        int s = scan.nextInt();
        a = Math.abs(a);
        b = Math.abs(b);
        if(a+b > s)
        	System.out.println("NO");
        else
        {
        	int res = s-(a+b);
        	if(res%2==0)
        		System.out.println("YES");
        	else
        		System.out.println("NO");
        		
        }
       
 
  }
}
