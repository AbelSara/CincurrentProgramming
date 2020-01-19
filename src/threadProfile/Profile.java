package threadProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Honghan Zhu
 * @Describe waiting, time waiting, blocked.
 */
public class Profile {
    public static void main(String[] args) {
        List<Thread> jobs = new ArrayList<>();
        Thread twt = new Thread(new TimeWaitingThread(), "time_waiting_thread");
        jobs.add(twt);
        Thread wt = new Thread(new WaitingThread(), "waiting_thread");
        jobs.add(wt);
        Thread bt1 = new Thread(new BlockedThread(), "blocked_thread_1");
        jobs.add(bt1);
        Thread bt2 = new Thread(new BlockedThread(), "blocked_thread_2");
        jobs.add(bt2);
        jobs.stream().forEach(t -> t.start());
    }

    static class TimeWaitingThread implements Runnable {

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

    static class WaitingThread implements Runnable {

        @Override
        public void run() {
            synchronized (WaitingThread.class) {
                try {
                    WaitingThread.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class BlockedThread implements Runnable {

        @Override
        public void run() {
            synchronized (BlockedThread.class) {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
