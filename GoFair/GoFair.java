/**
 * Sean Calarco
 * Program demonstrates that every process must use the resource before any process may use the resource again.
 * This is accomplished through the use of Semaphores and Arrays.
 */
import java.util.concurrent.Semaphore;
import java.lang.*;

public class GoFair {
  
 final static int N = 20;
 static int[] flag = new int[N]; //Tracks how many times a given process has used a resource
 static Semaphore mutex = new Semaphore(1);
 static Semaphore[] S = new Semaphore[N];

 public static void goFair(int id) throws InterruptedException 
 {
   S[id].acquire(); //aquire Semaphore for Process id until all other processes have had a turn
   try 
   {
     mutex.acquire(); //aquire mutual exclusion semaphore as resource can only be used by one process at a time
   } 
   catch (InterruptedException e) 
   {
     e.printStackTrace();
   }

   useResource(id);
   flag[id]++; //Process id has used resource once more
  
  //Array check
  for(int i=0;i<N;i++) System.out.print(flag[i] + " ");

  boolean fair;
  fair = isFair(id);
  if (fair) 
  {
    for (int i = 0; i < N; i++) 
    {
      S[i].release(); //If all processes have used resource same number of times, then release all.
    }
  }
  mutex.release(); //Allow next process to use resource
 }

 public static void useResource(int id) 
 {
   System.out.println("\n" + id + " now using resource"); //Just print text for which id is using resource
 }

 //Checks fairness
 public static boolean isFair(int id) 
 {
   boolean fair = true;
   for (int i = 0; i < N; i++) 
   {
     if (flag[i] < flag[id]) //It is not fair if any other process has used the resource less times than the current
       fair = false;
   }
   return fair;
 }

 public static void main(String[] args) 
 {
   Process[] process = new Process[N];
   //Initialize all Semaphores, flag array, and start all processes
   for (int i = 0; i < N; i++) 
   {
     S[i] = new Semaphore(1);
     flag[i] = 0;
     process[i] = new Process(i);
     process[i].start();
   }  
 }
}
