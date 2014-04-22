/*
ID: mywbob1
LANG: JAVA
TASK: transform
*/
import java.io.*;
import java.util.*;

class  transform {
	public int transPattern = 0;
	public char[][] org;
	public char[][] finl;
	public void read(String filename) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(f.readLine());
		int n = Integer.parseInt(st.nextToken());	
		org = new char[n][n];
		finl = new char[n][n];
		for (int w = 0; w <=1 ; w++) {
			for (int r = 0; r < n; r++) {
				st = new StringTokenizer(f.readLine());
				String line = st.nextToken();
				for (int c = 0; c < n; c++) {
					if (w == 0) {
						org[r][c] = line.charAt(c);
					}
					else {
						finl[r][c] = line.charAt(c);
					}
					
				}
			}
		}
	}

	public void writeResToFile(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
		out.print(transPattern);
		out.println();
		out.close();
	}
	
	public char[][] rotate(char[][] before) {//call this 1,2,3 time to implement 90 180 270, so can reuse code, no need in place
		int n = before.length;
		char[][] res = new char[n][n];
		for (int r = 0; r < n; r++) {
			for(int c = 0; c < n; c++) {
				res[c][n - r - 1] = before[r][c];
			}
		}
		return res;
	}
	
	
	public char[][] threeWaysRotate(char[][] before,  int direction) {//1,2,3 are 90, 180, 270
		if (direction == 1) 
			return rotate(before);
		else if (direction == 2) {
			char[][] temp = rotate(before);
			return rotate(temp);
		}
		else {
			char[][] temp = rotate(before);
			char[][] temp1 = rotate(temp);
			return rotate(temp1);
		}
	}
	
	public char[][] reflection(char[][] before) {
		int n = before.length;
		char[][] res = new char[n][n];
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				res[r][n-c-1] = before[r][c];
			}
		}
		return res;
		
	}
	
	public boolean isSame(char[][] before, char[][] after) {
		int n = before.length;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				if (before[r][c] != after[r][c]) return false;
			}
		}
		return true;
	}
	
	public boolean isCombination(char[][] before, char[][] after) {
		char[][] reflection = reflection(before);
		for (int i = 1; i<= 3; i++) {
			char[][] rotated = threeWaysRotate(reflection, i);
			if (isSame(rotated, after)) return true;
		}
		return false;
	}
	
	
	public int tryAllTransform(char[][] before, char[][] after) {
		
		//90
		if (isSame(threeWaysRotate(before, 1), after)) return 1;
		
		//180
		if (isSame(threeWaysRotate(before, 2), after)) return 2;
		
		//270, which is -90
		if (isSame(threeWaysRotate(before, 3), after)) return 3;
		
		//reflection
		if (isSame(reflection(before), after)) return 4;
		
		//combination, try this after all the trys above
		if (isSame(after, threeWaysRotate(reflection(before), 1)) || isSame(after, threeWaysRotate(reflection(before), 2))
			|| isSame(after, threeWaysRotate(reflection(before), 3))) return 5;
			
		//no change
		if (isSame(before, after)) return 6;
		
		//invalid
		return 7;

	}
	
	public static void main (String [] args) throws IOException {
		transform t = new transform();
		t.read("transform.in");
		t.transPattern = t.tryAllTransform(t.org, t.finl);
		t.writeResToFile("transform.out");
		System.exit(0);                               
	}
}
