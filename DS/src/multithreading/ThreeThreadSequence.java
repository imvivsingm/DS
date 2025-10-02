package multithreading;

public class ThreeThreadSequence {
    private final int N;           // The maximum number to print
    private int counter = 1;       // Shared counter starting at 1
    private final Object lock = new Object();  // Lock object for synchronization

    public ThreeThreadSequence(int N) {
        this.N = N;
    }

    // Method each thread will run to print numbers
    public void printNumbers(int threadId) {
        while (true) {
            synchronized (lock) {
                // Wait until it's this thread's turn or printing is done
                // (counter - 1) % 3 gives which thread should print this number
                int num = (counter - 1) % 3;
                while (counter <= N &&  num != threadId) {
                    try {
                        // If it's not this thread's turn, wait
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Restore interrupted status
                        return;  // Exit if thread is interrupted
                    }
                }

                // If counter exceeded N, printing is done
                if (counter > N) {
                    lock.notifyAll();  // Wake up any waiting threads so they can exit
                    break;             // Exit the loop
                }

                // Print the current number with the thread ID
                System.out.println("Thread " + (threadId + 1) + ": " + counter);

                counter++;  // Increment counter for next number

                // Notify other threads waiting to print their numbers
                lock.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        int N = 20; // Change this to print up to any number you want

        ThreeThreadSequence seq = new ThreeThreadSequence(N);

        // Create 3 threads, each assigned an ID 0, 1, or 2
        Thread t1 = new Thread(() -> seq.printNumbers(0));
        Thread t2 = new Thread(() -> seq.printNumbers(1));
        Thread t3 = new Thread(() -> seq.printNumbers(2));

        // Start all threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for all threads to finish before exiting main
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
