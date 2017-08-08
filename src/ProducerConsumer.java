import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer 
{
	private static BlockingQueue<Integer>queue = new ArrayBlockingQueue<Integer>(10);
	
	static void producer() throws InterruptedException
	{
		Random r = new Random(100);
		
		while(true)
		{
			queue.put(r.nextInt(100));
		}
		
	}
	
	static void consumer() throws InterruptedException
	{
		Random r = new Random(100);
		
		while(true)
		{
			Thread.sleep(100);
			if(r.nextInt(10)==0)
			{
				Integer value = queue.take();
				System.out.println("Value removed from the queue is "+ value);
				System.out.println("Size of the queue is "+ queue.size());
			}
		}
		
		
	}
	
	public static void main(String args[]) throws InterruptedException 
	{
		Thread t1 = new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					producer();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				
			}
			
		});
		
		Thread t2 = new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					consumer();
				}
				catch (InterruptedException e)
				{
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
