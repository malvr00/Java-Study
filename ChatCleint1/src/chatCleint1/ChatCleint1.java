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
		ReceiveThread rec;			// Receive Message 처리할 Thread
		
		try {
			echoSocket = new Socket("localhost", 1234);
			socketOut = new PrintStream(echoSocket.getOutputStream(),true);
			socketIn = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			
			strMsg = socketIn.readLine();			// 접속할 Server확인 Message Receive
			if(strMsg.equals("ChatServer")) {		// 접속할 Server가 맞는지 확인
				socketOut.println("EchoClient");	// Client 확인 Message 전송
				rec = new ReceiveThread(socketIn);
				rec.start();
				
				stdIn = new BufferedReader(new InputStreamReader(System.in));
				while((strUser = stdIn.readLine())!=null) {
					socketOut.println(strUser);
				}
				stdIn.close();
			}else {
				System.out.println("잘못된 Server입니다.");
			}
			socketOut.close();
			socketIn.close();
			echoSocket.close();
		}catch(UnknownHostException e) {
			System.err.println("Server가 없습니다.");
			System.exit(1);
		}catch(IOException e) {
			System.err.println("입출력 Error");
			System.exit(1);
		}catch(Exception e) {
			System.err.println("연결이 끊겼습니다.");
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
		System.out.println("Server에 접속됨");
		
		try {
			while((strSocket = socketIn.readLine())!=null) {
				System.out.println(strSocket);
			}
		}catch(Exception e) {
			System.err.println("연결이 끊겼습니다.");
		}
	}
	
}