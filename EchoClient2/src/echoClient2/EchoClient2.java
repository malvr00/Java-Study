package echoClient2;

import java.io.*;
import java.net.*;
						/* Socket Client Receive �κ� Thread�� ���� */
public class EchoClient2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket echoSocket = null;
		PrintStream socketOut = null;
		BufferedReader socketln = null;
		BufferedReader stdln = null;
		String strUser;
		ReceiveThread1 rec;
		
		try {
			echoSocket = new Socket("localhost", 1234);
			socketOut = new PrintStream(echoSocket.getOutputStream(), true);
			socketln = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			
			rec = new ReceiveThread1(socketln);		// �Է�Stream�� �μ��� Thread ��ü ����
			rec.start();							// ReceiveThread Start
		}catch(UnknownHostException e){
			System.out.println("Server�� �����ϴ�."); System.exit(1);
		}catch(IOException e) {
			System.err.println("����¿��� �Դϴ�."); System.exit(1);
		}
		try {
			stdln = new BufferedReader(new InputStreamReader(System.in));
			
			while((strUser = stdln.readLine()) != null) {
				socketOut.println(strUser);
			}
			
			socketOut.close();
			socketln.close();
			stdln.close();
			echoSocket.close();
		}catch(Exception e) {
			System.out.println("������ ������ϴ�.");
		}
	}

}

// Receive �� �ڷḦ ȭ�鿡 ��Ÿ�� Thread class
class ReceiveThread1 extends Thread{
	BufferedReader socketln = null;
	
	String strSocket;
	ReceiveThread1(){}
	ReceiveThread1(BufferedReader socketln){
		this.socketln = socketln;		// socket �Է¿� Stream ����
	}
	
	public void run() {
		System.out.println("Server�� �����߽��ϴ�.");
		try {
			while((strSocket = socketln.readLine())!=null) {	// Server���� �������� �ڷḦ �Է¹޾�
				System.out.println(strSocket);					// ȭ�鿡 ���
			}
		}catch(Exception e) {
			System.err.println("������ ������ϴ�.");
		}
	}
}