/*
ID: mywbob1
LANG: JAVA
TASK: preface
*/

import java.io.*;
import java.util.*;


public class preface {
	private int n = 0;
	private Map<Character, Integer> map = new LinkedHashMap<Character, Integer>();
	private String[] table = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
	private int[] val = {1000, 900, 500, 400, 100, 90, 50, 40 , 10, 9, 5, 4, 1};
	
	
	public void read(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		n = Integer.parseInt(br.readLine());
		map.put('I',0);
		map.put('V',0);
		map.put('X',0);
		map.put('L',0);
		map.put('C',0);
		map.put('D',0);
		map.put('M',0);
	}
	
	public void write(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		for (Character ch : map.keySet()) {
			if (map.get(ch) != 0) {
				out.print(ch);
				out.print(" ");
				out.println(map.get(ch));
			}
		}
		out.close();
	}
	
	
	public String intToRoman(int in) {
		String temp = "";
			
		for (int i = 0; i<val.length; i++) {
			while (in - val[i] >= 0) {
				temp = temp + table[i];
				in = in - val[i];
			}
		}	
		return temp;
	}
	
	public void doIt() {
		for (int i=1; i<=n ;i++) {
			cntRes(intToRoman(i));
		}
	}
	
	public void cntRes(String str) {
		for (int i=0; i<str.length(); i++) {
			map.put(str.charAt(i), map.get(str.charAt(i)) + 1);
		}
 	}

	public static void main(String[] args) throws IOException{
		preface p = new preface();
		p.read("preface.in");
		p.doIt();
		p.write("preface.out");
		
	}
}








