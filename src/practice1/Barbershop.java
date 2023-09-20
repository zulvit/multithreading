package practice1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import static semaphore_topic.Barbershop.mutex;

public class Barbershop {
    public static final int MAX_SEATS = 5;
    private final AtomicInteger waitingClients = new AtomicInteger(0);
    private final Semaphore barber = new Semaphore(0);
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore clientSemaphore = new Semaphore(MAX_SEATS);

    private final BarbershopUI ui;

    public Barbershop() {
        this.ui = new BarbershopUI(this);
        new Thread(new Barber()).start();
    }

    public static void main(String[] args) {
        new Barbershop();
    }

    public void addClient() {
        try {
            mutex.acquire();
            waitingClients.incrementAndGet();
            Thread thread = new Thread(new Client(clientSemaphore));
            ui.addClientUI(thread);
            thread.start();
            barber.release();
            mutex.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public class Barber extends Thread {
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    barber.acquire();
                    mutex.acquire();
                    if (waitingClients.get() > 0) {
                        System.out.println("���������� ����� �������.");
                        waitingClients.decrementAndGet();
                        ui.changeClientColorToOrange();
                        mutex.release();
                        Thread.sleep((int) (Math.random() + 1) * 5000);
                        System.out.println("���������� �������� �������.");
                        ui.removeClientUI();
                        clientSemaphore.release();
                    } else {
                        mutex.release();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

class Client implements Runnable {
    private final Semaphore clientSemaphore;

    public Client(Semaphore clientSemaphore) {
        this.clientSemaphore = clientSemaphore;
    }

    @Override
    public void run() {
        try {
            mutex.acquire();
            System.out.println(Thread.currentThread().getName() + ": ������ ��� � ��������.");
            mutex.release();
            clientSemaphore.acquire();
//            System.out.println(Thread.currentThread().getName() + ": ������ ����.");
            clientSemaphore.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

