package multithreading.important;

public class DeadlockDemo {

    private final Object resourceA = new Object();
    private final Object resourceB = new Object();

    public void method1() {
        synchronized (resourceA) {
            System.out.println("Thread 1: locked resource A");

            // Adding delay to make deadlock more likely
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            synchronized (resourceB) {
                System.out.println("Thread 1: locked resource B");
            }
        }
    }

    public void method2() {
        synchronized (resourceA) {
            System.out.println("Thread 2: locked resource B");

            // Adding delay to make deadlock more likely
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            synchronized (resourceB) {
                System.out.println("Thread 2: locked resource A");
            }
        }
    }

    public static void main(String[] args) {
        DeadlockDemo demo = new DeadlockDemo();

        Thread t1 = new Thread(() -> demo.method1(), "Thread-1");
        Thread t2 = new Thread(() -> demo.method2(), "Thread-2");

        t1.start();
        t2.start();
    }
}


