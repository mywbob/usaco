/*
ID: mywbob1
LANG: JAVA
TASK: milk
*/

import java.io.*;
import java.util.*;

public class milk {
	private int total;
	private int farmers;
	private int min;
	private TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
	
	public void read(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(br.readLine());
		String line;
		total = Integer.parseInt(st.nextToken());
		farmers = Integer.parseInt(st.nextToken());
		while ((line = br.readLine()) != null) {
			st = new StringTokenizer(line);
			int price = Integer.parseInt(st.nextToken());
			int amount = Integer.parseInt(st.nextToken());
			if (map.containsKey(price)) 
				map.put(price, map.get(price) + amount);
			else
				map.put(price,amount);
		}
	}
	
	public void write(String filename) throws IOException{
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		out.println(min);
		out.close();
	}
	
	public void getMin() {
		min = 0;
		for (Integer p : map.keySet()) {
			if (map.get(p) >= total) 
				min += total * p;
			else 
				min += map.get(p) * p;

			int k = map.get(p) >= total ? total : map.get(p);
			total -= k;
			if (total <= 0) break;
		}
	}
	
	public static void main(String[] args) throws IOException {
		milk m = new milk();
		m.read("milk.in");
		m.getMin();
		m.write("milk.out");
	}
}