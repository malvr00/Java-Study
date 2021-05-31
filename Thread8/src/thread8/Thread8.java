package thread8;

public class Thread8 {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		TestThread2 test1 = new TestThread2("Thread1");
		TestThread2 test2 = new TestThread2("Thread2");
		test1.start();
		test2.start();
		test2.setPriority(1);
	}

}
