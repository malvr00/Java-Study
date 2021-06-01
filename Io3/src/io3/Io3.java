package io3;

import java.io.*;

public class Io3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int inData, cnt=0;
		char ch;
		// Character 단위 Stream에 Buffer 기능 추가.
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			/*while(true) {
				inData = br.read();
				if(inData == -1) {
					break;
				}
				cnt++;
				ch = (char)inData;
				System.out.println(ch);
				br.skip(2); // ex) 12345 -> 출력 = 1, 4
			} */
			while(true) {
				String s = br.readLine();	// 행 단위 출력
				if(s == null)
					break;
				cnt++;
				System.out.println(s);
			}
			br.close();
		}catch(IOException e) {
			System.out.println(e);
		}
		System.out.println("입력된 문자수/행수 = " + cnt);
	}

}
