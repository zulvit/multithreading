package practice1;

import java.util.concurrent.Semaphore;

import static semaphore_topic.Barbershop.mutex;

public class Barbershop {
    public static final int MAX_SEATS = 5;
    private int waitingClients = 0;
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
            if (waitingClients < MAX_SEATS) {
                waitingClients++;
                ui.addClientUI();
                new Thread(new Client(clientSemaphore)).start();
                barber.release();
            } else {
                System.out.println("Нет мест для новых клиентов");
            }
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
                    if(waitingClients > 0) {
                        waitingClients--;
                        ui.changeClientColorToOrange(); // изменить цвет на оранжевый
                        mutex.release();
                        Thread.sleep(1000);  // имитация стрижки
                        ui.removeClientUI();  // удалить клиента
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
            System.out.println(Thread.currentThread().getName() + ": клиент сел в ожидание.");
            mutex.release();

            clientSemaphore.acquire();
            System.out.println(Thread.currentThread().getName() + ": клиент ушёл.");
            clientSemaphore.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

