

import java.util.*;

class Processor extends Thread
{
	/*
	 Volatile is used to prevent threads caching variables when they are not changed from within that thread.
	 If the variable needs to be changed from other thread, then the volatile is used.
	 */
	private volatile boolean running=true;
	
	public void run()
	{
		while(running)
		{
			System.out.println("Hello");
			
			try
			{
				Thread.sleep(1000
						);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void shutDown()
	{
		running=false;
	}
}
public class VolatileExample 
{
	public static void main(String args[])
	{
		Processor obj = new Processor();
		obj.start();
		System.out.println("Do you want to stop the thread? Hit return");
		Scanner sc=new Scanner(System.in);
		sc.nextLine();
		
		obj.shutDown();
		
		System.out.println("It Stopped");
		

		
	}

}
