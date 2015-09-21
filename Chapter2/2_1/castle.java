/*
ID: mywbob1
LANG: JAVA
TASK: castle
*/

/*
my dfs here is flood fill, visit but does not unvisit
can combine findMaxComb() and seekWalls()

/*
like word search, no need to remove/undo? see the example at point 3,1 in the figure
1:build a visit[][], then do a dfs. for each slot, check isValid and go. In the mean time, calculate the max room size and the number of rooms
2:how to find the wall? and which wall to break down?
3:do dfs once to find everything or twice? I do it sepratelly
*/

/*
for a room, visit[i][j] = room number(0 is not visited, 1,2,3,4...for room number).
keep a table of (room number: size), find the max combined size, findMaxComb()
after dfs, iterate all the walls(from left bottom, goes up, check N before E), check if we can get the max combined room size
the first found wall is the solution
*/

import java.io.*;
import java.util.*;
public class castle {
	private int l;
	private int w;
	private int[][] input;
	private int[][] visited;
	private int max = 0;
	private int smax = 0;//just smaller than max, max+ smax = maxComb, this is so wrong!!
	private int maxComb = 0;
	private int total = 0;
	private int cnt = 0;
	private int wallRow;
	private int wallCol;
	private char wallSide;
	private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	
	
	public void read(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(br.readLine());
		l = Integer.parseInt(st.nextToken());
		w = Integer.parseInt(st.nextToken());
		input = new int[w][l];
		visited = new int[w][l];
		for (int i=0; i<w; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j=0; j<l; j++) {
				input[i][j] = Integer.parseInt(st.nextToken());
			}
		}		
	}
	
	public void write(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		out.println(total);
		out.println(max);
		out.println(maxComb);
		out.print(wallRow + " " + wallCol + " " + wallSide);
		out.println();
		out.close();
	}
	
	public void findMaxComb() {
		//for each room(with certain number), find all the rooms(has different number) adj to it
		//then, find the maxcomb
		HashMap<Integer, HashSet<Integer>> adj = new HashMap<Integer, HashSet<Integer>>();
		for (int row=0; row< w; row++) {
			for (int col=0; col<l; col++) {
				HashSet<Integer> hs = new HashSet<Integer>();
				//go left
				if (inBoard(row, col-1) && visited[row][col-1] != visited[row][col]) //in board and not equal
					hs.add(visited[row][col-1]);

				//go up
				if (inBoard(row-1, col) && visited[row-1][col] != visited[row][col])
					hs.add(visited[row-1][col]);

				//go right
				if (inBoard(row, col+1) && visited[row][col+1] != visited[row][col])
					hs.add(visited[row][col+1]);

				//go down
				if (inBoard(row+1, col) && visited[row+1][col] != visited[row][col])
					hs.add(visited[row+1][col]);
				
				
				if (adj.containsKey(visited[row][col])) {
					HashSet<Integer> tt = adj.get(visited[row][col]);
					tt.addAll(hs);//union, return boolen
					adj.put(visited[row][col], tt); 
				}
				else {
					adj.put(visited[row][col], hs);
				}
				
			}
		}
		int tempMax = 0;
		for (Integer key : adj.keySet()) {
			for (Integer ele : adj.get(key)) {
				tempMax = Math.max(tempMax, map.get(key) + map.get(ele));
			}
		}
		maxComb = tempMax;
	}
	
	public boolean inBoard(int row, int col) {
		if (row < 0 || row >= w || col < 0 || col >= l) return false;
		return true;
	}
	
	public void seekRooms () {// find largest room and maxComb
		int num = 0;//room number
		for (int i=0; i<w; i++) {
			for (int j=0; j<l; j++) {
				if (visited[i][j] == 0) {
					cnt = 0;
					num++;//room number start from 1
					dfs(i,j,num);
				
					//update the number of rooms and max room size			
					max = Math.max(max, cnt); 
					total = num;
					map.put(num, cnt);
				}
			}
		}
				
		findMaxComb();// find the max size after breaking a wall!!!
		
	}
	
	public void seekWalls() {//find the wall to break
		//first row only break the E side, last col only break the N side, rigth top do not break
		//check there is a wall
		 for (int j=0; j<visited[0].length; j++){
			for (int i=visited.length-1; i>=0; i--) {			
				if (i!=0 && j!= visited[0].length-1) {//general position
					if (map.get(visited[i][j]) + map.get(visited[i-1][j]) == maxComb && !isFree(i,j,2)) {
						wallRow = i+1;
						wallCol = j+1;
						wallSide = 'N';
						return;//not break!!!!! return to exit !
					}

					if (map.get(visited[i][j]) + map.get(visited[i][j+1]) == maxComb && !isFree(i,j,4)) {
						wallRow = i+1;
						wallCol = j+1;
						wallSide = 'E';	
						return;	
					}
				}
				
				else if (i==0 && j!= visited[0].length-1) {
					if (map.get(visited[i][j]) + map.get(visited[i][j+1]) == maxComb && !isFree(i,j,4)) {
						wallRow = i+1;
						wallCol = j+1;
						wallSide = 'E';	
						return;
					}
				}

				else if (i!=0 && j== visited[0].length-1) {
					if (map.get(visited[i][j]) + map.get(visited[i-1][j]) == maxComb && !isFree(i,j,2)) {
						wallRow = i+1;
						wallCol = j+1;
						wallSide = 'N';
						return;
					}

				}
				else {//if (i==0 && j== visited[0].length-1) {
					//no solution
					return;
				}
			}
		}
	}
	
	
	public void dfs(int row, int col, int num) {
		// not the way i did before..but..check first or put first?
		// the dfs: put, check 4 direction with board and not visited, recursivlly do this
		// no need to worry about base case or anything
		visited[row][col] = num; 
		cnt ++;
		
		//go left
		if (isValid(row, col-1) && isFree(row, col, 1))
			dfs(row, col-1, num);
		
		//go up
		if (isValid(row-1, col) && isFree(row, col, 2))
			dfs(row-1, col, num);
		
		//go right
		if (isValid(row, col+1) && isFree(row, col, 4))
			dfs(row, col+1, num);
		
		//go down
		if (isValid(row+1, col) && isFree(row, col, 8))
			dfs(row+1, col, num);
	}
	
	
	public boolean isValid(int row, int col) {//with in the board(always here, wall block the bound) and not visited and no wall block the road
		if (row < 0 || row >= w || col < 0 || col >= l) return false;
		if (visited[row][col] != 0) return false;
		return true;
	}
	
	public boolean isFree(int row, int col, int dir) {//0000 - 1111, each bit => d,r,t,l
		if ((input[row][col] & dir) == 0) return true;
		return false;
		
	}
	
	public static void main (String[] args) throws IOException {
		castle c  = new castle();
		c.read("castle.in");
		c.seekRooms();
		c.seekWalls();
		c.write("castle.out");		
	}
}   
