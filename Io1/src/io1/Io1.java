package io1;

import java.io.*;

public class Io1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InputStream is = System.in;		// Byte���� Stream System.in(Keybord)
		OutputStream os = System.out;	// Byte���� Stream System.out(Monitor)
		int inData;
		try {
			while((inData = is.read())!= -1) {	// Byte ���� ó���� �ѱ� �ȉ�.
				os.write(inData);	// ������� �⺻���� int char�� Casting�ؼ� ���.
				os.write('\n');
			}
			is.close();
			os.close();
		}catch(IOException e) {
			System.err.println(e);
		}
	}

}
