package atomic_variables;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger i = new AtomicInteger(0);

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            new MyThread().start();
        }

        try {
            Thread.sleep(2000);
            System.out.println(i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            i.incrementAndGet();
        }
    }
}