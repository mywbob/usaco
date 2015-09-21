/*
ID: mywbob1
LANG: JAVA
TASK: hamming
*/

/*
ONE THING: check valid first then put? or put then check valid? or does not matter? is there a genearal way to do it?
byte has sign, can not represent 8 bits positive number, so use short or int
backtrack idea, start from 0, gennerate the numbers and check if the min distance >= D, backtrack and increase 
the D if fail at some point(hmm, check if valid then do is better). the max distance is 8 since max bits is 8
if B is 1, number range [0,1]
if B is 2, number range [0,3]
if B is n, number range [0, 2^B-1]

only need one solution
*/


import java.io.*;
import java.util.*;

public class hamming {
	private boolean flag = false;
	private ArrayList<Integer> res = new ArrayList<Integer>();
	private int N;
	private int B;
	private int D;
	private int maxNumber;
	
	public void read(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		maxNumber = (int)(Math.pow((double)2, (double)B) - 1);
		//System.out.println(maxNumber);
	} 
	
	public void write(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		for (int i =0; i< res.size(); i++) {
			out.print(res.get(i));
			if ((i+1) % 10 == 0 || i == res.size() - 1) out.println();
			else out.print(" ");
		}
		out.close();
	} 
	
	public int countOnes(int in) {
		int cnt = 0;
		for (int i = 0; i< B; i++) {
			int mask = 1 << i;
			if ((mask & in) != 0) cnt++;
		}
		
		//System.out.println("in is " + in + "cnt is " + cnt);
		return cnt;
	}
	
	public boolean isValid() {//check if everything in res with distance >= D
		for (int i=0; i<res.size()-1; i++)
			for (int j=i+1; j<res.size(); j++) {
				if (countOnes(res.get(i) ^ res.get(j)) < D) return false;
			}
		
		return true;
	}
	
	public void dfs(int s) {
		if (res.size() == N) {//res size == N, success, set the flag, flash the stack, only need the first success solution
			flag = true;
			//System.out.println(res.size());
			return;
		}
		
		for (int i=s; i<=maxNumber; i++) {
			res.add(i);
			if (isValid()) {
				//System.out.println("vali");
				dfs(i+1);
				if (flag == true) return;//flash the stack
			}
			res.remove(res.size()-1);
		}
	}
	
	public static void main (String[] args) throws IOException {
		hamming h = new hamming();
		h.read("hamming.in");
		h.dfs(0);
		h.write("hamming.out");
	}
}






