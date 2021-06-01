package io2;

import java.io.*;

public class Io2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	  // Byte단위 Stream 객체를 Character 단위 Stream에 연결. (한글처리 가능)
		InputStreamReader isr = new InputStreamReader(System.in);
		OutputStreamWriter osw = new OutputStreamWriter(System.out);
		int inData;
		try {
			while((inData = isr.read())!=-1) {
				osw.write(inData);
				osw.write('\n');
				osw.flush();		// OutputStreamWrite는 Buffer를 사용하므로 비워줘야함.
			}
		}catch(IOException e) {
			System.err.println(e);
		}
	}

}
