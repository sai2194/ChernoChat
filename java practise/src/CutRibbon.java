import java.util.Scanner;
import java.lang.Math;


public class CutRibbon {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int a[] = new int[3];
		 a[0] = scan.nextInt();
		 a[1] = scan.nextInt();
		 a[2] = scan.nextInt();
		 int dp[] = new int[n+1];
		  dp[0] = 1;
		  for(int i=1;i<=n;i++)
		  {
		    for(int j=0;j<3;j++)
		    {
		      if(i-a[j]<0)
		      continue;
		      if(i==a[j])
		      dp[i] = Math.max(dp[i],1);
		      else
		      if(dp[i-a[j]]!=0)
		      dp[i] = Math.max(dp[i],dp[i-a[j]]+1);
		    }
		  }
		  System.out.println(dp[n]);
		
	}

}
