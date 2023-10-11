package practice1;

import java.util.concurrent.Semaphore;

public class Client extends Thread {
    private final Semaphore semaphore;
    private boolean isCut = false;
    private final String nameClient;
    private final Barbershop barbershop;

    public Client(Semaphore semaphore, String nameClient, Barbershop barbershop) {
        this.semaphore = semaphore;
        this.nameClient = nameClient;
        this.barbershop = barbershop;
    }


    public String getNameClient() {
        return nameClient;
    }

    @Override
    public void run() {
        if (!isCut) {
            try {
                semaphore.acquire();
                barbershop.clientWaiting(this);
                System.out.println("---------" + nameClient + "---------");
                System.out.println(nameClient + " садится стричься...");

                sleep(1000);
                isCut = true;

                System.out.println(nameClient + " постригся, уходит.");
                semaphore.release();
                barbershop.clientDone(this);

                sleep(1000);
                barbershop.clientLeave(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
