/*
ID: mywbob1
LANG: JAVA
TASK: holstein
*/

/*
1:like combination sum(no reuse), just use dfs. Need to keep the min and min res all the time(the first solution found is the final solution). or maybe store all the res
2:maybe try 1 scoop, 2 scoops...try from the smallest number, end when a res is found. max is 1000.
no need to keep min, bfs idea. but each level has at most 1000 decisions, may stack overflow. may not work
 
*/
import java.io.*;
import java.util.*;

public class holstein {
	private int[] target;
	private int ntypes;
	private int nfeed;
	private int[][] types;
	private ArrayList<Integer> res = new ArrayList<Integer>();
	private ArrayList<Integer> ares = new ArrayList<Integer>();
	
	public void read(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		ntypes = Integer.parseInt(br.readLine());
		target = new int[ntypes];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < ntypes; i++) {
			target[i] = Integer.parseInt(st.nextToken());
		}
		nfeed = Integer.parseInt(br.readLine());
		types = new int[nfeed][ntypes];
		for (int i=0; i< nfeed;i++) { 
			st = new StringTokenizer(br.readLine());
			for (int j=0; j<ntypes;j++) {
				types[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		//init res, size is larger than the max possible size
		for (int i=0; i<=ntypes; i++) res.add(i);
	}
	
	public void write(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		out.print(res.size() + " ");
		for (int i=0; i<res.size();i++) {
			out.print(res.get(i));
			if (i!= res.size()-1) out.print(" ");
			else out.println();
		}
		out.close();
	}
	
	public void findMin() {
		dfs(0, nfeed, ares);
	}
	
	public boolean check() {
		for (int i=0; i< target.length;i++) {
			if (target[i] > 0) return false;
		}
		return true;
	}
	
	public void dfs (int s, int m, ArrayList<Integer> ares) {
		System.out.println(ares);
		if (check()) {//found a res,
			if (res.size() > ares.size()) {
				res = new ArrayList<Integer>(ares);//ares changes, res changes?(i mean the list, not the value in list)
			}
			return;//return or not matters, but does not matter for this one...
		}

		
		for (int i=s; i<m; i++) {
			for (int k=0; k<ntypes;k++) target[k] = target[k] - types[i][k];
			ares.add(i+1);//i+1, offset 1, not s+1 fuck!
			dfs(i+1,m,ares);
			for (int k=0; k<ntypes;k++) target[k] = target[k] + types[i][k];
			ares.remove(ares.size()-1);
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		holstein h = new holstein();
		h.read("holstein.in");
		h.findMin();
		h.write("holstein.out");
	}



}