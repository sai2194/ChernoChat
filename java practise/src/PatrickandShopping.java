import java.util.Scanner;
import java.lang.Math;


public class PatrickandShopping {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
	     int d1 = scan.nextInt();
	     int d2 = scan.nextInt();
	     int d3 = scan.nextInt();
	     System.out.println(Math.min(d1,d2)+Math.min(d3,d1+d2) + Math.min(Math.max(d1, d2),d3+Math.min(d1,d2)));

	}

}
