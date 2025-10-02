package multithreading;

public class OddEvenPrinter {
    private final int MAX = 10; // Change this to any max value
    private int number = 1;
    private final Object lock = new Object();

    public static void main(String[] args) {
        OddEvenPrinter printer = new OddEvenPrinter();

        Thread oddThread = new Thread(() -> printer.printOdd(), "OddThread");
        Thread evenThread = new Thread(() -> printer.printEven(), "EvenThread");

        oddThread.start();
        evenThread.start();
    }

    public void printOdd() {
        while (number <= MAX) {
            synchronized (lock) {
                if (number % 2 == 1) {
                    System.out.println(Thread.currentThread().getName() + " -> " + number);
                    number++;
                    lock.notify();
                } else {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    public void printEven() {
        while (number <= MAX) {
            synchronized (lock) {
                if (number % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + " -> " + number);
                    number++;
                    lock.notify();
                } else {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}
