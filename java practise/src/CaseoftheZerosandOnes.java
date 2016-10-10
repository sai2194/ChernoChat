import java.util.Scanner;


public class CaseoftheZerosandOnes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		String s = scan.next();
		int count=0;
		for(int i=0;i<s.length();i++)
		{
			if(s.charAt(i) == '0')
				count--;
			else
				count++;
			
		}
		System.out.println(Math.abs(count));

	}

}
