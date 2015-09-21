/*
ID: mywbob1
LANG: JAVA
TASK: sprime
*/


/*
idea 1: dfs generate all the numbers, for each number call isSuperPrime() to check
ieda 2: dfs generate all the numbers, check if prime from most significate (first)bit while genarating the number, faster
*/


import java.io.*;
import java.util.*;


public class sprime {
	
	
	private int len;
	private ArrayList<Integer> res = new ArrayList<Integer>();
	
	/*
	public boolean isSuperPrime(int n) {

	}
	*/
	
	
	public void read(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		len = Integer.parseInt(br.readLine());
	}
	
	public void write(String filename) throws IOException{
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		for (Integer i : res) {
			out.println(i);
		}
		out.close();
	}
	
	public boolean isPrime(int n) {
		if (n == 1) return false;
		if (n == 2) return true;
		if (n % 2 == 0) return false;

		for (int i = 2; i <= (int)Math.sqrt((double)n); i++)
			if (n % i == 0) return false;
		
		return true;
	}
	

	public void findAll() {
		dfs(0,0,len);
	}
	
	public void dfs(int level, int num, int len) {
		if (level == len) {
			res.add(num);
			return;
		}
		
		for (int i=0; i<=9;i++) {
			if (isPrime(10*num + i)) {
				num = 10 * num + i;
				dfs(level+1, num, len);
				num = num / 10;
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		sprime s = new sprime();
		s.read("sprime.in");
		s.findAll();
		s.write("sprime.out");
	}

	
}