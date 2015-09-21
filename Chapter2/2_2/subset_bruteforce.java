/*
ID: mywbob1
LANG: JAVA
TASK: subset
*/

/*
TLE and MLE!!! 
1: 1
2: 2
3: 4
4: 8
n: 2^(n-1) memory, a lot

assume i have a fuction pat(int n) can genenrate all possiable two subsets. some of them have equal sum, some not
pat(n) and pat(n-1)? 
put n to result of pat(n-1) ==> pat(n) 
pat(1) => {}, {1}
pat(2) => {2}, {1}
	   => {}, {1,2}
pat(3) => {2,3}, {1}
	   => {2}, {1,3}
	   => {3}, {1,2}
	   => {}, {1,2,3}

*/

/*
pure dp?
*/

import java.io.*;
import java.util.*;

public class subset {
	class Res {
		Set<Integer> subs1;
		Set<Integer> subs2;
		
		public Res () {
			this.subs1 = new HashSet<Integer>();
			this.subs2 = new HashSet<Integer>();
			this.subs2.add(1);
		}
		
		public Res (Res r) {
			this.subs1 = new HashSet<Integer>(r.subs1);
			this.subs2 = new HashSet<Integer>(r.subs2);
		}
		
		public void addTosubset (int num, int in) {
			if (num == 1) {
				this.subs1.add(in);
			}
			if (num == 2) {
				this.subs2.add(in);
			}
		}
		
		public boolean isEqual() {
			int s1 =0 ;
			int s2 = 0;
			for (Integer i : this.subs1) {
				s1 += i;
			}
			for (Integer j : this.subs2) {
				s2 += j;
			}
			return s1 == s2;
		}
	}
	
	private int input =0 ;
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
	
	public ArrayList<Res> partation(int n) {
		if (n == 1) {
			Res result = new Res();
			ArrayList<Res> newList = new ArrayList<Res>();
			newList.add(result);
			return newList;
		}
		
		ArrayList<Res> ss = partation(n-1);
		ArrayList<Res> tt = new ArrayList<Res>();
		for (int i = 0; i < ss.size(); i++) {
			tt.add(new Res(ss.get(i)));//copy of ss
		}
		for (int i = 0; i < ss.size(); i++) {//add n to all the possible subsets...
			ss.get(i).addTosubset(1, n);
			tt.get(i).addTosubset(2, n);
		}

		ss.addAll(tt);
		return ss;
	}
	
	public int count (ArrayList<Res> r) {
		int c = 0;
		for (Res rr : r) {
			if (rr.isEqual()) c++;
		}
		return c;
	}
	
	public int doIt() {
		output = count(partation(input));
		return output;
	}
	
	
	public static void main(String[] args) throws IOException {
		subset s = new subset();
		/*
		ArrayList<Res> test = s.partation(7);
		for (Res r : test) {
			System.out.println(r.subs1 + " " + r.subs2);
		}
		*/
		s.read("subset.in");
		s.doIt();
		s.write("subset.out");
		//System.out.println(s.doIt());
		
	}
	
}










