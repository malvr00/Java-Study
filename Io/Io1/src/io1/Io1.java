package io1;

import java.io.*;

public class Io1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InputStream is = System.in;		// Byte단위 Stream System.in(Keybord)
		OutputStream os = System.out;	// Byte단위 Stream System.out(Monitor)
		int inData;
		try {
			while((inData = is.read())!= -1) {	// Byte 단위 처리라 한글 안됌.
				os.write(inData);	// 입출력의 기본형은 int char로 Casting해서 사용.
				os.write('\n');
			}
			is.close();
			os.close();
		}catch(IOException e) {
			System.err.println(e);
		}
	}

}
