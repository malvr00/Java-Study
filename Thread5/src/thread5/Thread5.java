package thread5;

public class Thread5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RunnableThread5 td1 = new RunnableThread5();
		Thread t1 = new Thread(td1, "tName1");
		Thread t2 = new Thread(td1, "tName2");
		t1.start();
		t2.start();
	}

}

class RunnableThread5 implements Runnable{
	private String str1="str1:";
	static String str2="str2:";
	RunnableThread5(){}
			  // method ���� ���� �Ұ� (Lock)
	public /*synchronized*/ void run() {
		String tName = Thread.currentThread().getName();
		//synchronized(this) {					// synchronized block ���� ���� �Ұ� (Lock)
			for(int i = 1; i<=3; i++) {
				if(tName.equals("tName1")) {
					synchronized(this) {		//	���� ���� �κи� ���� ���� �Ұ� (Lock)
						str1 += "A";
						str2 += "A";
					}
				}
				else {
					str1 += "B";
					str2 += "B";
				}
				System.out.println(tName + " : " + str1 + "\t" + str2);
			}
		//}
	}
}
