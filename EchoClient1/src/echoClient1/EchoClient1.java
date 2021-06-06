package echoClient1;

import java.io.*;
import java.net.*;

public class EchoClient1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket echoSocket = null; 			// null�� �ʱ�ȭ �ؾ���
		PrintStream socketOut = null;
		BufferedReader socketIn = null;
		BufferedReader stdIn;
		String strInput, strOutput;
		
		try {
			echoSocket = new Socket("localhost", 1234);
		// Socket�� �Է¿�, ��¿� Stream ����
			socketOut = new PrintStream(echoSocket.getOutputStream(), true);
			System.out.println("echo Client SOcket = " + echoSocket);
			socketIn = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		}catch(UnknownHostException e) {
			System.out.println("Server�� �����ϴ�."); System.exit(1);
		}catch(IOException e) {
			System.err.println("����� �����Դϴ�."); System.exit(1);
		}
		
		try {
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			while((strInput = stdIn.readLine())!=null){		// KeyBoard�� �����
				socketOut.println(strInput);				// Server Socket�� ����
				strOutput = socketIn.readLine();			// Server���� ������ ���� �޾ƿ�
				System.out.println(strOutput);				// Server���� ������ ���� ��� 
			}
			socketOut.close();
			socketIn.close();
			stdIn.close();
			echoSocket.close();
		}catch(Exception e) {
			System.out.println("������ ������ϴ�.");
		}
	}

}
