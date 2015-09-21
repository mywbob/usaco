/*
ID: mywbob1
LANG: JAVA
TASK: clocks
*/

//generate all states
//trans states to moving steps
//sort steps, after checking solution!!!
//keep min
//4^9 states
//bit operation, 0,3,6,9,12 => 0,1,2,3,4, can use mask 3(11) to keep the state
//how to do bfs?

import java.io.*;
import java.util.*;

public class clocks {
	private ArrayList<Integer> res;
	private int[][] input;
	private int min = Integer.MAX_VALUE;
	private int[] ares = new int[9];
	
	public void read(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st;
		input = new int[3][3];
		for (int i = 0; i< 3; i++) {
			st  = new StringTokenizer(br.readLine());
			input[i][0] = Integer.parseInt(st.nextToken()) /3 % 4;
			input[i][1] = Integer.parseInt(st.nextToken()) /3 % 4;
			input[i][2] = Integer.parseInt(st.nextToken()) /3 % 4;
		}
	}
	
	public void write(String filename) throws IOException{
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		for (int i=0; i<res.size(); i++) {
			out.print(res.get(i));
			if (i!=res.size()-1) out.print(" ");
		}
		out.println();
		out.close();
	}
	
	
	public void enumAll() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				for (int k = 0; k < 4; k++)
					for (int l = 0; l < 4; l++)
						for (int m = 0; m < 4; m++)
							for (int n = 0; n < 4; n++)
								for (int o = 0; o < 4; o++)
									for (int p = 0; p < 4; p++)
										for (int q = 0; q < 4; q++) {
											ares[0] = i;
											ares[1] = j;
											ares[2] = k;
											ares[3] = l;
											ares[4] = m;
											ares[5] = n;
											ares[6] = o;
											ares[7] = p;
											ares[8] = q;
											int tmp = i +j + k + l + m + n + o + p + q;
											
											if (min >= tmp) {
													ArrayList<Integer> move = changeToSequenceOfMoves(ares);
													if (isSolution(move)) {//update min and res
														Collections.sort(move);
														min = tmp;
														if (res == null) res = new ArrayList<Integer>(move);
														else res = toInt(res) >= toInt(move) ? move : res;
													}
											}
											
										}
										
			
	}
	
	public ArrayList<Integer> changeToSequenceOfMoves(int[] a) {//change to moves, do not sort here...!!!
		ArrayList<Integer> b = new ArrayList<Integer>();
		for (int i = 0; i < a.length; i++) {
			if (a[i] != 0) {
				for (int j =0; j < a[i]; j++) 
					b.add(i+1);//so change to 1-9 not 0-8
			}
		}
		//Collections.sort(b);
		return b;
	}
	
	public int toInt(ArrayList<Integer> a) {
		int r = 0;
		for (Integer i : a)
			r = 10 * r + i;
		return r;
	}
	
	
	public boolean isSolution(ArrayList<Integer> t) {
		int[][] temp = new int[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				temp[i][j] = input[i][j];
			
		for (Integer m : t)
			nineMovePattern(temp, m);

		return checkDone(temp);
	}
	
	public void nineMovePattern(int[][] a, int pat) {
		if (pat == 1) {
			a[0][0] = (a[0][0] + 1) & 3;
			a[0][1] = (a[0][1] + 1) & 3;
			a[1][0] = (a[1][0] + 1) & 3;
			a[1][1] = (a[1][1] + 1) & 3;
		}
		else if (pat == 2) {
			a[0][0] = (a[0][0] + 1) & 3;
			a[0][1] = (a[0][1] + 1) & 3;
			a[0][2] = (a[0][2] + 1) & 3;			
		}
		else if (pat == 3) {
			a[0][1] = (a[0][1] + 1) & 3;
			a[0][2] = (a[0][2] + 1) & 3;
			a[1][1] = (a[1][1] + 1) & 3;
			a[1][2] = (a[1][2] + 1) & 3;
		}
		else if (pat == 4) {
			a[0][0] = (a[0][0] + 1) & 3;
			a[1][0] = (a[1][0] + 1) & 3;
			a[2][0] = (a[2][0] + 1) & 3;
		}
		else if (pat == 5) {
			a[0][1] = (a[0][1] + 1) & 3;
			a[1][0] = (a[1][0] + 1) & 3;
			a[1][1] = (a[1][1] + 1) & 3;
			a[1][2] = (a[1][2] + 1) & 3;
			a[2][1] = (a[2][1] + 1) & 3;
		}
		else if (pat == 6) {
			a[0][2] = (a[0][2] + 1) & 3;
			a[1][2] = (a[1][2] + 1) & 3;
			a[2][2] = (a[2][2] + 1) & 3;
		}
		else if (pat == 7) {
			a[1][0] = (a[1][0] + 1) & 3;
			a[1][1] = (a[1][1] + 1) & 3;
			a[2][0] = (a[2][0] + 1) & 3;
			a[2][1] = (a[2][1] + 1) & 3;
		}
		else if (pat == 8) {
			a[2][0] = (a[2][0] + 1) & 3;
			a[2][1] = (a[2][1] + 1) & 3;
			a[2][2] = (a[2][2] + 1) & 3;
		}
		else if (pat == 9) {
			a[1][1] = (a[1][1] + 1) & 3;
			a[1][2] = (a[1][2] + 1) & 3;
			a[2][1] = (a[2][1] + 1) & 3;
			a[2][2] = (a[2][2] + 1) & 3;
		}
		else {
			//throw an exception or whatever
		}
	}
	
	public boolean checkDone(int[][] a) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (!(a[i][j] == 0)) return false;
		return true;
	}

	
	public static void main(String[] args) throws IOException {
		clocks m = new clocks();
		m.read("clocks.in");
		m.enumAll();
		m.write("clocks.out");
	}
}