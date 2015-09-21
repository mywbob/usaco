/*
ID: mywbob1
LANG: JAVA
TASK: lamps
*/

/*
there are more insight that I did not see, check the solution. 
The main insight is that no matter which switches get pressed, the light pattern will repeat every six lamps.? this is what i missed


order does not matter
each button can be pressed odd or even times => 2*2*2*2 = 2^4 states at most(some state may not be able to reached)
if c >= 4 all states can be reached? 
or build a talbe of 16 states? no

KEY:
seems that if c is odd, can have all the states with odd number of 1s, odd pattern
...................even...............................even.........1s, even pattern

KEY:
c = 0 ,1 ,2, 3 are speacal?
c = 0 only one pattern
c = 1 only 4 pattern
c = 2 , have c = 0 pattern and those states with 2 1s. (same as other even number, except, no c = 15 pattern)
c = 3 is not special, like other odd number > 1



can use 4 nested for to generate all the possilbe state, but too large

hwo to sort the result? i guess change it to string, string natural order 
*/

import java.io.*;
import java.util.*;

public class lamps {
	private int[][] states = {// button 3,2,1,0
		{0, 0, 0, 0}, //c = 0   0	
		{0, 0, 0, 1}, //c = 1	1	
		{0, 0, 1, 0}, //c = 1	2
		{0, 0, 1, 1}, //c = 2	3
		{0, 1, 0, 0}, //c = 1	4
		{0, 1, 0, 1}, //c = 2	5	
		{0, 1, 1, 0}, //c = 2	6
		{0, 1, 1, 1}, //c = 3	7	
		{1, 0, 0, 0}, //c = 1	8
		{1, 0, 0, 1}, //c = 2	9
		{1, 0, 1, 0}, //c = 2	10
		{1, 0, 1, 1}, //c = 3	11
		{1, 1, 0, 0}, //c = 2	12
		{1, 1, 0, 1}, //c = 3	13
		{1, 1, 1, 0}, //c = 3	14
		{1, 1, 1, 1}, // 		15
	};
	
	private int[][] all;
	private int n;
	private int c;
	private ArrayList<Integer> onIndex = new ArrayList<Integer>();
	private ArrayList<Integer> offIndex =new ArrayList<Integer>();
	private TreeSet<String> set = new TreeSet<String>();
	
	
	public void read(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		c = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		int cnt = st.countTokens();
		while (cnt > 1) {
			onIndex.add(Integer.parseInt(st.nextToken()));
			cnt--;
		} 
		
		st = new StringTokenizer(br.readLine());
		cnt = st.countTokens();
		while (cnt > 1) {
			offIndex.add(Integer.parseInt(st.nextToken()));
			cnt--;
		}	
	}
	
	public void write(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		if (set.size() == 0) out.println("IMPOSSIBLE");
		else {
			for (String ss : set)
				out.println(ss);
		}
		out.close();
	}
	
	public void switchIt(int p) {
		//b0
		if (states[p][0] == 1) {
			for (int i=0; i<n;i++) {
				all[p][i] = all[p][i] == 1 ? 0 : 1;
			}
		}
		//b1
		if (states[p][1] == 1) {
			for (int i=0; i<n;i++) {
				if ((i+1) % 2 != 0)
					all[p][i] = all[p][i] == 1 ? 0 : 1;
			}			
		}
		//b2
		if (states[p][2] == 1) {
			for (int i=0; i<n;i++) {
				if ((i+1) % 2 == 0)
					all[p][i] = all[p][i] == 1 ? 0 : 1;
			}	
		}
		//b3
		if (states[p][3] == 1) {
			for (int i=0; i<n;i++) {
				if ((i+1) % 3 == 1)
					all[p][i] = all[p][i] == 1 ? 0 : 1;
			}	
		}
	}
	
	public void generateAll() {//generate all 16 states, then pick certain pattern, not sorted
		//init to all to 1
		all = new int[16][n];
		for (int i=0; i<16; i++) 
			for (int j=0; j<n; j++) {
				all[i][j] = 1;
			}
		
		
		//generate all then pick 
		for (int i =0 ; i<16; i++)
			switchIt(i);
		
		
		//pick
		ArrayList<String> sstr = new ArrayList<String>();
		for (int i=0; i<16; i++) {
			String temp="";
			if (c % 2 != 0) {
				if (c == 1) {					
					if (i == 1 || i ==2 || i == 4 || i ==8) {
						for (int j=0; j<n;j++) {
							temp = temp + all[i][j];					
						}
						if (contains(temp))
						sstr.add(temp);
					}
				}
				else {
					if (i == 1 || i ==2 || i == 4 || i ==7 || i==8 || i == 11 || i ==13 || i ==14) {
						for (int j=0; j<n;j++) {
							temp = temp + all[i][j];
						}
						if (contains(temp))
						sstr.add(temp);
					}
					
				}
			}
			else {
				if (c==0) {
					if (i == 0) {
						for (int j=0; j<n;j++) {
							temp = temp + all[i][j];						
						}
						if (contains(temp))
						sstr.add(temp);
					}
				}
				else if (c == 2) {
					if (i == 0 || i == 3 || i ==5 || i == 6 || i ==9 || i==10 || i == 12) {
						for (int j=0; j<n;j++) {
							temp = temp + all[i][j];	
						}
						if (contains(temp))
						sstr.add(temp);
					}			
				}
				else {
					if (i == 0 || i ==3 || i == 5 || i ==6 || i==9 || i == 10 || i ==12 || i ==15) {
						for (int j=0; j<n;j++) {
							temp = temp + all[i][j];
						}
						if (contains(temp))
						sstr.add(temp);
					}
				}
			}
		}
		
		
		//sort the string, string natrual order is the order we want(binary small to large order), 
		//use treeset to sort, can remove dup also
				
		for (int i=0; i< sstr.size(); i++)
			set.add(sstr.get(i));
		
	}
	
	public boolean contains(String s) {//the right configurations
		for (int i=0; i<onIndex.size(); i++) {
			if (s.charAt(onIndex.get(i)-1) != '1') return false;
		}
		
		for (int i=0; i<offIndex.size(); i++) {
			if (s.charAt(offIndex.get(i)-1) != '0') return false;
		}
		return true;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		lamps l = new lamps();
		l.read("lamps.in");
		l.generateAll();
		l.write("lamps.out");

	}
}