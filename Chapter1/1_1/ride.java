/*
ID: mywbob1
LANG: JAVA
TASK: ride
*/
import java.io.*;
import java.util.*;

class ride {
  public static void main (String [] args) throws IOException {
    // Use BufferedReader rather than RandomAccessFile; it's much faster
    BufferedReader f = new BufferedReader(new FileReader("ride.in")); // input file name goes above
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ride.out")));
    // Use StringTokenizer vs. readLine/split -- lots faster
    StringTokenizer st = new StringTokenizer(f.readLine()); // Get line, break into tokens

    
    String str1 = st.nextToken();    
    //System.out.println(str1);
    st = new StringTokenizer(f.readLine()); // Get line, break into tokens
    String str2 = st.nextToken();    
    //System.out.println(str2);
    int str1cnt = 1;//fuck, do not use 0
    int str2cnt = 1;
    for (int i = 0; i < str1.length(); i++) {
        str1cnt *= str1.charAt(i) - 'A' + 1;
    }
    //System.out.println(str1cnt);
    for (int i = 0; i < str2.length(); i++) {
        str2cnt *= str2.charAt(i) - 'A' + 1;
    }
        //System.out.println(str2cnt);
    if (str1cnt % 47 == str2cnt % 47) 
        out.println("GO");  
    else 
        out.println("STAY");  
    out.close();                                  
    System.exit(0);                               
  }
}
