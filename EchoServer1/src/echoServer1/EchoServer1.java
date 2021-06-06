package echoServer1;

import java.io.*;
import java.net.*;

public class EchoServer1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		
		Socket clientSocket = null;
		PrintWriter socketOut;
		BufferedReader socketln;
		String strData;
		try {
			serverSocket = new ServerSocket(1234);
		}catch(IOException e) {
			System.out.println("Server Socket ���� ���� ���");
		}
		System.out.println("������ 1234�� Port���� ������ ��ٸ��ϴ�.");
		
		try {
			clientSocket = serverSocket.accept();
		}catch(IOException e) {
			System.out.println("���� �����Դϴ�."); System.exit(1);
		}
		
		try {
			System.out.println("Client: " + clientSocket.toString() + " \n���� �����Ͽ����ϴ�.");
			
			socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
			socketln = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			while((strData = socketln.readLine()) != null) {
				socketOut.println("EchoServer : " + strData);
				System.out.println("Client:" + strData);
			}
			
			socketOut.close();
			socketln.close();
			clientSocket.close();
			serverSocket.close();
		}catch(IOException e) {
			System.out.println("������ ������ϴ�.");
		}
	}

}
