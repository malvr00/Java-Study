package io1;

import java.io.*;

public class Io1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InputStream is = System.in;
		OutputStream os = System.out;
		int inData;
		try {
			while((inData = is.read())!= -1) {
				os.write(inData);
				os.write('\n');
			}
			is.close();
			os.close();
		}catch(IOException e) {
			System.err.println(e);
		}
	}

}
