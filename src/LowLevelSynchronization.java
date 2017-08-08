import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.*;

class Manager
{
	private LinkedList<Integer>list = new LinkedList<Integer>();
	private final int limit=10;
	private Object lock = new Object();
	Random random = new Random();
	
	public void produce()throws InterruptedException
	{
		
		while(true)
		{
			int value = random.nextInt(1000);
			
			synchronized(lock)
			{
				while(list.size()==limit)
				{
					lock.wait();
				}
				
				list.add(value);
				System.out.println("New value added to the list "+ value);
				System.out.println("Size of the list is "+ list.size());
				lock.notify();
				
			}
			
			Thread.sleep(1000);
		}

		
	}
	
	public void consume()throws InterruptedException
	{
		while(true)
		{
			synchronized(lock)
			{
				while(list.size()==0)
				{
					lock.wait();
				}
					int value = list.removeFirst();
					System.out.println("Removed value is "+value);
					System.out.println("Size of the list is "+ list.size());
					lock.notify();
				
			}
			
			Thread.sleep(2000);
		}
		
	}
}

public class LowLevelSynchronization 
{
	public static void main(String agrs[]) throws InterruptedException
	{
		Manager processor = new Manager();
		
		Thread t1 = new Thread(new Runnable(){
			
			public void run()
			{
				try {
					processor.produce();
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
					processor.consume();
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

	}
}
