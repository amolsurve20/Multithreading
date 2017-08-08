import java.util.Scanner;

class ProcessorNew
{
	public void produce() throws InterruptedException
	{
		synchronized(this)
		{
			System.out.println("Producer thread running...");
			wait();
			System.out.println("Producer thread Resumed....");
		}
	}
	
	public void consume() throws InterruptedException
	{
		Scanner sc = new Scanner(System.in);
		synchronized(this)
		{
			Thread.sleep(3000);
			System.out.println("Consumer Thread running");
			System.out.println("Press any key to give up the thread...");
			sc.nextLine();
			notify();
			Thread.sleep(5000);
			System.out.println("Exiting the consumer block");
		}
	}
	
	
}

public class WaitandNotify 
{
	public static void main(String args[]) throws InterruptedException
	{
		ProcessorNew processor = new ProcessorNew();
		
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
