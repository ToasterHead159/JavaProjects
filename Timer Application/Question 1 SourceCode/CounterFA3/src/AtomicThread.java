
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicThread extends Thread {

    private AtomicInteger atomCTN = new AtomicInteger(0);//makes a new atomic integer object and gives it a Zero value

    public synchronized void increment()//method to increament the atomic integer.
    {
        atomCTN.incrementAndGet();//this just increments the atomic integer
    }

    public int getCounter() {//getter for the atomic integer.
        return atomCTN.get();
    }

    int value; //value that will be passed back from the thread 

    private int userInput; //value that will be passed and used in the for loop.

    public AtomicThread(int value) { //constructor for thread
        this.userInput = value;
    }
    @Override
    public synchronized void run() {

        for (int i = 0; i < this.userInput; i++) { //uses the user input value to increment the counter the specific number of times.
            increment();
        }
        value = getCounter();//this assigns the value of the atomic integer to the value so that it can be used in the main to show the user.
    }
}
//vh/fh