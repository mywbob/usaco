/*
ID: mywbob1
LANG: JAVA
TASK: crypt1
*/



import java.io.*;
import java.util.*;
//misunderstanding this problem, nothing about prime number, wtf
public class crypt1 {
	
	private int n;
	private int res=0;
	private int[] input;
	//private int aa = 0;

	private TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
	
	public void read(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		input = new int[n];
		for (int i = 0; i< n; i++) {
			input[i] = Integer.parseInt(st.nextToken());
		}
		
	}
	
	public void write(String filename) throws IOException{
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		out.println(res);
		out.close();
	}
	/*
	public boolean isPrime(int in) {//this is not necessary
		int cnt = 1;
		if (in == 2) return true;
		for (int j = 2; j <= (int)Math.sqrt((double)in); j++) {
			if (in % j == 0) cnt++;
		}

		if (cnt <= 1) return true;
		else return false;
	}
	*/
	
	public boolean val(int mul) {//nothing about prime number wtf
		while (mul != 0) {
			int last = mul % 10;
			int cnt = 0;
			mul = mul / 10;
			for (int i = 0; i < n; i++) {
				//System.out.println(i);
				
				//if ((input[i] == 3 ||input[i] == 4 ||input[i] == 6 ||input[i] == 8)&& input[i] == last) cnt++;
				if ( input[i] == last) cnt++;
			}
			if (cnt == 0) return false;
		}
		return true;
	}

	public boolean isValid(int first, int second) {
		if ((first * second > 999 && first * second <= 9999 && val(first * second)) &&
			(first * (second / 10) >=100 && first * (second / 10) <= 999 && val(first * (second / 10))) &&
			(first * (second % 10) >=100 && first * (second % 10) <= 999 && val(first * (second % 10)))) {

			return true;
		}
		else
			return false;			
	}
	
	public void getSol() {
		dfs(0, 0);
	}
	
	public void dfs(int index, int ares) {//only need 5 digits...
		if (index == 5) {
			//System.out.println(ares);
			int first = ares / 100;
			int second = ares % 100;
			if (isValid(first, second)) {res++;System.out.println(first + " * " + second + " = " + first*second);}
			//aa++;
			return;
		}
		
		for (int i=0; i<n; i++) {
			ares = 10 * ares + input[i];
			//System.out.println(ares);
			dfs(index+1, ares);
			ares =  ares / 10;
		}
	}
	

	
	public static void main(String[] args) throws IOException {
		crypt1 m = new crypt1();
		m.read("crypt1.in");
		System.out.println(m.n);
		System.out.println(m.input.length);
		m.getSol();
		m.write("crypt1.out");
	}
}