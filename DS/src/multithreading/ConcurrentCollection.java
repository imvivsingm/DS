package multithreading;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentCollection {

    public static void main(String[] args) throws InterruptedException {
        Map<Integer, String> hm = new HashMap<>();
        Map<Integer, String> hm2 = new ConcurrentHashMap<>();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 1000; i++) {
                    hm.put(i, "Number-"+String.valueOf(i));
                }
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 1000; i++) {
                    hm2.put(i, "Number-"+String.valueOf(i));
                }
            }
        };

        Thread t1 = new Thread(runnable2);
        Thread t2 = new Thread(runnable2);
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(hm.size());
        System.out.println(hm2.size());

    }
}
