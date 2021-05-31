package thread2;

public class Thread2 {

	public static void main(String[] args) throws Exception{	// sleep ����ϱ� ���� �߰�
		// TODO Auto-generated method stub
		
		Thread21 td1 = new Thread21(5, "Thread1");		// ù ��° Thread ����
		Thread21 td2 = new Thread21(5, "Thread2");		// �� ��° Thread ����
			
		td1.start();
		td2.start();
		Thread.sleep(100);
		System.out.println("Thread2 main() ��");
	}
	
}
class Thread21 extends Thread{
	private String strName;
	private int lastCount;
	
	public Thread21(){}
	public Thread21(int n, String str){
		strName = str;
		lastCount = n;
	}
	public void run() {
		for(int i = 1; i<=lastCount; i++) {
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
