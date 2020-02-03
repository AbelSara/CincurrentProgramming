package threadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author Honghan Zhu
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch end = new CountDownLatch(10);
        MyThreadPool myThreadPool = new MyThreadPool();
        for (int i = 0; i < 10; i++) {
            MyThread job = new MyThread(end);
            myThreadPool.execute(job);
        }
        TimeUnit.SECONDS.sleep(5);
        myThreadPool.addWorkers(5);
        end.await();
        System.out.println("exit main thread !!");
        myThreadPool.shutdown();
    }
}
