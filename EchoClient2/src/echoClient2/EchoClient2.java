package echoClient2;

import java.io.*;
import java.net.*;
						/* Socket Client Receive 부분 Thread로 구현 */
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
			
			rec = new ReceiveThread1(socketln);		// 입력Stream을 인수로 Thread 객체 생성
			rec.start();							// ReceiveThread Start
		}catch(UnknownHostException e){
			System.out.println("Server가 없습니다."); System.exit(1);
		}catch(IOException e) {
			System.err.println("입출력에러 입니다."); System.exit(1);
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
			System.out.println("연결이 끊겼습니다.");
		}
	}

}

// Receive 된 자료를 화면에 나타낼 Thread class
class ReceiveThread1 extends Thread{
	BufferedReader socketln = null;
	
	String strSocket;
	ReceiveThread1(){}
	ReceiveThread1(BufferedReader socketln){
		this.socketln = socketln;		// socket 입력용 Stream 연결
	}
	
	public void run() {
		System.out.println("Server에 접속했습니다.");
		try {
			while((strSocket = socketln.readLine())!=null) {	// Server에서 보내오는 자료를 입력받아
				System.out.println(strSocket);					// 화면에 출력
			}
		}catch(Exception e) {
			System.err.println("연결이 끊겼습니다.");
		}
	}
}