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
		// 접속자를 저장할 Vector 생성
		Vector<ChatThread> vClient = new Vector<>();
		
		try {
			serverSocket = new ServerSocket(1234);
		}catch(IOException e) {
			System.out.println("Server Socket 생성오류 발생.");
			System.exit(1);
		}
		System.out.println("Chatting Server가 1234 Port에서 접속을 기다립니다.");
		
		try {
			while(bool) {
				clientSocket = serverSocket.accept();
				chatTrd = new ChatThread(clientSocket, vClient);
				chatTrd.start();
				vClient.addElement(chatTrd);
			}
			serverSocket.close();
		}catch(IOException e) {
			System.err.println("접속 실패입니다.");
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
	// 접속끊은 유저 Vector에서 제거 
	public void removeClient() throws IOException{
		vClient.removeElement(this);
		broadcast("[" + strName + "] 님이 퇴장하셨습니다.");
	}
	// 접속중인 User 목록 전송
	public void sendUserList()throws IOException{
		socketOut.println("< 현재 접속자 " + vClient.size() + "명 명단 >");
		for(int i=0; i<vClient.size(); i++) {
			ChatThread trd = ((ChatThread)vClient.elementAt(i));
			socketOut.println(trd.strName);
		}
	}
	// Thread 실행
	public void run() {
		try {
			System.out.println("Client:"+clientSocket.toString() + "\n에서 접속하였습니다.");
			socketOut = new PrintWriter(clientSocket.getOutputStream(),true);
			socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			socketOut.println("ChatServer");		// Server 확인 Message 전송
			strInput = socketIn.readLine();			// Client 확인 Message 전송
			if(strInput.equals("EchoClient")) {		// 접속한 Client가 맞으면 처리
				socketOut.println("<단축키> : /h(도움말), /u(접속자목록), /r 대화명(대화명변경)");
				socketOut.println("대화명을 입력하세요!");
				strName = socketIn.readLine();		// 대화명 읽기
				broadcast("[" + strName + "]님이 입장하였습니다.");
				
				while((strInput = socketIn.readLine())!= null) {
					if(strInput.equals("/h"))
						socketOut.println("<단축키> : /h(도움말), /u(접속자목록), /r 대화명(대화명변경)");
					else if(strInput.equals("/u"))
						sendUserList();
					else if(strInput.regionMatches(0, "/r", 0, 2)) {	// 앞에 두 글자 추출하여 비교
						String new_name = strInput.substring(2).trim();	// "/r" 제외한 User Name 대입
																		// 대화명 변경 알림
						broadcast("접속자 " + strName + "님의 대화명이 [" + new_name + "](으)로 변경되었습니다.");
						strName = new_name;
					}else {
						broadcast("[" + strName + "]" + strInput);
					}
				}
			}else {
				socketOut.println("잘못된 Client입니다.");
			}
		}catch(IOException e) {
			try {
				removeClient();
			}catch(IOException e1) {
				System.out.println(" " + strName + " 의 접속이 끊겼습니다.");
			}
		}
	}
	// Message 전송
	public void broadcast(String msg) throws IOException{
		for(int i = 0; i<vClient.size(); i++) {
			ChatThread trd = ((ChatThread)vClient.elementAt(i));
			trd.socketOut.println(msg);		// 전달된 msg를 Vector에 등록된 모두에게 전송
		}
		System.out.println(msg);
	}
}