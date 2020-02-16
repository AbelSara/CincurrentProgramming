package concurrentBasic;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Honghan Zhu
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        int threadCount = 30;
        ExecutorService executor = Executors.newFixedThreadPool(30);
        Semaphore semaphore = new Semaphore(10);
        AtomicInteger num = new AtomicInteger(1);
        for (int i = 0; i < threadCount; i++) {
            executor.execute(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("num " + num.getAndIncrement() + " pass");
                    Thread.sleep(5000);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }
}
