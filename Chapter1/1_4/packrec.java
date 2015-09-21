/*
ID: mywbob1
LANG: JAVA
TASK: packrec
*/


/*
!!!!!!! take x as l, y as w or backwards, no need to thing longer one is l..we need to rotate anyway
6 basic layouts, for each layout, i can rotate(2*2*2*2 = 16), also, for each layout, i can change the positon of the 4 rectangles(4*3*2*1 = 24).
so I have 6 * 16 * 24 cases.
seach them all. 
some layout are same...(layout 4, and layout 5) so I have 5 layouts
How to rotate? just swap the w and l
also, for each layout, calculate the area(W * L), then find the smallest area
*/
//http://blog.csdn.net/philips123/article/details/6319094

/*
summary how the copy and add(new arraylist<>) thing work!!!!!!!
*/




import java.io.*;
import java.util.*;

public class packrec {
	class Rec {
		int l;
		int w;
		public Rec(int l, int w) {
			this.l = l;
			this.w = w;
		}
		public Rec(Rec a) {
			this.l = a.l;
			this.w = a.w;
		}
		public void sort() {
			if (this.w > this.l) {
				int t = this.w;
				this.w = this.l;
				this.l = t;
			}
		}
		public void swap() {
				int t = this.w;
				this.w = this.l;
				this.l = t;
		}
	}
	
	class MyComp implements Comparator<Rec> {
		public int compare(Rec r1, Rec r2) {
			if (r1.w == r2.w && r1.l == r2.l) return 0;
			else if (r1.w < r2.w) return -1;
			else return 1;
		}
	}
	
	int min = Integer.MAX_VALUE;
	ArrayList<Rec> res = new ArrayList<Rec>();//l >= w, the large rec 
	ArrayList<Rec> input = new ArrayList<Rec>();
	
	public void read(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st;
		String line;
		while ((line = br.readLine()) != null) {
			st = new StringTokenizer(line);
			int first = Integer.parseInt(st.nextToken());
			int second = Integer.parseInt(st.nextToken());
			Rec a = new Rec(first, second);
			a.sort();
			input.add(a);//ref to a, so ok
		}
		
	}
	
	public void write(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		sortTheRes();

		out.println(min);
		for (int i=0; i< res.size(); i++) {
				out.print(res.get(i).w);
				out.print(" ");
				out.println(res.get(i).l);
		}
		out.close();
		
	}
	
	public void sortTheRes() {
		TreeSet<Rec> set = new TreeSet<Rec>(new MyComp());
		for (int i=0; i<res.size(); i++) {//make sure w <= l
			res.get(i).sort();
		}
		for (Rec a : res) {// sort by w
			set.add(a);
		}
		res.clear();
		for (Rec a : set) {// sort by w
			res.add(a);
		} 
	}
	
	
	
	
	public ArrayList<ArrayList<Rec>> generateAll() {//generate 16 * 24 group of recs
		ArrayList<ArrayList<Rec>> per = permutation(input);
		ArrayList<ArrayList<Rec>> all = rotation(per);
		return all;
	}
	
	public ArrayList<ArrayList<Rec>> permutation (ArrayList<Rec> a) {
		ArrayList<ArrayList<Rec>> p = new ArrayList<ArrayList<Rec>>();
		for (int i=0; i< 4; i++)
			for (int j=0; j< 4; j++)
				for(int k=0; k<4; k++)
					for(int l=0; l<4; l++) {
						if (i!=j && i!=k && i!=l && j!=k && j!=l && k!=l) {
							ArrayList<Rec> one = new ArrayList<Rec>();
							one.add(a.get(i));
							one.add(a.get(j));
							one.add(a.get(k));
							one.add(a.get(l));
							p.add(new ArrayList<Rec>(one));//new matters?
						}
					}
		return p;
	}
	
	public ArrayList<ArrayList<Rec>> rotation (ArrayList<ArrayList<Rec>> a) {//!!!!!this is not deep copy!!! both arraylist just contain the ref to same obj!!!string is ok
		ArrayList<ArrayList<Rec>> r = new ArrayList<ArrayList<Rec>>();
		for (int s=0; s<a.size();s++)
			for (int i=0; i<2;i++)
				for (int j=0; j<2;j++)
					for (int k=0; k<2;k++)
						for (int l=0; l<2;l++) {
							//ArrayList<Rec> ares = new ArrayList<Rec>(new ArrayList<Rec>(a.get(0)));//new matters? differences? seems no help  with new here
							ArrayList<Rec> ares = deepCopy(a.get(s));
							//System.out.println(ares.get(0).w + " " + ares.get(0).l + "   " + ares.get(1).w + " " +ares.get(1).l + "   " + ares.get(2).w + " " +ares.get(2).l + "   "  + ares.get(3).w + " " +ares.get(3).l );
							//System.out.println(a.get(0).get(0).w + " " + a.get(0).get(0).l + "   " + a.get(0).get(1).w + " " +a.get(0).get(1).l + "   " + a.get(0).get(2).w + " " +a.get(0).get(2).l + "   "  + a.get(0).get(3).w + " " +a.get(0).get(3).l );
							
							if (i!=0) ares.get(0).swap();
							if (j!=0) ares.get(1).swap();
							if (k!=0) ares.get(2).swap();
							if (l!=0) ares.get(3).swap();
							//System.out.println(ares.get(0).w + " " + ares.get(0).l + "   " + ares.get(1).w + " " +ares.get(1).l + "   " + ares.get(2).w + " " +ares.get(2).l + "   "  + ares.get(3).w + " " +ares.get(3).l );
							//System.out.println();
							r.add(new ArrayList<Rec>(ares));//new matters?
						}
		
		return r;
	}
	
