import java.util.Scanner;
import java.lang.Math;
public class LinelandMail {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		int a[] = new int[n+1];
		for(int i=0;i<n;i++)
		{
			a[i] = s.nextInt();
		}
		for(int i=0;i<n;i++)
		{
			if(i==0)
			System.out.println((a[i+1]-a[i])+" "+(a[n-1]-a[i]));
			else
			if(i==n-1)
	        System.out.println((a[i]-a[i-1])+" "+(a[n-1]-a[0]));
			else
		    System.out.println(Math.min(a[i+1]-a[i],a[i]-a[i-1])+" "+Math.max(a[n-1]-a[i],a[i]-a[0]));
			
		}
		

	}

}
