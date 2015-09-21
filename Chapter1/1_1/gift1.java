/*
ID: mywbob1
LANG: JAVA
TASK: gift1
*/
import java.io.*;
import java.util.*;

class gift1 {

	public LinkedHashMap<String, Integer> map = new LinkedHashMap<String,Integer>();
	public LinkedHashMap<String, Integer> initmap = new LinkedHashMap<String,Integer>();
	public BufferedReader f;
	public PrintWriter out;
	public StringTokenizer st;


	public void readNumAndNames(String filename) throws IOException {
		f = new BufferedReader(new FileReader(filename));
		st = new StringTokenizer(f.readLine());
		int numOfPerson = Integer.parseInt(st.nextToken());

		for (int i=0; i< numOfPerson; i++) {
			st = new StringTokenizer(f.readLine());
			String name = st.nextToken();
			map.put(name, 0);
		}

	}

	public void readAndGiveMoney() throws IOException {
		String line;
		while ((line = f.readLine()) != null) {
			String sender = line;
			st = new StringTokenizer(f.readLine());
			int total = Integer.parseInt(st.nextToken());
			int divide = Integer.parseInt(st.nextToken());
			//System.out.println(map.get(sender) + total - total % divide);
			initmap.put(sender, total);

			if (divide != 0) {
				map.put(sender, map.get(sender) + total % divide);
				int giveMoney = total / divide;
				//System.out.println(sender + " " + map.get(sender));

				for (int i=0; i< divide; i++) {
					st = new StringTokenizer(f.readLine());
					String receiver = st.nextToken();
					map.put(receiver, map.get(receiver) + giveMoney);
				}
			}
			else {
				map.put(sender, map.get(sender) + total);
			}

		}
	}

	public void generateOutput(String filename) throws IOException {
		out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
		for (String key : map.keySet()) {
			out.println(key + " " + (map.get(key) - initmap.get(key)));
		}
		out.close();
	}

	public static void main (String [] args) throws IOException {
		gift1 gf = new gift1();
		gf.readNumAndNames("gift1.in");
		gf.readAndGiveMoney();
		gf.generateOutput("gift1.out");
		System.exit(0);                               
	}
}
