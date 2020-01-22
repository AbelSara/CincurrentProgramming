package threadProfile;

import java.util.concurrent.TimeUnit;

/**
 * @Auhtor Honghan Zhu
 * @Describe stop thread gracefully
 */
public class StopThread {
    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();
        Thread t1 = new Thread(runner, "count_thread_1");
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        runner.cancelThread();
        Thread t2 = new Thread(new Runner(), "count_thread_2");
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        t2.interrupt();
    }

    static class Runner implements Runnable {
        private long count = 0;
        private volatile boolean on = true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                count++;
            }
            System.out.println(Thread.currentThread().getName() + ": " + count);
        }

        public void cancelThread() {
            on = false;
        }
    }
}
