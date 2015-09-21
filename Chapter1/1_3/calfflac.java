/*
ID: mywbob1
LANG: JAVA
TASK: calfflac
*/

import java.io.*;
import java.util.*;
//can keep two text, one just has a-z and A-Z, find pal in this text, then find the corresponding text in org text...maybe easier
//two pointers are not easy to implemnt..summary this quesiton, why I always got indexoutofbound exception
public class calfflac {
	class Result {
		String resStr;
		int resLen;
		public Result(String s, int l) {
			resStr = s;
			resLen = l;
		}
	}
	
	private String input;
	private Result res= new Result("", 0);

	private TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
	
	public void read(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");//they want newline, wtf
		}
		input = sb.toString();
	}
	
	public void write(String filename) throws IOException{
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		out.println(res.resLen);
		out.println(res.resStr);//can print many lines...if we have "\n" in input
		out.close();
	}
	
	public Result twoWaysExpand(int l, int r) {
		int ll = -1;
		int rr = -1;
		int cnt = -1;//input just "a"
		int who = 1;//1 or 2, 1 split on number, 2 split between numbers
		if (l != r) {who = 2; cnt = 0;}
		while(l >= 0 && r < input.length()) {
			while ((l >= 0 && r < input.length()) &&
				(!(input.charAt(l) >= 'a' && input.charAt(l) <= 'z' ||
				input.charAt(l) >= 'A' && input.charAt(l) <= 'Z'))) l--;
		
			while ((l >= 0 && r < input.length()) &&
				(!(input.charAt(r) >= 'a' && input.charAt(r) <= 'z' ||
				input.charAt(r) >= 'A' && input.charAt(r) <= 'Z'))) r++;
		
			if ((l >= 0 && r < input.length()) && 
				(Math.abs(input.charAt(l) - input.charAt(r)) == 0 ||
				Math.abs(input.charAt(l) - input.charAt(r)) == Math.abs('A' - 'a'))) {
				l--;
				r++;
				cnt = cnt + 2;
				//record the last working bound, (etc, found some substrings are pal)
				ll = l;
				rr = r;
			}
			else break;
		}

		String str;
		//point is between numbers and not pal(etc, ab), or no last working bound(etc, a@b, stand on @)
		if (l+1 == r || (ll == -1 && rr == -1)) str = "";//fucked, summary this!
		else str =  input.substring(ll+1, rr);//return the last working bound
		Result ret = new Result(str,cnt);
		return ret;
	}
	
	public void getPal() {
		for (int i = 0; i < input.length() - 1; i++) {
			Result str1 = twoWaysExpand(i, i);
			Result str2 = twoWaysExpand(i, i+1);
			//upadte longest pal
			Result temp = str1.resLen >= str2.resLen ? str1 : str2;
			res = res.resLen >= temp.resLen ? res : temp;
		}	
	}
	
	public static void main(String[] args) throws IOException {
		calfflac m = new calfflac();
		m.read("calfflac.in");
		m.getPal();
		m.write("calfflac.out");
	}
}