	public ArrayList<Rec> deepCopy (ArrayList<Rec> in) {// fuck this
		ArrayList<Rec> r = new ArrayList<Rec>();
		for (Rec a : in) {
			r.add(new Rec(a));
		}
		return r;
	}
	
	public void findMinArea(ArrayList<ArrayList<Rec>> in) {
		for (ArrayList<Rec> a : in) {
			getArea(a);
		}
	}
	
	public void getArea(ArrayList<Rec> a) {//	
		Rec ares = new Rec(Integer.MAX_VALUE, Integer.MAX_VALUE);
		for (int i = 1; i <= 5; i++) {
			if (i == 1) {
				ares.w = a.get(0).w + a.get(1).w + a.get(2).w + a.get(3).w;
				ares.l = Math.max(Math.max(Math.max(a.get(0).l, a.get(1).l), a.get(2).l), a.get(3).l);
				//System.out.println(min + "     " + ares.l + " " + ares.w);
				if (ares.w * ares.l == 36 || ares.w * ares.l == 35) System.out.println(i);
			}
			/*
			0 1 2
			3
			*/
			else if (i==2) {
				ares.w = Math.max(a.get(0).w + a.get(1).w + a.get(2).w, a.get(3).l);
				ares.l = Math.max(Math.max(a.get(0).l, a.get(1).l), + a.get(2).l) + a.get(3).w;
				//System.out.println(min + "     " + ares.l + " " + ares.w);
				if (ares.w * ares.l == 36 || ares.w * ares.l == 35) System.out.println(i);
			}
			/*
			0 1 2 
			3
			*/
			else if (i==3) {
				ares.w = Math.max(a.get(0).w + a.get(1).w + a.get(2).w, a.get(3).l + a.get(2).w);
				ares.l = Math.max(Math.max(a.get(0).l , a.get(1).l) + a.get(3).w, a.get(2).l);
				//System.out.println(min + "     " + ares.l + " " + ares.w);
				if (ares.w * ares.l == 36 || ares.w * ares.l == 35) System.out.println(i);
			}
			/*
			0 1 2
			  3
			*/	
			else if (i==4) {
				ares.w = Math.max(a.get(1).w, a.get(3).w) + a.get(0).w + a.get(2).w;
				ares.l = Math.max(Math.max(a.get(0).l, a.get(2).l), a.get(1).l + a.get(3).l);
				//System.out.println(min + "     " + ares.l + " " + ares.w);
				//System.out.println(a.get(0).w + " " + a.get(0).l + "   " + a.get(1).w + " " +a.get(1).l + "   " + a.get(2).w + " " +a.get(2).l + "   "  + a.get(3).w + " " +a.get(3).l );
				if (ares.w * ares.l == 36 || ares.w * ares.l == 35) System.out.println(i);
			}
			/*
			0 1
			2 3
			*/
			else if (i==5) {
				//if (a.get(0).l + a.get(2).l > a.get(3).l)
					//ares.w = Math.max(a.get(2).w + a.get(3).w, a.get(0).w + a.get(1).l);
				//else 
					//ares.w = Math.max(a.get(2).w + a.get(3).w, a.get(1).l);//rec 0 does not affect the w
				
				if (a.get(2).l >= a.get(1).l + a.get(3).l) 
					ares.w = Math.max(Math.max(a.get(0).w,a.get(1).w+a.get(2).w),a.get(2).w+a.get(3).w);
				if (a.get(2).l > a.get(3).l && a.get(2).l < a.get(1).l + a.get(3).l) 
					ares.w =Math.max(Math.max(a.get(0).w+a.get(1).w, a.get(1).w+a.get(2).w), a.get(2).w+a.get(3).w);
				if (a.get(3).l > a.get(2).l && a.get(3).l < a.get(0).l + a.get(2).l) 
					ares.w =Math.max(Math.max(a.get(0).w+a.get(1).w, a.get(0).w+a.get(3).w), a.get(2).w+a.get(3).w);
				if (a.get(3).l >= a.get(0).l + a.get(2).l) 
					ares.w =Math.max(Math.max(a.get(1).w, a.get(0).w+a.get(3).w),a.get(2).w+a.get(3).w);
				if (a.get(2).l == a.get(3).l) 
					ares.w =Math.max(a.get(0).w+a.get(1).w,a.get(2).w+a.get(3).w);
		
				
				ares.l = Math.max(a.get(0).l + a.get(2).l, a.get(1).l + a.get(3).l);
				
				//System.out.println(min + "     " + ares.l + " " + ares.w);
				if (ares.w * ares.l == 49 || ares.w * ares.l == 35) System.out.println(i);
			}
			else {
				//error
			}
		
			//System.out.println(min + "     " + ares.l + " " + ares.w);
			//update min and res
			
			
			//check this!!!!!!!!!
			if (ares.l * ares.w == min) { 
				res.add(new Rec(ares));
			}
			if (ares.l * ares.w < min) {
				res.clear();
				res.add(new Rec(ares));
				//System.out.println(ares.l + " " +ares.w);
				min = ares.l * ares.w;
			}

			//System.out.println(min);
		}
	}
	
	public static void main (String[] args) throws IOException{
		packrec p = new packrec();
		p.read("packrec.in");
		p.findMinArea(p.generateAll());
		p.write("packrec.out");
		/*
		ArrayList<ArrayList<Rec>> per = p.permutation(p.input);
		for (int i = 0; i< per.size();i++) {
			for (int j=0;j<per.get(i).size();j++) {
				System.out.print(per.get(i).get(j).w + " " + per.get(i).get(j).l + "    ");
			}
			System.out.println();
		}
		
		p.rotation(per);
		*/
		
	}

}