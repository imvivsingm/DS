package multithreading.important;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private int value = 0;  // Shared resource

    // Read method: multiple threads can read simultaneously
    public int read() {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reading value: " + value);
            // Simulate read delay
            Thread.sleep(100);
            return value;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    // Write method: only one thread can write at a time
    public void write(int newValue) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " writing value: " + newValue);
            // Simulate write delay
            Thread.sleep(200);
            value = newValue;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    // Main to test
    public static void main(String[] args) {
        ReadWriteLockDemo resource = new ReadWriteLockDemo();

        // Writer task as Runnable
        Runnable writerTask = () -> {
            for (int i = 1; i <= 5; i++) {
                resource.write(i * 10);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        // Reader task as Runnable (same as before)
        Runnable readerTask = () -> {
            for (int i = 0; i < 5; i++) {
                resource.read();
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(writerTask);
        executorService.execute(writerTask);
        executorService.execute(readerTask);
        executorService.execute(readerTask);

        executorService.shutdown();

        // Create threads from Runnable tasks

        // Start threads

    }
}

