package addition;

import java.util.Random;

public class SumThread {
    private final Object lock;
    private final int[] array = new int[1_000_000];
    private int counter = 0;

    public SumThread(Object lock) {
        this.lock = lock;
    }

    private void generateRandomNumbers() {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
        }
    }

    private void multithreadingAdditionArray() {
        while (counter <= array.length) {
            synchronized (lock) {

                counter++;
            }
        }
    }

    public static void main(String[] args) {
        Object lock = new Object();
        SumThread sumThread = new SumThread(lock);
        sumThread.generateRandomNumbers();
    }
}
