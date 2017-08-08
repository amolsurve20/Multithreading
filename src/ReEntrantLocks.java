import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Runner
{
	
	int count=0;
	private Lock lock = new ReentrantLock();
	Condition cond = lock.newCondition();
	
	public void increment()
	{
		for(int i=0 ; i<10000; i++)
		{
			count++;
		}
	}
	
	public void firstThread()throws InterruptedException
	{
		lock.lock();
		System.out.println("Thread one is "+ "Waiting......");
		cond.await();
		System.out.println("Thread one is awake now!!!");
		
		try
		{
			increment();
		}
		
		finally
		{
			lock.unlock();
		}
		
	}
	
	public void secondThread()throws InterruptedException
	{
		Thread.sleep(1000);
		lock.lock();
		System.out.println("Acquired lock by thread 2");
		System.out.println("Press any key to return");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		System.out.println("got the return key");
		cond.signalAll();
		
		try
		{
			increment();
		}
		
		finally
		{
			lock.unlock();
		}
	}
	
	public void finish()
	{
		System.out.println("Count is "	+ ""+count);
	}
}
public class ReEntrantLocks 
{
	public static void main(String rags[]) throws InterruptedException
	{
	Runner processor = new Runner();
	
	Thread t1 = new Thread(new Runnable(){
		
		public void run()
		{
			try {
				processor.firstThread();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	});
	
	Thread t2 = new Thread(new Runnable(){
		
		public void run()
		{
			try {
				processor.secondThread();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
					
	});
	
	t1.start();
	t2.start();
	
	t1.join();
	t2.join();
	
	processor.finish();

}
}
