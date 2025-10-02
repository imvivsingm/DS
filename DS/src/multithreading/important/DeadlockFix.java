package multithreading.important;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class DeadlockFix {

    private static final Lock lock1 = new ReentrantLock();
    private static final Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                if (lock1.tryLock(1, TimeUnit.SECONDS)) {
                    System.out.println("Thread 1: Locked lock1");

                    Thread.sleep(100);

                    if (lock2.tryLock(1, TimeUnit.SECONDS)) {
                        System.out.println("Thread 1: Locked lock2");
                        lock2.unlock();
                    } else {
                        System.out.println("Thread 1: Could not lock lock2, releasing lock1");
                    }

                    lock1.unlock();
                } else {
                    System.out.println("Thread 1: Could not lock lock1");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                if (lock2.tryLock(1, TimeUnit.SECONDS)) {
                    System.out.println("Thread 2: Locked lock2");

                    Thread.sleep(100);

                    if (lock1.tryLock(1, TimeUnit.SECONDS)) {
                        System.out.println("Thread 2: Locked lock1");
                        lock1.unlock();
                    } else {
                        System.out.println("Thread 2: Could not lock lock1, releasing lock2");
                    }

                    lock2.unlock();
                } else {
                    System.out.println("Thread 2: Could not lock lock2");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }
}

