/*
ID: mywbob1
LANG: JAVA
TASK: subset
*/



/*
pure dp? 0/1 knapsack problem. do not know very well.
maybe learn 0/1 and complete knapsack.
http://www.geeksforgeeks.org/dynamic-programming-set-10-0-1-knapsack-problem/
http://love-oriented.com/pack/
need to know how to write recursion version and dp version

f[i, j] means, the ways that the sum of subset1 = j with first i number from the set
f[n, (1+n)*n/2] returns the result i want 

f[i,j]=f[i-1,j]+f[i-1,j-i]    j-i>=0 
f[i,j]=f[i-1,j]               j-i<0
f[i,0] = 0	//?? true?				  
f[0,j] = 0 //?? true?


f[1,1]:=1;//why?
f[1,0]:=1;//why?
for i:=2 to n do
 for j:=0 to sum do
  if j-i>=0 then f[i,j]:=f[i-1,j]+f[i-1,j-i]
  else f[i,j]:=f[i-1,j];

res is f[n, sum] div 2 !!!!!!!!!!!!!!
*/




import java.io.*;
import java.util.*;

public class subset {	
	private int input =0 ;
	private long output = 0;
	private long cache[][] = new long[50][1800]; //(1 + 39) * 39 / 2 / 2
	
	public void read(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		input = Integer.parseInt(br.readLine());
		
	}
	
	public void write(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		out.println(output/2);
		out.close();
	}
	
	public void doIt() {
		int sum = (1+input) * input / 2;
		if (sum % 2 != 0) output = 0;
		else 
			dp(input, sum/2);
	}
	
	public void dp(int i, int j) {
		cache[1][1] = 1;//why?
		cache[1][0] = 1;//why?
		for (int m=2; m<= i; m++) {//why?
			for (int n=0; n<= j; n++) {
				if (n-m<0) {
					cache[m][n] = cache[m-1][n];
				}
				else {
					cache[m][n] = cache[m-1][n] + cache[m-1][n-m];
				}
			}
		}
		output = cache[i][j];
	}
	
	
	public static void main(String[] args) throws IOException {
		subset s = new subset();
		s.read("subset.in");
		s.doIt();
		s.write("subset.out");		
	}
	
}










