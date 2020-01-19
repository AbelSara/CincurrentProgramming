package threadProfile;

/**
 * @Author Honghan Zhu
 * @Describe interrupt, isInterrupted
 */
public class Interrupted {
    public static void main(String[] args) throws InterruptedException {
        Thread workThread = new Thread(new WorkThread(), "work_thread");
        workThread.setDaemon(true);
        Thread sleepThread = new Thread(new SleepThread(), "sleep_thread");
        sleepThread.setDaemon(true);
        sleepThread.start();
        workThread.start();
        Thread.sleep(10);
        sleepThread.interrupt();
        workThread.interrupt();
        //thread.sleep()抛出异常时清除interrupted标志位
        System.out.println("sleep thread interrupted state is " + sleepThread.isInterrupted());
        System.out.println("work thread interrupted state is " + workThread.isInterrupted());
        Thread.sleep(100);
    }

    static class SleepThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class WorkThread implements Runnable {
        @Override
        public void run() {
            int i = 0;
            while (true) {
                ++i;
            }
        }
    }
}
