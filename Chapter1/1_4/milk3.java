/*
ID: mywbob1
LANG: JAVA
TASK: milk3
*/

import java.io.*;
import java.util.*;

/*
search all cases
a-b
a-c
b-a
b-c
c-a
c-b

when to stop? the key thing is that, if you do a-b, b-a, a-b, b-a...we will see the same state again and again, so store
all the states that we have seen, if same state, return...so we have can stop!!, at most 20*20*20 cases, we can stop, for sure
*/

/*
java thing, hashset can take a collection, like an arraylist, arraylist should have a hashcode() already, so i do not need to write my own equal() thing
for treeset, it can take a comparator, but it is hard to write a working one...so just use hashset...
*/

public class milk3 {
	

	
	private ArrayList<Integer> cap = new ArrayList<Integer>();
	private ArrayList<Integer> init = new ArrayList<Integer>();
	//private TreeSet<ArrayList<Integer>> allStates = new TreeSet<ArrayList<Integer>>(new MyComp());
	private HashSet<ArrayList<Integer>> allStates = new HashSet<ArrayList<Integer>>();
	private TreeSet<Integer> res = new TreeSet<Integer>();

	
	public void read(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(br.readLine());
		cap.add(Integer.parseInt(st.nextToken()));
		cap.add(Integer.parseInt(st.nextToken()));
		cap.add(Integer.parseInt(st.nextToken()));
		init.add(0);
		init.add(0);
		init.add(cap.get(2));
	}
	
	public void write(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		int s = res.size();
		for (Integer key : res) {
			s--;
			out.print(key);
			if (s!=0)out.print(" ");
			else out.println();
		}
		out.close();
	}
	
	public void pour(int from, int to, ArrayList<Integer> in) {	
		if (in.get(from) + in.get(to) >= cap.get(to)) {
			int toRest = cap.get(to) - in.get(to);
			in.set(from, in.get(from) - toRest);
			in.set(to, cap.get(to));
		}
		else {
			int t = in.get(from);
			in.set(from, 0);
			in.set(to, in.get(to) + t);
		}
	}
	
	public void pourSimulator(int a, int b, int c) {
		ArrayList<Integer> curState = new ArrayList<Integer>();
		curState.add(a);
		curState.add(b);
		curState.add(c);
		
		if (allStates.contains(curState)) {//contains this state
			return;
		}
		else {
			allStates.add(curState);
			//if (curState.get(0) == 0) res.add(curState.get(2));//found a res
		}
			
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) { //for (int j = 0; j < 3 && j != i; j++) { this is wrong!!, this will terminate the loop
				if (j != i) {
					ArrayList<Integer> temp = new ArrayList<Integer>(curState);
					pour(i, j, temp);
					pourSimulator(temp.get(0), temp.get(1), temp.get(2));
				}
			}
		}
		
	}	

	public void goSimulate() {
		pourSimulator(init.get(0), init.get(1), init.get(2));
		for (ArrayList<Integer> key : allStates) {
			if (key.get(0) == 0)
				res.add(key.get(2));
		}
	}
	
	public static void main(String[] args) throws IOException {
		milk3 m = new milk3();
		m.read("milk3.in");
		m.goSimulate();
		m.write("milk3.out");
	}
}



/*
class MyComp implements Comparator<ArrayList<Integer>> {
	public int compare(ArrayList<Integer> a1, ArrayList<Integer> a2) {
		if (a1.get(0) == a2.get(0) && a1.get(1) == a2.get(1) && a1.get(2) == a2.get(2)) return 0;
		//if (a1.get(0) < a2.get(0)) return -1;
		//if (a1.get(0) > a2.get(0)) return 1;// == ?
		return 1;
	}
	
}
*/