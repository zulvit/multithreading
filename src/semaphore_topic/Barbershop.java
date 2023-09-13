package semaphore_topic;

import java.util.concurrent.Semaphore;

public class Barbershop extends Thread {
    public static final int CHAIRS = 5;
    public static Semaphore customers = new Semaphore(0);
    public static Semaphore barber = new Semaphore(1);
    public static Semaphore mutex = new Semaphore(1);
    public static int waiting = 0;
    private static final int COUNT = 20;
    private static int currentCount = 0;
    private final static int CHILL_TIME = 1500;
    private final static int WORK_TIME = 3500;
    private final static int CUSTOMER_INTERVAL = 2000;

    public static void main(String[] args) {
        Barbershop barber = new Barbershop();
        barber.start();
        for (int i = 1; i <= COUNT; i++) {
            Customer customer = new Customer(i);
            customer.start();
            try {
                sleep(CUSTOMER_INTERVAL);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                mutex.acquire();
                customers.acquire();
                cutHair();
                waiting--;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (COUNT == currentCount) {
                break;
            }
        }
    }

    public void cutHair() {
        System.out.println("The barber is cutting hair");
        try {
            sleep(WORK_TIME);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Barber finished work");
        mutex.release();
        barber.release();
        try {
            sleep(CHILL_TIME);
            System.out.println("Barber are chilling out");
            sleep(CHILL_TIME);
            System.out.println("Barber are ready to work");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    static class Customer extends Thread {
        int number;

        public Customer(int number) {
            this.number = number;
        }

        public void run() {
            if (waiting < CHAIRS) {
                System.out.println("Customer " + this.number + " is waiting on seat");
                waiting++;
                customers.release();
            } else {
                System.out.println("Customer " + this.number + " is leaving via no free seats");
            }
            currentCount++;
        }
    }
}
