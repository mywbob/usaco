/*
ID: mywbob1
LANG: JAVA
TASK: palsquare
*/

import java.io.*;
import java.util.*;
import java.lang.*;


public class palsquare {
	private int base;
	private ArrayList<String> num = new ArrayList<String>();
	private ArrayList<String> sq = new ArrayList<String>();
	public void read(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		base = Integer.parseInt(br.readLine());
	}
	
	public void writeOutput(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
		for (int i = 0; i < num.size(); i++) {
			out.print(num.get(i));
			out.print(" ");
			out.print(sq.get(i));
			out.println();
		}
		out.close();
	}
	
	public boolean isPal(String anum) {//recur version just for fun
		if (anum.length() == 1) return true;
		if (anum.length() == 2 && anum.charAt(0) == anum.charAt(1)) return true;
		if (anum.charAt(0) != anum.charAt(anum.length() - 1)) return false;
		return isPal(anum.substring(1, anum.length() - 1));
	}
		
	public String changeBase(int anum) {
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
		read("palsquare.in");
		for (int i = 1; i<=300; i++) {
			String strnum = changeBase(i);
			String strsq = changeBase(i*i);
			if (isPal(strsq)) {
				num.add(strnum);
				sq.add(strsq);
			}
		}
		writeOutput("palsquare.out");
	}
	
	public static void main(String[] args) throws IOException {
		palsquare p = new palsquare();
		p.doAll();
	}
}
