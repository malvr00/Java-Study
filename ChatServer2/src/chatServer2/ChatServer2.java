package chatServer2;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class ChatServer2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		ChatThread chatTrd;
		boolean bool = true;
		// �����ڸ� ������ Vector ����
		Vector<ChatThread> vClient = new Vector<>();
		
		try {
			serverSocket = new ServerSocket(1234);
		}catch(IOException e) {
			System.out.println("Server Socket �������� �߻�.");
			System.exit(1);
		}
		System.out.println("Chatting Server�� 1234 Port���� ������ ��ٸ��ϴ�.");
		
		try {
			while(bool) {
				clientSocket = serverSocket.accept();
				chatTrd = new ChatThread(clientSocket, vClient);
				chatTrd.start();
				vClient.addElement(chatTrd);
			}
			serverSocket.close();
		}catch(IOException e) {
			System.err.println("���� �����Դϴ�.");
			System.exit(1);
		}
	}

}

class ChatThread extends Thread{
	Socket clientSocket = null;
	PrintWriter socketOut;
	BufferedReader socketIn;
	String strInput, strName = "NoName";
	Vector<ChatThread> vClient;
	public ChatThread() {}
	public ChatThread(Socket socket, Vector vClient) {
		clientSocket = socket;
		this.vClient = vClient;
	}
	// ���Ӳ��� ���� Vector���� ���� 
	public void removeClient() throws IOException{
		vClient.removeElement(this);
		broadcast("[" + strName + "] ���� �����ϼ̽��ϴ�.");
	}
	// �������� User ��� ����
	public void sendUserList()throws IOException{
		socketOut.println("< ���� ������ " + vClient.size() + "�� ��� >");
		for(int i=0; i<vClient.size(); i++) {
			ChatThread trd = ((ChatThread)vClient.elementAt(i));
			socketOut.println(trd.strName);
		}
	}
	// Thread ����
	public void run() {
		try {
			System.out.println("Client:"+clientSocket.toString() + "\n���� �����Ͽ����ϴ�.");
			socketOut = new PrintWriter(clientSocket.getOutputStream(),true);
			socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			socketOut.println("ChatServer");		// Server Ȯ�� Message ����
			strInput = socketIn.readLine();			// Client Ȯ�� Message ����
			if(strInput.equals("EchoClient")) {		// ������ Client�� ������ ó��
				socketOut.println("<����Ű> : /h(����), /u(�����ڸ��), /r ��ȭ��(��ȭ����)");
				socketOut.println("��ȭ���� �Է��ϼ���!");
				strName = socketIn.readLine();		// ��ȭ�� �б�
				broadcast("[" + strName + "]���� �����Ͽ����ϴ�.");
				
				while((strInput = socketIn.readLine())!= null) {
					if(strInput.equals("/h"))
						socketOut.println("<����Ű> : /h(����), /u(�����ڸ��), /r ��ȭ��(��ȭ����)");
					else if(strInput.equals("/u"))
						sendUserList();
					else if(strInput.regionMatches(0, "/r", 0, 2)) {	// �տ� �� ���� �����Ͽ� ��
						String new_name = strInput.substring(2).trim();	// "/r" ������ User Name ����
																		// ��ȭ�� ���� �˸�
						broadcast("������ " + strName + "���� ��ȭ���� [" + new_name + "](��)�� ����Ǿ����ϴ�.");
						strName = new_name;
					}else {
						broadcast("[" + strName + "]" + strInput);
					}
				}
			}else {
				socketOut.println("�߸��� Client�Դϴ�.");
			}
		}catch(IOException e) {
			try {
				removeClient();
			}catch(IOException e1) {
				System.out.println(" " + strName + " �� ������ ������ϴ�.");
			}
		}
	}
	// Message ����
	public void broadcast(String msg) throws IOException{
		for(int i = 0; i<vClient.size(); i++) {
			ChatThread trd = ((ChatThread)vClient.elementAt(i));
			trd.socketOut.println(msg);		// ���޵� msg�� Vector�� ��ϵ� ��ο��� ����
		}
		System.out.println(msg);
	}
}