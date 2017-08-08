import java.util.*;
/*
 *Each class comes with the intrinsic lock. Here, the object of the multipleLocks class comes with the lock which at 
 *a time, can be accessed by the single thread. So even if the code inside the function blocks of stage 1 and stage 2 
 *is independent, the thread once acquires the lock for running the stage 1 code, finishes the execeution
 *and then only other thread can access the lock and run the stage 1 and stage 2 methods. In order to make it work in a way 
 *such that one thread is running stage1 and other stage2 simultaneously, we use the concept of multiple locks.
 * 
 * 
 */
public class MultipleLocks 
{
	private static Random random = new Random();
	
	static List<Integer>list1 = new ArrayList<Integer>();
	static List<Integer>list2 = new ArrayList<Integer>();
	
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	
	//Instead of using the synchronized keywords, we can have the synchronized code blocks inside the method ,synchronized on
	//each lock which are shown below
	
	public static void stage1()
	{
		synchronized(lock1)
			{
				try
				{
					Thread.sleep(1);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				
				list1.add(random.nextInt(100));
			
			}
	}
	
	public static void stage2()
	{
		synchronized(lock2)
			{
				try
				{
					Thread.sleep(1);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				
				list2.add(random.nextInt(100));
				
			}
	}
			
	
	/*
	
	
	public static synchronized void stage1()
	{
		try
		{
			Thread.sleep(1);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		list1.add(random.nextInt(100));
		
	}
	
	public static synchronized void stage2()
	{
		try
		{
			Thread.sleep(1);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		list2.add(random.nextInt(100));
		
	}
	*/
	
	public static void process()
	{
		for(int i=0; i<1000; i++)
		{
			stage1();
			stage2();
		}
	}
	

	public static void main(String rags[])
	{
		System.out.println("Starting.....");
		
		long start = System.currentTimeMillis();

		
		Thread t1 = new Thread(new Runnable(){

			@Override
			public void run() {
				process();
				
			}
			
		});
		
		t1.start();
		
		Thread t2 = new Thread(new Runnable(){

			@Override
			public void run() {
				process();
				
			}
			
		});
		
		t2.start();
		
		try
		{
			t1.join();
			t2.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		System.out.println("Time taken "+ (end-start));
		System.out.println("List 1 size "+list1.size());
		System.out.println("List 2 size "+list2.size());
		
		
	}
}

