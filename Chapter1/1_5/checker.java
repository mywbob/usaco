/*
ID: mywbob1
LANG: JAVA
TASK: checker
*/


import java.io.*;
import java.util.*;


/*
key: only try half of the first row, can reduce the time by half. then cnt = cnt * 2(for even number). for odd number, need to check the middle queen(r==0)
key: d % 2 is slow, do not check this first, try check r ==0 first, save me 0.2 sec
key: try to store a col[d], dia1[2*d -1] and dia2[2*d -1], so can look them to see if valid or not
*/


public class checker {
	private int cnt;
	private int d;
	private ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
	private int[][] board;//maybe char, or even bit
	private boolean firstThree = false;
	private int[] colv;
	private int[] dia1v;
	private int[] dia2v;
	private int[][] hashDia1;
	private int[][] hashDia2;
	
	public void read(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		d = (int)Integer.parseInt(br.readLine());
		board = new int[d][d];
		colv = new int[d];
		dia1v = new int[2*d-1];
		dia2v = new int[2*d-1];
		hashDia1 = new int[d][d];
	 	hashDia2 = new int[d][d];	
		dia1();
		dia2();
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
			if (board[r][i] != 1 && isValid(r, i)) {
				board[r][i] = 1;//put
				colv[i] = 1;
				dia1v[hashDia1[r][i]] = 1;
				dia2v[hashDia2[r][i]] = 1;
				
				dfsFindFirstThree(r+1, d, board);
				
				board[r][i] = 0 ;//cancel
				colv[i] = 0;
				dia1v[hashDia1[r][i]] = 0;
				dia2v[hashDia2[r][i]] = 0;
			}
		}
	}
	
	public void dfsHalf(int r, int d, int[][] board) {//even number only need this
		if (r==d) {
			cnt++;
			return;
		}
				
		for (int i=0; i< d; i++) {
			if (r==0 && d % 2 == 0 && i>=d/2) break;//check r== 0 first, do not check d % 2 first, save me 0.2 sec
			if (r==0 && d % 2 != 0 && i>d/2-1) break;
			if (board[r][i] != 1 && isValid(r, i)) {
				board[r][i] = 1;//put
				colv[i] = 1;
				dia1v[hashDia1[r][i]] = 1;
				dia2v[hashDia2[r][i]] = 1;
				
				dfsHalf(r+1, d, board);
				
				board[r][i] = 0 ;//cancel
				colv[i] = 0;
				dia1v[hashDia1[r][i]] = 0;
				dia2v[hashDia2[r][i]] = 0;
			}
		}
	}
	
	public void dfsMid(int r, int d, int[][] board) {//odd number need this also
		if (r==d) {
			cnt++;
			return;
		}
				
		for (int i=0; i< d; i++) {
			if (r==0 && i != d/2) continue;
			if (board[r][i] != 1 && isValid(r, i)) {
				board[r][i] = 1;//put
				colv[i] = 1;
				dia1v[hashDia1[r][i]] = 1;
				dia2v[hashDia2[r][i]] = 1;
				
				dfsMid(r+1, d, board);
				
				board[r][i] = 0 ;//cancel
				colv[i] = 0;
				dia1v[hashDia1[r][i]] = 0;
				dia2v[hashDia2[r][i]] = 0;
			}
		}
	}
	
	public void dia1() {
		for (int i=0; i<d; i++) {
			for (int j=0; j<d; j++) {
				hashDia1[i][j] = d-i-1+j;
			}
		}
	}
	
	public void dia2() {
		for (int i=0; i<d; i++) {
			for (int j=0; j<d; j++) {
				hashDia2[i][j] = i + j;
			}
		}
	}
	
	
	public boolean isValid(int row, int col) {
		return checkCol(row, col) && checkDiag1(row, col) && checkDiag2(row, col);
		
	}
	
	public boolean checkCol (int row, int col) {//not need to check row
		if (colv[col] == 1) return false;
		return true;
	}
	
	public boolean checkDiag1 (int row, int col) {// dia "\" only check above
		if (dia1v[hashDia1[row][col]] == 1) return false;
		return true; 
	}
	
	public boolean checkDiag2 (int row, int col) {// dia "/" only check above
		if(dia2v[hashDia2[row][col]] == 1) return false;
		return true;
	}
	
	
	public void doIt () {	
		if (d % 2 == 0) {
			dfsFindFirstThree(0, d, board);
			cnt=0;
			dfsHalf(0, d, board);
			cnt = cnt*2;
		}
		else {
			dfsFindFirstThree(0, d, board);
			cnt=0;
			dfsHalf(0, d, board);
			cnt = cnt*2;
			dfsMid(0, d, board);			
		}
	}
	
	public static void main(String[] args) throws IOException {
		checker c = new checker();
		c.read("checker.in");
		c.doIt();
		c.write("checker.out");
	}
}