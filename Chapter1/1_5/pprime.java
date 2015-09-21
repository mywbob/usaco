/*
ID: mywbob1
LANG: JAVA
TASK: pprime
*/

import java.io.*;
import java.util.*;

/*
check if palindromes first is faster, why? 
my isPal runs o(n), n is the number of bits, at most 9 here
check if prime runs o(sqrt(n)), n is the number, can be large
around 9^5 + 9^4 + 9^3 + 9^2 pal(maybe)
around 5 million prime number
Key: even bit of pal is not prime number, except 11.(all other number % 11 ==0, do not knwo how to prove it)
*/


public class pprime {
	private int a;
	private int b;
	private ArrayList<Integer> res = new ArrayList<Integer>();//a init size does not help
	public void read(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(br.readLine());
		a = Integer.parseInt(st.nextToken());
		b = Integer.parseInt(st.nextToken());
	}
	
	public void write(String filename) throws IOException{
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		for (Integer i : res) {
			out.println(i);
		}
		out.close();
	}
	
	public void findAll() {// can not pass last test
		for (int i=a; i<= b; i++) {
			if (i % 2 != 0 && !evenBits(i) && isPal(i)) {//do not check even number, skip even bits pal

				boolean isPrime = true;
				for (int n = 2; n <= (int)Math.sqrt((double)i); n++) {
					if (i % n == 0) {isPrime = false;break;}
				}
			
				if (isPrime) res.add(i);
			}
		}
		
	}
	
	public boolean evenBits(int n) {
		if (n==11) return false;
		if (n>=100000000) return false;
		if (n>=10000000) return true;
		if (n>=1000000) return false;
		if (n>=100000) return true;
		if (n>=10000) return false;
		if (n>=1000) return true;
		if (n>=100) return false;
		if (n>=10) return true;
		if (n>=1) return false;
		return false;//or error
	}
	
	public boolean isPal(int num) {//reverse int, the num less than max int, so safe no overflow
		int rev = 0;
		int last = 0;
		int rest = 0;
		int temp = num;
		while (num >0) {
			rest = num / 10;
			last = num % 10;
			rev = 10 * rev + last;
			num = rest;
		}
		return temp == rev;
		
	}
	
	/*
	public boolean isPalStr(int num) {//this is slow
		String s = Integer.toString(num);
		int l = 0;
		int r = s.length()-1;
		while (l<=r) {
			if (s.charAt(l) != s.charAt(r)) return false;
			l++;
			r--;
		}
		return true;
	}
	*/
	

	
	
	public static void main(String[] args) throws IOException {
		pprime p = new pprime();
		p.read("pprime.in");
		p.findAll();
		p.write("pprime.out");

	}	
}