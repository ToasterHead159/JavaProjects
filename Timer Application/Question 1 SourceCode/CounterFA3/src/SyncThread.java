
public class SyncThread extends Thread {

    int value;//value that will be passed back from the thread 

    private int userInput;//value that will be passed and used in the for loop.

    public SyncThread(int value) {//constructor for thread
        this.userInput = value;
    }

    ;
    @Override
    public synchronized void run() {

        for (int i = 0; i < this.userInput; i++) {//uses the user input value to increment the counter the specific number of times.
            value++;
        }
    }
}
//vh/fh