package semaphore_topic;

import java.util.concurrent.Semaphore;

public class Barbershop extends Thread {
    public static final int CHAIRS = 5;
    public static Semaphore customers = new Semaphore(0);
    public static Semaphore barber = new Semaphore(0);
    public static Semaphore mutex = new Semaphore(1);
    public static int waiting = 0;

    public static void main(String[] args) {
        Barbershop barber = new Barbershop();
        barber.start();
        for (int i = 1; i <= 10; i++) {
            Customer customer = new Customer(i);
            customer.start();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                customers.acquire();
                mutex.acquire();
                waiting--;
                cutHair();
                System.out.println("Barber is cutting hair");
                barber.release();
                mutex.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void cutHair() {
        System.out.println("The barber is cutting hair");
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Customer extends Thread {
        int number;

        public Customer(int number) {
            this.number = number;
        }

        public void run() {
            try {
                mutex.acquire();
                if (waiting < CHAIRS) {
                    System.out.println("Customer " + this.number + " is waiting");
                    waiting++;
                    customers.release();
                    mutex.release();
                    try {
                        barber.acquire();
                        getHaircut();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Customer " + this.number + " is leaving");
                    mutex.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void getHaircut() {
            System.out.println("Customer " + this.number + " is getting a haircut");
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
