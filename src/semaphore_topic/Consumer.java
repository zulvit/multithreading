package semaphore_topic;

import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Consumer implements Runnable {
    private final Queue<Integer> queue;
    private final Semaphore spaceSemaphore;
    private final Semaphore itemSemaphore;

    public Consumer(Queue<Integer> queue, Semaphore spaceSemaphore, Semaphore itemSemaphore) {
        this.queue = queue;
        this.spaceSemaphore = spaceSemaphore;
        this.itemSemaphore = itemSemaphore;
    }

    @Override
    public void run() {
        while (true) {
            try {
                itemSemaphore.acquire();
                synchronized (queue) {
                    System.out.println("Consumed " + queue.poll());
                }
                spaceSemaphore.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
