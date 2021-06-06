package echoClient1;

import java.io.*;
import java.net.*;

public class EchoClient1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket echoSocket = null; 			// null로 초기화 해야함
		PrintStream socketOut = null;
		BufferedReader socketIn = null;
		BufferedReader stdIn;
		String strInput, strOutput;
		
		try {
			echoSocket = new Socket("localhost", 1234);
		// Socket에 입력용, 출력용 Stream 연결
			socketOut = new PrintStream(echoSocket.getOutputStream(), true);
			System.out.println("echo Client SOcket = " + echoSocket);
			socketIn = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		}catch(UnknownHostException e) {
			System.out.println("Server가 없습니다."); System.exit(1);
		}catch(IOException e) {
			System.err.println("입출력 에러입니다."); System.exit(1);
		}
		
		try {
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			while((strInput = stdIn.readLine())!=null){		// KeyBoard에 ㅇ비력
				socketOut.println(strInput);				// Server Socket에 전송
				strOutput = socketIn.readLine();			// Server에서 보내온 내용 받아옴
				System.out.println(strOutput);				// Server에서 보내온 내용 출력 
			}
			socketOut.close();
			socketIn.close();
			stdIn.close();
			echoSocket.close();
		}catch(Exception e) {
			System.out.println("연결이 끊겼습니다.");
		}
	}

}
