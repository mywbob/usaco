/*
ID: mywbob1
LANG: JAVA
TASK: runround
*/

/*
use mod to find the rotate position
(index + val[index]) % len = new index for next number

int to string or char[], easier
*/

import java.io.*;
import java.util.*;

public class runround {
	private int input = 0;
	private int output = 0;
	public void read(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		input = Integer.parseInt(br.readLine());
	}
	
	public void write(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		out.println(output);
		out.close();
	}
	
	public void findNext() {
		int t = input;
		while (true) {
			t++;
			if (check(t)) break;
		}
		
		output = t;
	}
	
	public boolean check(int in) {
		String str = Integer.toString(in);
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		if (unique(str, map)) {//check all number unique first
			int index = 0;
			int newindex = 0;
			int len = str.length();
			for (int i=0; i< str.length(); i++) {// do it len times..
				newindex = (index + (str.charAt(index) - '0')) % len;
				map.put(str.charAt(newindex), map.get(str.charAt(newindex)) + 1);
				if (map.get(str.charAt(newindex)) > 2) {
					//if conflict any rule
					return false;
				}
				index = newindex;//fuck do not forget this, to update index

			}
			return true;
		}
		else return false;
	}
	
	public boolean unique(String s, HashMap<Character, Integer> map) {
		for (int i=0; i<s.length(); i++) {
			if (map.containsKey(s.charAt(i)) || s.charAt(i) == '0') return false;//has dup
			else {
				map.put(s.charAt(i), 1);
			}
		}
 		return true;//no dup
	}
	
	public static void main(String[] args) throws IOException {
		runround r = new runround();
		r.read("runround.in");
		r.findNext();
		r.write("runround.out");
	}
}