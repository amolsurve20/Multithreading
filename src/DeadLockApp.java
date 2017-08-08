import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account
{
	private int balance;
	
	public Account(int balance)
	{
		this.balance = balance;
	}
	
	public void transfer(Account acc2, int amount)
	{
		if(amount > this.balance)
			return;
		
		this.withdraw(amount);
		acc2.deposit(amount);
	}
	
	public void deposit(int deposit)
	{
		balance = balance + deposit;
	}
	
	public void withdraw(int withdrawal)
	{
		balance = balance - withdrawal;
	}
	
	public int getBalance()
	{
		return balance;
	}
}

class AccountRunner
{
	Account acc1 = new Account(20000);
	Account acc2 = new Account(20000);
	
	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();
	
	public void acquireLocks(Lock lock1, Lock lock2)throws InterruptedException
	{
		while(true)
		{
			boolean gotFirstLock = false;
			boolean gotSecondLock = false;
			
			try{
			gotFirstLock = lock1.tryLock();
			gotSecondLock = lock2.tryLock();
			}
			finally
				{
					if(gotFirstLock && gotSecondLock)
						{
							return;
						}
					if(gotFirstLock)
						{
							lock1.unlock();
						}
					if(gotSecondLock)
						
						{
							lock2.unlock();
						}
	
				}
			
			Thread.sleep(10);
			
		}

	}
	
	public void firstThread()throws InterruptedException
	{
		Random random = new Random();
		
		for(int i=0 ; i<10000; i++)
		{
			acquireLocks(lock1, lock2);
			
			try{
				acc1.transfer(acc2, random.nextInt(i));
				
			}
			finally
			{
				lock1.unlock();
				lock2.unlock();
			}
		}
	}
	
	public void secondThread()throws InterruptedException
	{
		Random random = new Random();
		
		for(int i=0 ; i<10000; i++)
		{
			acquireLocks(lock2, lock1);
			
			try{
				acc2.transfer(acc1, random.nextInt(i));
				
			}
			finally
			{
				lock2.unlock();
				lock1.unlock();
			}
		}
		
	}
	
	public void finish()
	{
		System.out.println("Account 1 balance is "+ acc1.getBalance());
		System.out.println("Account 2 balance is "+ acc2.getBalance());
		System.out.println("Total balance is "+ (acc1.getBalance()+acc2.getBalance()));
	}
}

public class DeadLockApp 
{
	public static void main(String args[]) throws InterruptedException
	{
		AccountRunner processor = new AccountRunner();
		
		Thread t1 = new Thread(new Runnable()
		{
			
			public void run()
			{
				try 
				{
					processor.firstThread();
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
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
					processor.secondThread();
				} 
				catch (InterruptedException e) 
				{
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
