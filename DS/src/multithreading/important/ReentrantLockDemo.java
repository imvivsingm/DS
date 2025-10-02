package multithreading.important;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    private final ReentrantLock lock = new ReentrantLock();

    public void methodA() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " acquired lock in methodA, hold count: " + lock.getHoldCount());
            methodB();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + " released lock in methodA, hold count: " + lock.getHoldCount());
        }
    }

    public void methodB() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " acquired lock in methodB, hold count: " + lock.getHoldCount());
            // Simulate some work with sleep
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + " released lock in methodB, hold count: " + lock.getHoldCount());
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo demo = new ReentrantLockDemo();

        Runnable task = () -> {
            demo.methodA();
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        Thread t3 = new Thread(task, "Thread-3");

        t1.start();
        t2.start();
        t3.start();
    }
}
