package chatServer1;

import java.io.*;
import java.net.*;
import java.util.Vector;
public class ChatServer1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		Socket clientSocketet;
		ChatThread trd;
		int port = 1234;
		boolean listening = true;
		// Client User ������ Vector�� ����
		Vector v = new Vector();
		
		try {
			serverSocket = new ServerSocket(port);
		}catch(IOException e) {
			System.out.println("Server Socket �������� �߻�");
			System.exit(1);
		}
		System.out.println("���� �� " + port + " �� Port���� ������ ��ٸ��ϴ�.");
		
		try {
			while(listening) {
				clientSocketet = serverSocket.accept();
				// ������ User Vector�� ���� �� Client Thread ����
				trd = new ChatThread(clientSocketet, v);
				trd.start();
				v.addElement(trd);
			}
			serverSocket.close();
		}catch(IOException e) {
			System.out.println("���� �����Դϴ�.");
			System.exit(1);
		}
	}

}

class ChatThread extends Thread{
	Socket clientSocket = null;
	PrintWriter socketOut;
	BufferedReader socketIn;
	String strInput, strName;
	Vector v;
	
	public ChatThread() {}
	public ChatThread(Socket socket, Vector v) {
		clientSocket = socket;
		this.v = v;
	}
	// �������� ��� User���� Message ����
	public void broadcast(String msg) throws IOException{
		for(int i = 0; i<v.size(); i++) {
			ChatThread trd = ((ChatThread)v.elementAt(i));
			if(trd.socketOut != null)
				trd.socketOut.println(msg);
		}
		System.out.println(msg);
	}
	public void run() {
		try {
			System.out.println("Client : " + clientSocket.toString() + "\n���� �����Ͽ����ϴ�.");
			socketOut = new PrintWriter(clientSocket.getOutputStream(),true);						// ���
			socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	// �Է�
			
			socketOut.println("Chatting Server�� ����Ǿ����ϴ�.");
			socketOut.println("��ȭ�� : ");
			// User Name ����
			strName = socketIn.readLine();
			broadcast("[" + strName + "]���� �����ϼ̽��ϴ�.");
			// User Chatting ���� ����
			while((strInput = socketIn.readLine()) != null) {
				broadcast("[" + strName + "] : " + strInput);
			}
			socketOut.close();
			socketIn.close();
			clientSocket.close();
		}catch(IOException e) {
			System.out.println("Client\n" + clientSocket + " \n�� ������ ������ϴ�.");
		}
		
	}
}