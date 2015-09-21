/*
ID: mywbob1
LANG: JAVA
TASK: ariprog
*/

import java.io.*;
import java.util.*;

//hashset is o(1) access, but, much slower than an array...so use an array to build the "hashset". wtf
public class ariprog {
	private int N;
	private int M;
	private int max;
	private HashSet<Integer> set;
	private int[] arraySet;
	private TreeMap<Integer, ArrayList<Integer>> res = new TreeMap<Integer, ArrayList<Integer>>();
	
	public void read(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		/*
		set = new HashSet<Integer>(200000);
		for (int i = 0; i <= M; i++)
			for (int j = i; j <=M; j++)
				set.add(i*i + j*j);
		*/
		
		
		arraySet = new int[200000];
		for (int kk = 0; kk<200000;kk++) arraySet[kk] = -1;
		for (int i = 0; i <= M; i++)
			for (int j = i; j <=M; j++)
				arraySet[i*i + j*j] = i*i + j*j;
		
		
		max = 2 * M * M;
				
	}
		
	public void write(String filename) throws IOException{
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		if (res.size() == 0) out.println("NONE");
		else {
			for (Integer key : res.keySet()) {
				for (int i=0; i<res.get(key).size();i++) {
					out.print(res.get(key).get(i) + " ");
					out.print(key);
					out.println();
				}
			}
		}
		out.close();
	}
	
	/*
	public void findAll() {
		for (int a = 0; a <= max; a++) {
			if (set.contains(a)) {
				for (int diff = 1; diff <= max && (a + (N-1) * diff <= max); diff++) {//cut some here
					boolean found = true;
					for (int c =  0; c < N; c++) {// the len is N
						if (!set.contains(a +  c * diff)) {found = false; break;}
						
					}
					if (found) {
						if (res.containsKey(diff)) {
							res.get(diff).add(a);
						}
						else {
							ArrayList<Integer> ares = new ArrayList<Integer>();
							ares.add(a);
							res.put(diff, ares);
						}
					}
					
				}
			}
		}
		
	}
	*/
	
	
	
	public void findAll() {
		for (int a = 0; a <= max; a++) {
			if (arraySet[a] != -1) {
				for (int diff = 1; diff <= max && (a + (N-1) * diff <= max); diff++) {//cut some here
					boolean found = true;
					for (int c =  0; c < N; c++) {// the len is N
						if (arraySet[a +  c * diff] == -1) {found = false; break;}
						
					}
					if (found) {
						if (res.containsKey(diff)) {
							res.get(diff).add(a);
						}
						else {
							ArrayList<Integer> ares = new ArrayList<Integer>();
							ares.add(a);
							res.put(diff, ares);
						}
					}
					
				}
			}
		}
		
	}
	
	
	
	public static void main(String[] args) throws IOException{
		ariprog a = new ariprog();
		a.read("ariprog.in");
		a.findAll();
		a.write("ariprog.out");
	}
	
	
}