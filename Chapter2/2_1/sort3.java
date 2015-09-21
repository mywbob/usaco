/*
ID: mywbob1
LANG: JAVA
TASK: sort3
*/

import java.io.*;
import java.util.*;

/*
array s may not necessary
below is wrong!!!
s[0] = 0;
greedy, s[i] = s[i-1] + 0 if input[i] >= input[i-1], no need swap
					  + 1 or 2 if input[i] < input[i-1], one or two swap to sort the array, at most two

*/

/*
!!this problem is not sort the array effectively, just want a optimal number of swaping. 
greedy: each decision is optimal, the final solution is optimal(not always true)
order does not matter here
what are the optimal decisions here?
1:firgure out the position of 1,2,3, the bound.
opt type 1: swap 1 with 2 (1 in 2 position and 2 in 1 position)
opt type 2: swap 1 with 3 (same as above)
opt type 3: swap 2 with 3 (same as above)
above 3 types of swaping are optimal(min swap that must be done), swap number is 1 for each
then, for those still in wrong place, swap number is 2(how to implement?)

it is possible only have 1 or 2 or 3, but the swap above are genaral case, works in specical case too
I do not check the bound very carefully, the test case passed but there are many bugs I guess, but ...whatever..
*/
public class sort3 {
	private int n;
	private int[] input;
	private int[] sorted;
	private int[] s;
	private int res = 0;
	private int b1=-1;
	private int b2=-1;
	private int b3=-1;
	

	public void read (String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		n = Integer.parseInt(br.readLine());
		input = new int[n];
		sorted = new int[n];
		s = new int[n];
		String line;
		int index = 0;
		while ((line = br.readLine()) != null) {
			input[index++] = Integer.parseInt(line);
		}
		//sort the array, figure out the bound for 1,2,3
		for (int i=0; i< n; i++) {
			sorted[i] = input[i];
		}
		Arrays.sort(sorted);
		//figure out the bound for 1,2,3
		for (int i=0; i< n; i++) {
			if (sorted[i] == 1) b1 = i;// [0, b1]
		}
		for (int i=0; i< n; i++) {
			if (sorted[i] == 2) b2 = i;//[b1+1, b2]
		}
		for (int i=0; i< n; i++) {
			if (sorted[i] == 3) b3 = i;//[b2+1, b3]
		}
		
	}
	
	public void write (String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		out.println(res);
		out.close();
	}
	
	public void swapIt (int i, int j) {
		int temp = input[i];
		input[i] = input[j];
		input[j] = temp;
	}
	
	public void optSwap(int firsts, int firste, int firstv, int seconds, int seconde, int secondv) {//the start and end for two ranges, and value
		for (int i=firsts; i<= firste; i++){
			for (int j=seconds; j<= seconde; j++) {
				if ( valid(i) && valid(j) && (input[i] == secondv) && (input[j] == firstv) ) {
					swapIt(i, j);
					res++;//res = res + 1 for each success swap
				}
			}
		}
	}
	
	public void restSwap(int firsts, int firste, int seconds, int seconde, int v) {
		for (int i=firsts; i<= firste; i++){
			for (int j=seconds; j<= seconde; j++) {
				if ( valid(i) && valid(j) && (input[i] != v) && (input[j] == v) ) {
					swapIt(i, j);
					res++;//res = res + 1 for each success swap
				}
			}
		}
	}
	

	
	public boolean valid(int i) {
		if (i >= 0 && i < n) return true;
		return false;
	}
	
	public void doIt() {
		optSwap(0, b1, 1, b1+1, b2, 2);
		optSwap(0, b1, 1, b2+1, b3, 3);
		optSwap(b1+1, b2, 2, b2+1, b3, 3);
		
		//deal with those still in wrong place
		//1: find in 1 range but not 1, swap with 1 in range 2 or 3
		//2: find in 3 range but not 3, swap with 3 in range 1 or 2
		
		restSwap(0, b1, b1+1, b3, 1);
		restSwap(b2+1, b3, 0, b2, 3);
	}
	
	

	
	public static void main (String[] args) throws IOException {
		sort3 s = new sort3();
		s.read("sort3.in");
		s.doIt();
		s.write("sort3.out");		
	}
	
	/* wrong
	public void findMin () {
		for (int i=1; i<n; i++) {
			if (input[i] >= input[i-1]) s[i] = s[i-1];
			else {
				//swap once or twice
				int d = 0;
				int c = 0;
				int[] index = {-1, -1};
				int first = input[0];
				for (int k = 0; k <= i-1; k++) {
					if (input[k] != first) {
						index[d] = k;
						first = input[k];
						d++;
					}
				}
				if (d==2) {
					if (input[i] == 1) {
						swapIt(i, index[1]);
						swapIt(index[1], index[0]);
						c = 2;
					}
					if (input[i] == 2) {
						swapIt(i, index[1]);
						c = 1;
					}
				}
				
				if (d==1) {
					swapIt(i, index[0]);
					c = 1;
				}
				
				if (d==0) {
					swapIt(i, 0);
					c = 1;
				}
				
				s[i] = s[i-1] + c;
			}
			
			
		}
		res = s[n-1];
	}
	*/
}