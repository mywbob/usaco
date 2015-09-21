/*
ID: mywbob1
LANG: JAVA
TASK: frac1
*/

import java.io.*;
import java.util.*;

public class frac1 {
	private int n;
	private TreeMap<Double, String> res = new TreeMap<Double, String>();
	
	
	public void read (String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		n = Integer.parseInt(br.readLine());
	}
	
	public void write (String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		for (Double d : res.keySet()) {
			out.println(res.get(d));
		}
		out.close();
	}
	
	public void generateAll () {
		for (int i=1; i<=n; i++) {
			for (int j=0; j<= i; j++) {
				double k = (double)j / (double)i;
				if (!res.containsKey(k)) res.put(k, j + "/" +i);
			}
		}
	}
	
	public static void main (String[] args) throws IOException {
		frac1 f = new frac1();
		f.read("frac1.in");
		f.generateAll();
		f.write("frac1.out");
	}
	
	
	
	
	
}