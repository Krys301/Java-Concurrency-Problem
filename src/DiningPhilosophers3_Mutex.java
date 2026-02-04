class DiningPhilosophers3 {
public static void main(String args[]) {
int chopSticks[];
Semaphore mutex;
Philosopher workerThread[];

// Create an array of five Semaphore Object Reference Handles
chopSticks = new int[5];
// Create five Binary Semaphore Objects and assign to the array
for (int i=0; i<5; i++)
chopSticks[i]=1;
// Create an array of five Philosopher Thread Object Reference Handles
workerThread = new Philosopher[5];

mutex = new Semaphore(1);
// Create and initiate five Philosopher Thread Objects
for (int i=0; i<5; i++) {
workerThread[i] = new Philosopher(i, chopSticks,mutex);
workerThread[i].start();
}
}
}
// The Philosopher class implements a run() method defining the behaviour of a Philosopher thread
class Philosopher extends Thread {
private int myName;
private int chopSticks[];
private Semaphore mutex;
//
// This is the constructor function which is executed when a Philosopher
// thread is first created
//
public Philosopher(int myName, int chopSticks[],Semaphore mutex) {
this.myName = myName; // 'this' distinguishes the local private variable from the
//parameter
this.chopSticks = chopSticks;
this.mutex = mutex;
}
//
// This is what each philosopher thread executes
//
	public void run() {
	boolean haveChopsticks;
	
	while (true) {
	
	System.out.println("Philosopher "+myName+" thinking.");
	
	try {
	sleep ((int)(Math.random()*20000));
	} catch (InterruptedException e) {}
	System.out.println("Philosopher "+myName+" hungry.");
	
	haveChopsticks=false;
	
	while(!haveChopsticks){
	
	mutex.acquire();
	
	if((chopSticks[myName]==1)&(chopSticks[(myName+1)%5]==1)){
	
	chopSticks[myName]=0; // Acquire right
	chopSticks[(myName+1)%5]=0; // Acquire left
	haveChopsticks=true;
	System.out.println("Philosopher "+myName+"acquired chopsticks");
	}
	mutex.release();


	try { // Simulate eating activity for a random time
	sleep ((int)(Math.random()*10000));
	} catch (InterruptedException e) {}

	mutex.acquire();
	chopSticks[myName]=1; // Release right
	chopSticks[(myName+1)%5]=1; // Release left
	System.out.println("Philosopher "+myName+ " released both 			chopsticks");
	mutex.release();

	}
}
}
}
