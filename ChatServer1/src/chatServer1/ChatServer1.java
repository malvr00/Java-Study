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
		// Client User 접속자 Vector로 저장
		Vector v = new Vector();
		
		try {
			serverSocket = new ServerSocket(port);
		}catch(IOException e) {
			System.out.println("Server Socket 생성오류 발생");
			System.exit(1);
		}
		System.out.println("서버 가 " + port + " 번 Port에서 연결을 기다립니다.");
		
		try {
			while(listening) {
				clientSocketet = serverSocket.accept();
				// 접속한 User Vector에 저장 후 Client Thread 실행
				trd = new ChatThread(clientSocketet, v);
				trd.start();
				v.addElement(trd);
			}
			serverSocket.close();
		}catch(IOException e) {
			System.out.println("연결 실패입니다.");
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
	// 접속중인 모든 User에게 Message 전송
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
			System.out.println("Client : " + clientSocket.toString() + "\n에서 접속하였습니다.");
			socketOut = new PrintWriter(clientSocket.getOutputStream(),true);						// 출력
			socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	// 입력
			
			socketOut.println("Chatting Server와 연결되었습니다.");
			socketOut.println("대화명 : ");
			// User Name 저장
			strName = socketIn.readLine();
			broadcast("[" + strName + "]님이 입장하셨습니다.");
			// User Chatting 내용 전송
			while((strInput = socketIn.readLine()) != null) {
				broadcast("[" + strName + "] : " + strInput);
			}
			socketOut.close();
			socketIn.close();
			clientSocket.close();
		}catch(IOException e) {
			System.out.println("Client\n" + clientSocket + " \n의 접속이 끊겼습니다.");
		}
		
	}
}