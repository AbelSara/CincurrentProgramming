package concurrentBasic;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author Honghan Zhu
 */
public class CyclicBarrierTest {

    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                System.out.println("thread 1 arrived");
                c.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("thread 1 exist");
        }).start();
        try {
            Thread.sleep(50);
            System.out.println("main thread arrived");
            c.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("main thread exist");
    }

    static class A implements Runnable {
        @Override
        public void run() {
            System.out.println("prior thread exist");
        }
    }
}
