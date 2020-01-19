package threadProfile;

/**
 * @Author Honghan Zhu
 * @Describe daemon
 */
public class Daemon {
    public static void main(String[] args) {
        Thread daemonThread = new Thread(new DaemonThread(), "daemon_thread");
        daemonThread.setDaemon(true);
        daemonThread.start();
    }

    static class DaemonThread implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("exit daemon thread!");
            }
        }
    }
}
