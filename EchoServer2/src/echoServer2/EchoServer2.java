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
			System.out.println("Server Socket 생성 오류 발생!"); System.exit(1);
		}
		
		System.out.println("서버가 1234번 Port에서 연결을 기다립니다.");
		
		try {
			while(listening) {
				clientSocket = serverSocket.accept();		// Client 접속자 대기
				trd = new ServerThread(clientSocket);		// 접속한 User정보가지고 Thread 생성
				trd.start();								// Thread Start
			}
			serverSocket.close();
		}catch(IOException e) {
			System.err.println("연결 실패입니다."); System.exit(1);
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
			System.out.println("Client : " + clientSocket.toString() + "\n에서 접속하였습니다.");
			socketOut = new PrintWriter(clientSocket.getOutputStream(),true);						// 출력 연결
			socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	// 입력 연결
			socketOut.println("Multu Echo Server와 연결되었습니다.");
			
			while((strInput = socketIn.readLine())!=null) {
				socketOut.println("Multi Echo Server : " + strInput);		// Multi Echo Server 붙여서 Client에 전송
			}
			socketOut.close();
			socketIn.close();
			clientSocket.close();
		}catch(IOException e) {
			System.out.println("Client\n" + clientSocket + "\n의 접속이 끊겼습니다.");
		}
	}
}