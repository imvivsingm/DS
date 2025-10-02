package multithreading;

import java.util.Random;
import java.util.concurrent.*;


public class ProducerConsumer {
    private static final int BUFFER_SIZE = 5;
    private static final int NUM_PRODUCERS = 2;
    private static final int NUM_CONSUMERS = 3;
    private static final int ITEMS_PER_PRODUCER = 5;
    private static final int POISON_PILL = -1;

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(BUFFER_SIZE);
        ExecutorService executor = Executors.newFixedThreadPool(NUM_PRODUCERS + NUM_CONSUMERS);

        // Start producers
        for (int i = 0; i < NUM_PRODUCERS; i++) {
            executor.submit(new Producer(queue, ITEMS_PER_PRODUCER, POISON_PILL, NUM_CONSUMERS));
        }

        // Start consumers
        for (int i = 0; i < NUM_CONSUMERS; i++) {
            executor.submit(new Consumer(queue, POISON_PILL));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("All tasks completed.");
    }
}

// Producer class
class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final int itemsToProduce;
    private final int poisonPill;
    private final int numConsumers;
    private final Random random = new Random();

    public Producer(BlockingQueue<Integer> queue, int itemsToProduce, int poisonPill, int numConsumers) {
        this.queue = queue;
        this.itemsToProduce = itemsToProduce;
        this.poisonPill = poisonPill;
        this.numConsumers = numConsumers;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < itemsToProduce; i++) {
                int item = random.nextInt(100);
                queue.put(item);
                System.out.println(Thread.currentThread().getName() + " Produced: " + item);
                Thread.sleep(300);
            }

            // After producing, add poison pills for each consumer
            for (int i = 0; i < numConsumers; i++) {
                queue.put(poisonPill);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Consumer class
class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final int poisonPill;

    public Consumer(BlockingQueue<Integer> queue, int poisonPill) {
        this.queue = queue;
        this.poisonPill = poisonPill;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int item = queue.take();

                if (item == poisonPill) {
                    System.out.println(Thread.currentThread().getName() + " Received poison pill. Shutting down.");
                    break;
                }

                System.out.println(Thread.currentThread().getName() + " Consumed: " + item);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}