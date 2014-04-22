/*
ID: mywbob1
LANG: JAVA
TASK: friday
*/
import java.io.*;
import java.util.*;


class  friday {
	public int[] res = new int[8];
	public int read(String filename) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(f.readLine());
		int n = Integer.parseInt(st.nextToken());	
		return n;
	}

	public void writeResToFile(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
		res[0] = res[7];
		for (int i= 1; i<= 7; i++) {
			out.print(res[(i + 5) % 7]);
			if (i != 7) out.print(" ");
		}
		out.println();
		out.close();
	}
	//15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 1
	//5  6  7  1  2  3  4  5  6  7  1  2  3  4  5  6  7  1
	public void getRes(int n) {
		int day =  3;//1899 12 13
		for (int years = 1900; years < 1900 + n; years++) {
			for (int mon = 1; mon <= 12; mon++) {
				if (mon == 3) {//feb to march
					if (years % 4 != 0 || years % 100 == 0 && years % 400 != 0) {//not leap year
							day = day + 28 % 7;
							day = day > 7 ? day % 7 : day;
							res[day]++;
					}
					else {
						day = day + 29 % 7;
						day = day > 7 ? day % 7 : day;
						res[day]++;
					}
				}
				else if (mon == 5 || mon == 7 || mon == 10 || mon == 12) {
					day = day + 30 % 7;
					day = day > 7 ? day % 7 : day;
					res[day]++;
				}
				else {
					day = day + 31 % 7;
					day = day > 7 ? day % 7 : day;
					res[day]++;
				}
			}
		}
	}
	
	

	
	

	public static void main (String [] args) throws IOException {
		friday f = new friday();
		
		int n = f.read("friday.in");
		f.getRes(n);
		f.writeResToFile("friday.out");
		System.exit(0);                               
	}
}
