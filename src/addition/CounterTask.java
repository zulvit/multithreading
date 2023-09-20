package addition;

public class CounterTask implements Runnable {
    private final Object lock;
    private final int start;
    private static int counter = 1;

    public CounterTask(Object lock, int start) {
        this.lock = lock;
        this.start = start;
    }

    @Override
    public void run() {
        while (counter <= 10000) {
            synchronized (lock) {
                if (counter % 2 == start) {
                    System.out.println(Thread.currentThread().getName() + ": " + counter++);
                    lock.notify();
                } else {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread thread = new Thread(new CounterTask(lock, 0), "Thread1");
        Thread thread1 = new Thread(new CounterTask(lock, 1), "Thread2");
        thread.start();
        thread1.start();

        thread.join();
        thread1.join();
    }
}
