/*
ID: mywbob1
LANG: JAVA
TASK: clocks
*/


//4^9 states
//bfs is better
//bit operation is not necessary, the key is, does sort before checking valid!!! order does not matter!!(but it matters for keeping min list)

import java.io.*;
import java.util.*;

public class clocks {
	private ArrayList<Integer> res;
	private int[][] input;
	private int min = Integer.MAX_VALUE;
	private ArrayList<Integer> ares = new ArrayList<Integer>();
	
	public void read(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st;
		input = new int[3][3];
		for (int i = 0; i< 3; i++) {
			st  = new StringTokenizer(br.readLine());
			input[i][0] = Integer.parseInt(st.nextToken());
			input[i][1] = Integer.parseInt(st.nextToken());
			input[i][2] = Integer.parseInt(st.nextToken());
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
		
	public void dfs(int index, ArrayList<Integer> ares) {
		if (index == 9) {
			int s = getSteps(ares);
			if (min >= s) {
					ArrayList<Integer> move = changeToSequenceOfMoves(ares);
					if (isSolution(move)) {//update min and res
						min = s;
						Collections.sort(move);
						if (res == null) res = new ArrayList<Integer>(move);
						else res = toInt(res) >= toInt(move) ? move : res;
					}
			}
			return;
		}
		for (int i=0; i<4; i++) {
			ares.add(i);
			dfs(index+1, ares);
			ares.remove(ares.size() - 1);
		}
	}
	
	public int getSteps(ArrayList<Integer> in) {
		int steps = 0;
		for (Integer a : in)
			steps += a;
		return steps;
	}
	
	public ArrayList<Integer> changeToSequenceOfMoves(ArrayList<Integer> a) {//change to moves, do not sort here, order does not matter!
		ArrayList<Integer> b = new ArrayList<Integer>();
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) != 0) {
				for (int j =0; j < a.get(i); j++) 
					b.add(i+1);//so change to 1-9 not 0-8
			}
		}
		//Collections.sort(b);!!!!
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
			a[0][0] = (a[0][0] + 3) % 12;
			a[0][1] = (a[0][1] + 3) % 12;
			a[1][0] = (a[1][0] + 3) % 12;
			a[1][1] = (a[1][1] + 3) % 12;
		}
		else if (pat == 2) {
			a[0][0] = (a[0][0] + 3) % 12;
			a[0][1] = (a[0][1] + 3) % 12;
			a[0][2] = (a[0][2] + 3) % 12;			
		}
		else if (pat == 3) {
			a[0][1] = (a[0][1] + 3) % 12;
			a[0][2] = (a[0][2] + 3) % 12;
			a[1][1] = (a[1][1] + 3) % 12;
			a[1][2] = (a[1][2] + 3) % 12;
		}
		else if (pat == 4) {
			a[0][0] = (a[0][0] + 3) % 12;
			a[1][0] = (a[1][0] + 3) % 12;
			a[2][0] = (a[2][0] + 3) % 12;
		}
		else if (pat == 5) {
			a[0][1] = (a[0][1] + 3) % 12;
			a[1][0] = (a[1][0] + 3) % 12;
			a[1][1] = (a[1][1] + 3) % 12;
			a[1][2] = (a[1][2] + 3) % 12;
			a[2][1] = (a[2][1] + 3) % 12;
		}
		else if (pat == 6) {
			a[0][2] = (a[0][2] + 3) % 12;
			a[1][2] = (a[1][2] + 3) % 12;
			a[2][2] = (a[2][2] + 3) % 12;
		}
		else if (pat == 7) {
			a[1][0] = (a[1][0] + 3) % 12;
			a[1][1] = (a[1][1] + 3) % 12;
			a[2][0] = (a[2][0] + 3) % 12;
			a[2][1] = (a[2][1] + 3) % 12;
		}
		else if (pat == 8) {
			a[2][0] = (a[2][0] + 3) % 12;
			a[2][1] = (a[2][1] + 3) % 12;
			a[2][2] = (a[2][2] + 3) % 12;
		}
		else if (pat == 9) {
			a[1][1] = (a[1][1] + 3) % 12;
			a[1][2] = (a[1][2] + 3) % 12;
			a[2][1] = (a[2][1] + 3) % 12;
			a[2][2] = (a[2][2] + 3) % 12;
		}
		else {
			//throw an exception or whatever
		}
	}
	
	public boolean checkDone(int[][] a) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (!(a[i][j] == 0 || a[i][j] == 12)) return false;
		return true;
	}

	
	public static void main(String[] args) throws IOException {
		clocks m = new clocks();
		m.read("clocks.in");
		m.dfs(0, m.ares);
		m.write("clocks.out");
	}
}