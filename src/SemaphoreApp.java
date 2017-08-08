import java.util.concurrent.*;
 
class Connection
{
	private static Connection instance;
	private int numberOfConnections = 0;
	private Semaphore semaphore = new Semaphore(10, true);
	
	private Connection()
	{
		
	}
	
	protected static Connection getInstance()
	{
		if(instance==null)
		{
			instance = new Connection();
		}
		
		return instance;
	}
	
	public void connect()
	{
		try
		{
			semaphore.acquire();
		}
		catch(InterruptedException e)
		{
			
		}
		
		try
		{
			doConnect();
		}
		catch(InterruptedException e)
		{
			
		}
		
		finally{
			semaphore.release();
		}
		
		
	}
	
	public void doConnect() throws InterruptedException
	{
		synchronized(this)
		{
			numberOfConnections++;
			
		}
		
		System.out.println("Getting the connection "+numberOfConnections);
		Thread.sleep(5000);
		System.out.println("releasing the connection");
		
		synchronized(this)
		{
			numberOfConnections--;
			
		}
		
	}
	
}
 
public class SemaphoreApp 
{
	public static void main(String args[]) throws InterruptedException
	{
		ExecutorService executor  = Executors.newCachedThreadPool();
		
		for(int i=0; i<200; i++)
		{
			executor.submit(new Runnable(){
				public void run()
				{
					Connection.getInstance().connect();
				}
			});
		}
		
		executor.shutdown();
		
		executor.awaitTermination(1, TimeUnit.DAYS);
		
	}
	

}
