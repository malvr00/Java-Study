package io2;

import java.io.*;

public class Io2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	  // Byte���� Stream ��ü�� Character ���� Stream�� ����. (�ѱ�ó�� ����)
		InputStreamReader isr = new InputStreamReader(System.in);
		OutputStreamWriter osw = new OutputStreamWriter(System.out);
		int inData;
		try {
			while((inData = isr.read())!=-1) {
				osw.write(inData);
				osw.write('\n');
				osw.flush();		// OutputStreamWrite�� Buffer�� ����ϹǷ� ��������.
			}
		}catch(IOException e) {
			System.err.println(e);
		}
	}

}
