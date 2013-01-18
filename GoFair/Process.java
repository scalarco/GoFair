//Process class simply initializes with an id, and runs 10 times using goFair(id) to ensure fair turns
class Process extends Thread  
{
  int id;
  public Process(int i)
  {
   id=i;
  }
  
  public void run()
  {
    int n=0;
    while(n<10)
    {
      try 
      {
        GoFair.goFair(id);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
      n++;
    }
  }
}