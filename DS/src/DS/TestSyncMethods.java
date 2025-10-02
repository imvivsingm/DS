package DS;

class SharedResource {

    // First synchronized method
    public synchronized void methodOne() {
        System.out.println(Thread.currentThread().getName() + " entered methodOne");
        try {
            Thread.sleep(2000);  // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " leaving methodOne");
    }

    // Second synchronized method
    public synchronized void methodTwo() {
        System.out.println(Thread.currentThread().getName() + " entered methodTwo");
        try {
            Thread.sleep(2000);  // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " leaving methodTwo");
    }
}

public class TestSyncMethods {

    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        // Thread 1 calls methodOne
        Thread t1 = new Thread(() -> resource.methodOne(), "Thread-1");

        // Thread 2 calls methodTwo
        Thread t2 = new Thread(() -> resource.methodTwo(), "Thread-2");

        t1.start();
        t2.start();
    }
}

