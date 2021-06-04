package io4;

import java.io.*;

public class Io4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int inData;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintStream ps = new PrintStream(System.out);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ps));
		
		try {
			while(true) {
				inData = br.read();
				if(inData == -1) break;
				char c = (char) inData;
				bw.write(c);
				bw.flush();
			}
			System.out.println("test");
			String str = new String("sejong 세종");
			bw.write(str);
			bw.newLine();
			bw.write(str,0,str.length()-1);
			bw.newLine();
			bw.flush();
			System.out.println("bw test end");
			
			ps.println("PrintStream 출력연습");
			ps.print('a');
			ps.print(123);
			ps.print(123.45);
			ps.print(true);
			ps.println("PrintStream" + 123 + "test end");
		}catch(IOException e) {
			System.err.println(e);
		}finally {
			try {
				br.close();
				bw.close();
				ps.close();
			}catch(IOException e2) {
				System.out.println(e2);
			}
		}
	}

}
