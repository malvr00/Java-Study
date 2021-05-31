package thread3;

public class Thread3 {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		RunnableThread1 td1 = new RunnableThread1(5, "Thread1");
		RunnableThread1 td2 = new RunnableThread1(5, "Thread2");
	  
	  // Runnable 상속 받은 경우 Runnable은 run() Method만 정의 되어있어서
	  // Thread 객체를 생성할 때 생성자에 대입하여 사용
	  // Thread 이름 정의.
		Thread t1 = new Thread(td1);
		Thread t2 = new Thread(td2, "ThreadName2");
		
		t1.start();
		t2.start();
		
		System.out.println("main Thread Name = " + Thread.currentThread().getName());
		System.out.println("t1 Thread Name = " + t1.getName());
		System.out.println("t2 Thread Name = " + t2.getName());
		Thread.sleep(100);
		System.out.println("Thread3 Main() 종료 ");
	}

}

class RunnableThread1 implements Runnable{
	private String strName;	// Runnable에서만 쓰는 Thread 이름.
	private int lastCount;
	
	RunnableThread1(int n, String str){
		strName = str;
		lastCount = n;
	}
	public void run() {
		for(int i=0; i <= lastCount; i++) {
			System.out.println(strName + " = " + i);
			try {
				if(strName.equals("Thread1")) {
					Thread.sleep(2);
				}else {
					Thread.sleep(1);
				}
			}catch(InterruptedException e) {}
		}
	}
}