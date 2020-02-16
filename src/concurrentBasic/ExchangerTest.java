package concurrentBasic;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Honghan Zhu
 */
public class ExchangerTest {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        Exchanger<String> exchanger = new Exchanger<>();
        threadPool.execute(() -> {
            String str = "1000000";
            try {
                exchanger.exchange(str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadPool.execute(() -> {
            String str = "600";
            try {
                String ex = exchanger.exchange(str);
                System.out.println("user " + str + " change "+ex);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadPool.shutdown();
    }
}
