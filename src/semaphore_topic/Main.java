package semaphore_topic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        int maxSize = 10;
        Semaphore spaceSemaphore = new Semaphore(maxSize);
        Semaphore itemSemaphore = new Semaphore(0);
        int numberOfProducers = 5;

        for (int i = 0; i < numberOfProducers; i++) {
            new Thread(new Producer(queue, spaceSemaphore, itemSemaphore), "Producer").start();
        }
        new Thread(new Consumer(queue, spaceSemaphore, itemSemaphore), "Consumer").start();
    }
}
