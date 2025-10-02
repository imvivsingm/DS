package multithreading;

import java.util.concurrent.*;

public class FutureCallableExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> task = () -> {
            Thread.sleep(1000);
            return "hello from callable";
        };
        Future<String> futureTask = executorService.submit(task);

        System.out.println("Doing other work...");

        String result = futureTask.get();

        System.out.println("Result: " + result);

        executorService.shutdown();
    }
}
