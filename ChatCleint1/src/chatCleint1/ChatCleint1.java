package chatCleint1;

import java.io.*;
import java.net.*;

public class ChatCleint1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket echoSocket = null;
		PrintStream socketOut = null;
		BufferedReader socketIn = null;
		BufferedReader stdIn;
		String strUser, strMsg;
		ReceiveThread rec;			// Receive Message ó���� Thread
		
		try {
			echoSocket = new Socket("localhost", 1234);
			socketOut = new PrintStream(echoSocket.getOutputStream(),true);
			socketIn = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			
			strMsg = socketIn.readLine();			// ������ ServerȮ�� Message Receive
			if(strMsg.equals("ChatServer")) {		// ������ Server�� �´��� Ȯ��
				socketOut.println("EchoClient");	// Client Ȯ�� Message ����
				rec = new ReceiveThread(socketIn);
				rec.start();
				
				stdIn = new BufferedReader(new InputStreamReader(System.in));
				while((strUser = stdIn.readLine())!=null) {
					socketOut.println(strUser);
				}
				stdIn.close();
			}else {
				System.out.println("�߸��� Server�Դϴ�.");
			}
			socketOut.close();
			socketIn.close();
			echoSocket.close();
		}catch(UnknownHostException e) {
			System.err.println("Server�� �����ϴ�.");
			System.exit(1);
		}catch(IOException e) {
			System.err.println("����� Error");
			System.exit(1);
		}catch(Exception e) {
			System.err.println("������ ������ϴ�.");
			System.exit(1);
		}
	}

}

// ReceiveThread class
class ReceiveThread extends Thread{
	BufferedReader socketIn = null;
	String strSocket;
	public ReceiveThread() {}
	public ReceiveThread(BufferedReader socketIn) {
		this.socketIn = socketIn;
	}
	public void run() {
		System.out.println("Server�� ���ӵ�");
		
		try {
			while((strSocket = socketIn.readLine())!=null) {
				System.out.println(strSocket);
			}
		}catch(Exception e) {
			System.err.println("������ ������ϴ�.");
		}
	}
	
}