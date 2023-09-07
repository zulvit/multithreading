package consumer_producer;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        int maxSize = 10;

        Thread producer = new Thread(new Producer(queue, maxSize), "Producer");
        Thread consumer = new Thread(new Consumer(queue), "Consumer");

        producer.start();
        consumer.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }
}

class Consumer implements Runnable {
    private volatile boolean shouldRun = true;
    private final Queue queue;

    Consumer(Queue queue) {
        this.queue = queue;
    }

    public void stop() {
        shouldRun = false;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(Thread.currentThread().getName() + "Потребитель: " + queue.poll());
                queue.notifyAll();
            }
        }
    }
}

class Producer implements Runnable {
    private volatile boolean shouldRun = true;
    private final Queue queue;
    private final int maxSize;

    Producer(Queue queue, int maxSize) {
        this.queue = queue;
        this.maxSize = maxSize;
    }

    public void stop() {
        shouldRun = false;
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            synchronized (queue) {
                while (queue.size() >= maxSize) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(Thread.currentThread().getName() + "-Произведено: " + i);
                queue.add(i++);
                queue.notifyAll();
            }
        }
    }
}