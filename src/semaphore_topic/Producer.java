package semaphore_topic;

import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Producer implements Runnable {
    private final Queue<Integer> queue;
    private final Semaphore spaceSemaphore;
    private final Semaphore itemSemaphore;

    public Producer(Queue<Integer> queue, Semaphore spaceSemaphore, Semaphore itemSemaphore) {
        this.queue = queue;
        this.spaceSemaphore = spaceSemaphore;
        this.itemSemaphore = itemSemaphore;
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            try {
                spaceSemaphore.acquire();
                synchronized (queue) {
                    queue.add(i);
                    System.out.println(Thread.currentThread().getName() + " produced " + i);
                    i++;
                }
                itemSemaphore.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
