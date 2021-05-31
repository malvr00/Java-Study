package thread1;

public class Thread1 extends Thread{
	private String strName;
	private int lastCount;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread1 td1 = new Thread1(5, "myThread");
		Thread1 td2 = new Thread1();
		td1.start();		// td1 Thread run ȣ��
		td2.start();		// td2 Thread run ȣ��
		
		System.out.println("td1 Thread Name = " + td1.getName());
		System.out.println("td2 Thread Name = " + td2.getName());
		
		System.out.println("Thread1 main() ��");
	}
	Thread1(){
		strName = getName(); // �̸� ���� ���ؼ� Thread-0...99 ������ �̸� �ڵ��ο�.
		lastCount = 10;
	}
	Thread1(int n, String str){
		super(str);			// ������ Thread Name ����
		strName = str;
		lastCount = n;
	}
	public void run() {
		for(int i = 1; i<=lastCount; i++) {
			System.out.println(strName + " = " + i);
			try {
				if(strName.equals("myThread")) {
					sleep(2);
				}else {
					sleep(1);
				}
			}catch(InterruptedException e) {}
		}
	}
}
