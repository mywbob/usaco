/*
ID: mywbob1
LANG: JAVA
TASK: dualpal
*/

import java.io.*;
import java.util.*;
import java.lang.*;


public class dualpal {
	private int n;
	private int s;
	private ArrayList<Integer> num = new ArrayList<Integer>();
	public void read(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		s = Integer.parseInt(st.nextToken());
	}
	
	public void writeOutput(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
		for (int i = 0; i < num.size(); i++) {
			out.println(num.get(i));
		}
		out.close();
	}
	
	public boolean isPal(String anum) {//recur version just for fun
		if (anum.length() == 1) return true;
		if (anum.length() == 2 && anum.charAt(0) == anum.charAt(1)) return true;
		if (anum.charAt(0) != anum.charAt(anum.length() - 1)) return false;
		return isPal(anum.substring(1, anum.length() - 1));
	}
		
	public String changeBase(int anum, int base) {
		char[] table = {'0', '1', '2' ,'3', '4', '5', '6', '7', 
		'8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
		StringBuilder res = new StringBuilder();
		int rest;
		int bit;
		while (anum != 0) {
			rest = anum / base;
			bit = anum % base;
			anum = rest;
			res.append(table[bit]);
		}
		res.reverse();//add int to res, and call reverse, 12 -> 21...each int is not a single thing...
		return res.toString();
	}
	
	public void doAll () throws IOException {
		read("dualpal.in");
		int cnt;
		for (int k = s + 1; k < Integer.MAX_VALUE && num.size() < n; k++) {
			cnt = 0;
			for (int i = 2; i <= 10; i++) {
				String strnum = changeBase(k, i);
				if (isPal(strnum)) cnt++;
				if (cnt >= 2) {
					num.add(k);
					break;
				}
			}
		}
		writeOutput("dualpal.out");
	}
	
	public static void main(String[] args) throws IOException {
		dualpal p = new dualpal();
		p.doAll();
	}
}
