package threadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author Honghan Zhu
 */
public class MyThread implements Runnable {
    CountDownLatch end;

    public MyThread(CountDownLatch end) {
        this.end = end;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " start work !");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + " over !");
        end.countDown();
    }
}
