/*
ID: mywbob1
LANG: JAVA
TASK: barn1
*/
import java.io.*;
import java.util.*;

public class barn1 {
	private int boards;
	private int stalls;
	private int occupied;
	private int emptyStalls; //not including those leading and tailing empty stalls
	private int[] s;
	private int min;
	private TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>(Collections.reverseOrder());//sorted empty sections
	public void read(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(br.readLine());
		String line;
		boards = Integer.parseInt(st.nextToken());
		stalls = Integer.parseInt(st.nextToken());
		occupied = Integer.parseInt(st.nextToken());
		s = new int[stalls];
		while ((line = br.readLine()) != null) {
			int i = Integer.parseInt(line);
			s[i - 1] = 1;//label occupied
		}
	}
	
	public void write(String filename) throws IOException{
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		out.println(min);
		out.close();
	}
	
	public void countConsecutiveEmptyStalls() {//skip leading and tailing empty stalls
		int start = 0;
		int end = s.length - 1;
		while (start < s.length && s[start] == 0) start++;
		while (end >= 0 && s[end] == 0) end--;
		min = end - start + 1;//init the min
		int cur = start;
		emptyStalls = 0;
		while (cur < end) {
			int cnt = 0;
			while (cur < end && s[cur] != 0) cur++;
			while (cur < end && s[cur] == 0) {cur++; cnt++;}
			if (map.containsKey(cnt))
				map.put(cnt, map.get(cnt) + 1);
			else 
				map.put(cnt, 1);

			emptyStalls += cnt;//count middle empty stalls
		}
	}
	
	public void findMinCover() throws IOException{//find the largest section then break the board
		for (Integer section : map.keySet()) {
			while (map.get(section) > 0) {
				if (boards <= 1 || emptyStalls == 0) break;
				else {
					boards--;
					min -= section;
					map.put(section, map.get(section) - 1);
					emptyStalls -= section;
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		barn1 b = new barn1();
		b.read("barn1.in");
		b.countConsecutiveEmptyStalls();
		b.findMinCover();
		b.write("barn1.out");
	}
}