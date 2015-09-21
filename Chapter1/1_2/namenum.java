/*
ID: mywbob1
LANG: JAVA
TASK: namenum
*/

import java.io.*;
import java.util.*;
// nice solution 2, check all the words in dict, to see if match the input
public class namenum {
	private ArrayList<String> res;
	private HashSet<String> dict;
	private String input;
	public void readInput(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		//StringTokenizer st = new StringTokenizer(br.readLine());
		this.input = br.readLine();
	}
	
	public void readDict(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		dict = new HashSet<String>();
		//StringTokenizer st;
		String line;

		while ((line = br.readLine()) != null) {
			dict.add(line);
		}
	}
	
	public void writeOutput(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
		if (res.size() == 0) out.println("NONE");
		else {
			for (String name : res) 
				out.println(name);
		}
		out.close();
	}
	
	public void findValidNames(String in) {
		res = new ArrayList<String>();
		String[] table = {"0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PRS", "TUV", "WXY"};
		dfs(0, input, "", table);
	}
	
	public void dfs(int index, String in, String ares, String[] table) {
		if (ares.length() == in.length()) {
			if (dict.contains(ares)) {
				res.add(new String(ares));
			}
			return;
		}
		
		int c = in.charAt(index) - '0';
		for (int i = 0; i < table[c].length(); i++) {
			ares = ares + table[c].charAt(i);
			dfs(index + 1, in, ares, table);
			ares = ares.substring(0, ares.length() - 1);
		}
	}
	
	public static void main (String[] args) throws IOException {
		namenum n = new namenum();
		n.readInput("namenum.in");
		n.readDict("dict.txt");
		n.findValidNames(n.input);
		n.writeOutput("namenum.out");
		System.exit(0);
	}
}

