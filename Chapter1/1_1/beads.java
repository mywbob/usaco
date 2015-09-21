/*
ID: mywbob1
LANG: JAVA
TASK: beads
*/
import java.io.*;
import java.util.*;

//string, you can not use str.charAt(1) = 'x', string char can not be changed, need use substring...function

class  beads {
	//public String concatString;
	public int max = 0;
	public String readAndConcate(String filename) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(f.readLine());
		st = new StringTokenizer(f.readLine());
		String concatString = st.nextToken();
		concatString = concatString + concatString + concatString;
		return concatString;
	}

	public int findMax(String input) throws IOException {
		int cnt = 0;
		for (int index = input.length() / 3; index < input.length() / 3 * 2; index++) {
			cnt = twoWaySearch(input, index);
			max = Math.max(max, cnt);
		}
		return max;
	}
	
	public int twoWaySearch(String input, int i) {
		char[] in = input.toCharArray();
		int l = i-1;
		int r = i;
		boolean ldone = false;
		boolean rdone = false;
		while (l>0 && r<in.length-1 && r-l + 1 < in.length / 3) {
			if (in[l] == in[l-1] || in[l] == 'w' || in[l-1] =='w') {
				if (in[l-1] == 'w') {
					in[l-1] = in[l]; 
				}
				l--;	
			}	
			else ldone = true;
			
			if (in[r] == in[r+1] || in[r] == 'w' || in[r+1] == 'w') {
				if (in[r+1] == 'w') {
					in[r+1] = in[r];
				}
				r++;	
			}	
			else rdone = true;
			
			if (ldone && rdone) break;
		}
		if (r-l+1 >= in.length/3) return in.length/3;//all can count, care
		int len = r - l + 1;
		return len;
	}
	
	public void writeResToFile(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
		out.println(max);
		out.close();
	}


	public static void main (String [] args) throws IOException {
		beads b = new beads();
		
		b.findMax(b.readAndConcate("beads.in"));
		b.writeResToFile("beads.out");
		System.exit(0);                               
	}
}
