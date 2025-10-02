package multithreading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + " -> " + "This is runnable");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Callable<String> callable = () ->{
            System.out.println("Callable is getting executed" +
                     "::" + Thread.currentThread().getName());
            return "callable Has Returned" + Thread.currentThread().getName();
        };

        executorService.submit(runnable);
        Future<String> callableResponse = executorService.submit(callable);
        executorService.shutdown();
    }
}
