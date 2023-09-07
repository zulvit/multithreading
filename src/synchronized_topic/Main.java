package synchronized_topic;

public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();

        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(counter::increment);
            Thread thread1 = new Thread(counter::increment);

            thread.start();
            thread1.start();

            try {
                thread.join();
                thread1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(counter.getCount());
        }
    }
}