package thread4;

public class Thread4 {
	// Thread 공유자원
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RunnableThread4 td1 = new RunnableThread4();
		RunnableThread4 td2 = new RunnableThread4();
		
		Thread t1 = new Thread(td1, "tName1");
		Thread t2 = new Thread(td1, "tName2");
		//Thread t2 = new Thread(td2, "tName2");
		t1.start();
		t2.start();
	}

}

class RunnableThread4 implements Runnable{
	private String str1 = "str1:";
	static String str2 = "str2:";	// static 변수는 공유
	
	RunnableThread4(){}
	public void run() {
		// Thread.currentThread = 현재 실행 중인 Thread 반환
		String tName = Thread.currentThread().getName();
		for(int i = 1; i<=3; i++) {
			if(tName.equals("tName1")) {
				str1+="A";
				str2+="A";
			}else {
				str1+="B";
				str2+="B";
			}
			System.out.println(tName+ " : " + str1 + "\t" + str2);
		}
	}
}