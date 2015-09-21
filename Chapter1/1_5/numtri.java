/*
ID: mywbob1
LANG: JAVA
TASK: numtri
*/

/*
DP, allocate a dp  array int[r][r](waste some memory is ok, i allcoate a matrix not a triangle).
at last row, dp[i][j] = input[i][j]
above last row, dp[i][j] = input[i][j] + max(dp[i+1][j], dp[i+1][j+1])
*/


import java.io.*;
import java.util.*;

public class numtri {
	private int[][] input;
	private int r;
	private int res;
	
	public void read(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st;
		String line;
		r = Integer.parseInt(br.readLine());
		input = new int[r][r];
		int index =0;
		while ((line = br.readLine()) != null) {
			st = new StringTokenizer(line);
			for (int i = 0; i<=index; i++) {
					input[index][i] = Integer.parseInt(st.nextToken());

			}
			index++;
		}
	}
	
	public void write(String filename) throws IOException{
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		out.println(findMax());
		out.close();
	}
	
	public int findMax() {
		int[][] dp = new int[r][r];
		for (int row = r-1; row >= 0; row--) {
			for (int col = 0; col <= row; col++) {
				if (row == r - 1) 
					dp[row][col] = input[row][col];
				else {
					dp[row][col] = input[row][col] + Math.max(dp[row+1][col], dp[row+1][col+1]);
				}
			}
		}
		
		return dp[0][0];
		
	}
	
	public static void main (String[] args) throws IOException{
		numtri n = new numtri();
		n.read("numtri.in");
		n.write("numtri.out");
	}
}