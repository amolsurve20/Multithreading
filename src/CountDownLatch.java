import java.util.concurrent.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;;
class Processor2 implements Runnable
{
	private CountDownLatch latch;
	
	public Processor2(CountDownLatch latch)
	{
		this.latch = latch;
	}
	
	@Override
	public void run() 
	{
		
		try
		{
			Thread.sleep(1000);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		latch.countDown();
	}

	
}
public class CountDownLatch 
{
	public static void main(String args[])
	{
		CountDownLatch latch = new CountDownLatch(3);
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		for(int i=0;i<3;i++)
		{
			executor.submit(new Processor2(latch));
		}
		
		try
		{
			latch.await();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Completed");
		
	}

}
