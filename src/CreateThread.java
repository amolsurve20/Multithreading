import java.util.*;

class Runner1 extends Thread
{
	public void run()
	{
		for(int i=0;i<10;i++)
		{
			System.out.println("Hello"+ i);
			try
			{
				Thread.sleep(1);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}

class Runner2 implements Runnable
{
	public void run()
	{
		for(int i=0;i<10;i++)
		{
			System.out.println("Hello"+ i);
			try
			{
				Thread.sleep(1);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}

public class CreateThread 
{
	public static void main(String agrs[])
	{
		/*
		Runner1 obj=new Runner1();
		obj.start();
		
		Runner1 obj1=new Runner1();
		obj1.start();
		*/
		/*
		Thread t1 = new Thread(new Runner2());
		Thread t2 = new Thread(new Runner2());
		
		t1.start();
		t2.start();
		*/
		
		Thread t3 = new Thread(new Runnable()
		{
			public void run()
			{
				for(int i=0;i<10;i++)
				{
					System.out.println("Hello"+ i);
					try
					{
						Thread.sleep(100);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		t3.start();
	}

}
