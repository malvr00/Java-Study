package echoServer2;

import java.io.*;
import java.net.*;

public class EchoServer2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		Socket clientSocket;
		ServerThread trd;
		boolean listening = true;
		
		try {
			serverSocket = new ServerSocket(1234);
		}catch(IOException e) {
			System.out.println("Server Socket ���� ���� �߻�!"); System.exit(1);
		}
		
		System.out.println("������ 1234�� Port���� ������ ��ٸ��ϴ�.");
		
		try {
			while(listening) {
				clientSocket = serverSocket.accept();		// Client ������ ���
				trd = new ServerThread(clientSocket);		// ������ User���������� Thread ����
				trd.start();								// Thread Start
			}
			serverSocket.close();
		}catch(IOException e) {
			System.err.println("���� �����Դϴ�."); System.exit(1);
		}
	}

}


class ServerThread extends Thread{
	Socket clientSocket = null;
	
	PrintWriter socketOut;
	BufferedReader socketIn;
	String strInput;
	
	public ServerThread(Socket socket) {
		this.clientSocket = socket;
	}
	public void run() {
		try {
			System.out.println("Client : " + clientSocket.toString() + "\n���� �����Ͽ����ϴ�.");
			socketOut = new PrintWriter(clientSocket.getOutputStream(),true);						// ��� ����
			socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	// �Է� ����
			socketOut.println("Multu Echo Server�� ����Ǿ����ϴ�.");
			
			while((strInput = socketIn.readLine())!=null) {
				socketOut.println("Multi Echo Server : " + strInput);		// Multi Echo Server �ٿ��� Client�� ����
			}
			socketOut.close();
			socketIn.close();
			clientSocket.close();
		}catch(IOException e) {
			System.out.println("Client\n" + clientSocket + "\n�� ������ ������ϴ�.");
		}
	}
}