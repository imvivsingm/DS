package multithreading.important;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    private static final int NUMBER_OF_WORKERS = 3;
    private static final CountDownLatch latch = new CountDownLatch(NUMBER_OF_WORKERS);

    public static void main(String[] args) {
        System.out.println("Main thread started. Waiting for workers to finish...");

        // Start worker threads
        for (int i = 1; i <= NUMBER_OF_WORKERS; i++) {
            new Thread(new Worker(i, latch)).start();
        }

        try {
            // Main thread waits until latch count reaches zero
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted while waiting.");
        }

        System.out.println("All workers finished. Main thread proceeds.");
    }

    // Worker class that simulates some work and then counts down the latch
    static class Worker implements Runnable {
        private final int workerId;
        private final CountDownLatch latch;

        public Worker(int workerId, CountDownLatch latch) {
            this.workerId = workerId;
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println("Worker " + workerId + " started working.");

            try {
                // Simulate some work with sleep
                Thread.sleep((long) (Math.random() * 3000) + 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Worker " + workerId + " was interrupted.");
            }

            System.out.println("Worker " + workerId + " finished work.");

            // Decrement the latch count by 1
            latch.countDown();
        }
    }
}

