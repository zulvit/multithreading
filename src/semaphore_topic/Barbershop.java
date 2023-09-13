package semaphore_topic;

import java.util.List;
import java.util.concurrent.Semaphore;

public class Barbershop extends Thread {
    public static final int CHAIRS = 5;
    public static Semaphore customers = new Semaphore(0);
    public static Semaphore barber = new Semaphore(1);
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
                mutex.acquire();
                customers.acquire();
                cutHair();
                waiting--;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void cutHair() {
        System.out.println("The barber is cutting hair");
        try {
            sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Barber finished work");
        mutex.release();
        barber.release();
    }

    static class Customer extends Thread {
        int number;

        public Customer(int number) {
            this.number = number;
        }

        public void run() {
            //                mutex.acquire();
            if (waiting < CHAIRS) {
                System.out.println("Customer " + this.number + " is waiting on seat");
                waiting++;
                customers.release();
                //                    barber.acquire();
//                getHaircut();
//                    mutex.release();
            } else {
                System.out.println("Customer " + this.number + " is leaving via no free seats");
//                mutex.release();
            }
        }

//        public void getHaircut() {
//            System.out.println("Customer " + this.number + " ready to get a haircut");
//            try {
//                sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Customer " + this.number + " got a haircut");
//        }
    }
}
