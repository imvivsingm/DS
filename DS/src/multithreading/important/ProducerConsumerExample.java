package multithreading.important;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerExample {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        final int MAX_ITEMS = 10;

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < MAX_ITEMS; i++) {
                    System.out.println("Producing item: " + i);
                    queue.put(i);
                    Thread.sleep(200); // simulate production delay
                }
                System.out.println("Producer finished producing.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < MAX_ITEMS; i++) {
                    int item = queue.take();
                    System.out.println("Consuming item: " + item);
                    Thread.sleep(400); // simulate consumption delay
                }
                System.out.println("Consumer finished consuming.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        // makes the main thread to wait will exeution of T1 & t2 is complete.
        producer.join();
        consumer.join();

        System.out.println("Both producer and consumer have finished.");
    }
}

