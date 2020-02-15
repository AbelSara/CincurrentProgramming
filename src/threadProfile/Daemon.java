package threadProfile;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author Honghan Zhu
 * @Describe daemon
 */
public class Daemon {
    public static void main(String[] args) throws InterruptedException {
        Thread daemonThread = new Thread(new DaemonThread(), "daemon_thread");
        daemonThread.setDaemon(true);
        daemonThread.start();
        Thread.sleep(50);
        System.out.println("main thread exit");
    }

    static class DaemonThread implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("enter");
                Thread.sleep(100);
                System.out.println("exit");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("exit daemon thread!");
            }
        }
    }
}
