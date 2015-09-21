/*
ID: mywbob1
LANG: JAVA
TASK: checker
*/


import java.io.*;
import java.util.*;

public class checker {
	private int cnt;
	private int d;
	private ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
	private int[][] board;//maybe char, or even bit
	private boolean firstThree = false;
	
	public void read(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		d = Integer.parseInt(br.readLine());
		board = new int[d][d];
	}
	
	public void write(String filename) throws IOException{
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		
		for (ArrayList<Integer> i : res) {
			for (int j = 0; j<i.size();j++) {
				out.print(i.get(j));
				if (j!= i.size() -1) out.print(" ");
				else out.println();
			}

		}

		out.println(cnt);
		out.close();
	}
	
	public ArrayList<Integer> getOutput(int[][] board) {
		ArrayList<Integer> t = new ArrayList<Integer>();
		for (int r=0; r< d; r++) {
			for (int c=0; c<d; c++) {
				if (board[r][c] == 1) {
					t.add(c+1);
					break;
				}
			}
		}
		return t;
	}
	
	public void dfs(int r, int d, int[][] board) {//all
		if (r==d) {
			cnt++;
			if (cnt <= 3) res.add(getOutput(board));//only collect the first 3 board as output
			return;
		}
		
		for (int i=0; i< d; i++) {
			board[r][i] = 1;//put
			if (isValid(r, i, board)) {
				dfs(r+1, d, board);
			}
			board[r][i] = 0 ;//cancel
		}
	}
	
	public void dfsFindFirstThree(int r, int d, int[][] board) {
		if (firstThree) return;
		if (r==d) {
			cnt++;
			if (cnt <= 3) {//only collect the first 3 board as output
				res.add(getOutput(board)); 
				if (cnt==3)firstThree = true;
			}
			return;
		}
		
		for (int i=0; i< d; i++) {
			if (firstThree) return;
			board[r][i] = 1;//put
			if (isValid(r, i, board)) {
				dfsFindFirstThree(r+1, d, board);
			}
			board[r][i] = 0 ;//cancel
		}
	}
	
	public void dfsHalf(int r, int d, int[][] board) {//even number only need this
		if (r==d) {
			cnt++;
			return;
		}
		
		for (int i=0; i< d; i++) {
			if (d % 2 == 0 && r==0 && i>=d/2) break;
			if (d % 2 != 0 && r==0 && i>d/2-1) break;
			board[r][i] = 1;//put
			if (isValid(r, i, board)) {
				dfsHalf(r+1, d, board);
			}
			board[r][i] = 0 ;//cancel
		}
	}
	
	public void dfsMid(int r, int d, int[][] board) {//odd number need this also
		if (r==d) {
			cnt++;
			return;
		}
		
		for (int i=0; i< d; i++) {
			if (r==0 && i != d/2) continue;
			board[r][i] = 1;//put
			if (isValid(r, i, board)) {
				dfsMid(r+1, d, board);
			}
			board[r][i] = 0 ;//cancel
		}
	}
	/*
	0 1 2 3 4 5 6 7 8
	0 1 2 3 4 5 6 7
	0 1 2 3 4 5 6
	*/
	
	
	
	
	public boolean isValid(int row, int col, int board[][]) {
		return checkCol(row, col, board) && checkDiag1(row, col, board) && checkDiag2(row, col, board);
		
	}
	
	
	public boolean checkCol (int row, int col, int[][] board) {//not need to check row
		for (int i=0; i< d; i++) {
			if (board[i][col] == 1 && i!=row) return false;
		}
		return true;
	}
	
	public boolean checkDiag1 (int row, int col, int[][] board) {// dia "\" only check above
		int rr = row;
		int cc = col;
		while (rr >0 && cc >0) {
			if (board[rr-1][cc-1]==1) return false;
			rr--;
			cc--;
		}
		
		return true;
		
	}
	
	public boolean checkDiag2 (int row, int col, int[][] board) {// dia "/" only check above
		int rr = row;
		int cc = col;
		while (rr >0 && cc <d-1) {
			if (board[rr-1][cc+1]==1) return false;
			rr--;
			cc++;
		}
		
		return true;
	}
	
	
	
	public void doIt () {
		//dfs(0, d, board);
		
		if (d % 2 == 0) {
			dfsFindFirstThree(0, d, board);
			cnt=0;
			dfsHalf(0, d, board);
			cnt = cnt*2;
		}
		else {
			
			long ts = System.nanoTime();
			long t1 = System.nanoTime();
			dfsFindFirstThree(0, d, board);
			long t2 = System.nanoTime();
			cnt=0;
			long t3 = System.nanoTime();
			dfsHalf(0, d, board);
			long t4 = System.nanoTime();
			cnt = cnt*2;
			long t5 = System.nanoTime();
			dfsMid(0, d, board);
			long t6 = System.nanoTime();
			long te = System.nanoTime();
			System.out.println(t2-t1);
			System.out.println(t4-t3);
			System.out.println(t6-t5);
			System.out.println(te-ts);
			
			
			/*
			long ts = System.nanoTime();
			dfs(0, d, board);
			long te = System.nanoTime();
			System.out.println(te-ts);
			*/
			
		}
	}
	
	
	
	public static void main(String[] args) throws IOException {
		checker c = new checker();
		c.read("checker.in");
		c.doIt();
		c.write("checker.out");
	}
}