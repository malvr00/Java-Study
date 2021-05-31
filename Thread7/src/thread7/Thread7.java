package thread7;

public class Thread7 {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		TestClass ta = new TestClass("test1");
		TestClass tb = new TestClass("test2");
	// main Thread Name
		System.out.println("main Thread Name = " + Thread.currentThread().getName());
		
		ta.threadStart();
		tb.threadStart();
		Thread.sleep(10);
		System.out.println("test1 종료");
		ta.threadStop();
		Thread.sleep(20);
		System.out.println("test2 종료");
		tb.threadStop();
		System.out.println("main Thread end");
	}


}

class TestClass{
	String strName;
	TestThread1 sj;
	TestClass(){}
	TestClass(String str){
		sj = new TestThread1(str);
		strName = str;
	}
	public void threadStart() {
		Thread t= new Thread(sj);
		t.start();
		System.out.println("threadStart Thread Name = " + Thread.currentThread().getName());
	}
	public void threadStop() {
		sj.stopThread();
	}
}
class TestThread1 implements Runnable{
	private boolean bStop = false;	// Thread Loop 종료 key
	String strName;
	int cnt = 0;
	TestThread1(String str){
		strName = str;
	}
	public void run() {
		System.out.println("run Thread Name = " + Thread.currentThread().getName());
		while(bStop!=true) {
			cnt++;
			System.out.println(strName + "의 cnt = " + cnt + "\n");
			try {
				Thread.sleep(3);
			}catch(InterruptedException e) {}
		}
	}
	public void stopThread() {
		bStop = true;
	}
}