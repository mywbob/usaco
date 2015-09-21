/*
ID: mywbob1
LANG: JAVA
TASK: milk2
*/
import java.io.*;
import java.util.*;
//the farmers rise at 5 am each morning? this is confusing, if int larger than 24 * 60 * 60
//sorted? i guess not

class  milk2 {
	class Interval {
		int start;
		int end;
		public Interval(int s, int e) {
			this.start = s;
			this.end = e;
		}
	}
	
	class MyComp implements Comparator<Interval> {
		public int compare(Interval i1, Interval i2) {
			if (i1.start > i2.start) return 1;
			else if (i1.start == i2.start) return 0;
			else return -1;
		}
	} 
	
	
	public ArrayList<Interval> input = new ArrayList<Interval>();
	public int longestMilked = 0;
	public int longestVoid = 0;
	public void read(String filename) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(f.readLine());
		int n = Integer.parseInt(st.nextToken());	
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(f.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			input.add(new Interval(s, e));
		}
	}

	public void writeResToFile(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
		out.print(longestMilked);
		out.print(" ");
		out.print(longestVoid);
		out.println();
		out.close();
	}

	public void mergeAndGetRes(ArrayList<Interval> in) {
		Collections.sort(in, new MyComp());//this is the right way to do it
		ArrayList<Interval> res = new ArrayList<Interval>();
		res.add(in.get(0));
		int prev = 0;
		longestMilked = res.get(0).end - res.get(0).start;//init
		//longestVoid = res.get(0).start;//what? the start idle does not count
		longestVoid = 0;//init
		for (int i = 1; i < in.size(); i++) {
			if (in.get(i).start > res.get(prev).end) {//start a new one, update longestVoid and longestMilked
				res.add(in.get(i));
				longestMilked = Math.max(longestMilked, res.get(prev).end - res.get(prev).start);
				longestVoid = Math.max(longestVoid, in.get(i).start - res.get(prev).end);
				prev++;
			}
			else {
				if (in.get(i).end <= res.get(prev).end) {//skip, complete overlap
					//do not need to anything
				}
				else {//update res, update longestMilked
					res.get(prev).end = in.get(i).end;
					longestMilked = Math.max(longestMilked, res.get(prev).end - res.get(prev).start);
				}
			}
		}
	}
	


	public static void main (String [] args) throws IOException {
		milk2 m = new milk2();
		m.read("milk2.in");
		m.mergeAndGetRes(m.input);
		m.writeResToFile("milk2.out");
		System.exit(0);                               
	}
}
