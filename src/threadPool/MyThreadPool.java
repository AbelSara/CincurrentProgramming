package threadPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author Honghan Zhu
 */
public class MyThreadPool<Job extends Runnable> {
    private static final int MAX_WORKER_NAMBERS = 10;
    private static final int DEFAULT_WORKER_NUMBERS = 5;
    private static final int MIN_WORKER_NUMBERS = 1;
    private final LinkedList<Job> jobs = new LinkedList<Job>();
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
    private int workerNum = DEFAULT_WORKER_NUMBERS;
    private AtomicLong threadNum = new AtomicLong(0);

    public MyThreadPool() {
        initializeWorkers(DEFAULT_WORKER_NUMBERS);
    }

    public MyThreadPool(int num) {
        num = num > MAX_WORKER_NAMBERS ? MAX_WORKER_NAMBERS : num;
        initializeWorkers(num);
    }

    public void execute(Job job) {
        if (null != jobs) {
            synchronized (jobs) {
                jobs.add(job);
                jobs.notify();
            }
        }
    }

    public void shutdown() {
        workers.forEach(w -> w.shutdown());
    }

    public void addWorkers(int num) {
        synchronized (jobs) {
            if (num + threadNum.get() > MAX_WORKER_NAMBERS) {
                num = (int) (MAX_WORKER_NAMBERS - threadNum.get());
            }
            initializeWorkers(num);
            this.workerNum += num;
        }
    }

    public void removeWorkers(int num) {
        synchronized (jobs) {
            if (num > workerNum) {
                num = workerNum;
            }
            for (int i = 0; i < num; ) {
                Worker worker = workers.get(0);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    i++;
                }
            }
            this.workerNum -= num;
        }
    }

    public int getJobSize() {
        return jobs.size();
    }

    private void initializeWorkers(int num) {
        System.out.println("-------------------------------init worker-----------------------------");
        for (int i = 1; i <= num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            new Thread(worker, "worker-" + threadNum.incrementAndGet()).start();
        }
    }

    class Worker implements Runnable {
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    while (jobs.isEmpty() && running) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }
}
