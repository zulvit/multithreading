package practice1;

import java.util.concurrent.Semaphore;

public class Barbershop implements BarberShopUIEventListener {
    public static final int MAX_SEATS = 5;
    private final Semaphore MAX_NUMBER_OF_PEOPLE_CAN_GET_HAIRCUT = new Semaphore(1);
    private static int clientCurrentCounter = 0;
    private static int clientCounter = 0;
    private final BarbershopUI barbershopUI;

    public Barbershop(BarbershopUI barbershopUI) {
        this.barbershopUI = barbershopUI;
    }

    public static void main(String... args) {
        BarbershopUI barbershopUI = new BarbershopUI();
        Barbershop barbershop = new Barbershop(barbershopUI);
        barbershopUI.setEventListener(barbershop);
    }

    public synchronized void addClient() {
        if (clientCurrentCounter < MAX_SEATS) {
            clientCurrentCounter++;
            Client client = new Client(MAX_NUMBER_OF_PEOPLE_CAN_GET_HAIRCUT, clientCounter++ + " client:", this);
            client.start();
            barbershopUI.addClient(client);
        } else {
            clientCurrentCounter--;
            System.out.println("Мест нет, клиент развернулся и ушёл.");
        }
    }

    public void clientLeave(Client client) {
        clientCurrentCounter--;
        barbershopUI.removeClient(client);
    }

    public void clientDone(Client client) {
        barbershopUI.changeClientColor(client, BarbershopUI.CLIENT_WAIT_COLOR);
    }

    public void clientWaiting(Client client) {
        barbershopUI.changeClientColor(client, BarbershopUI.CLIENT_PROCESS_COLOR);
    }

    @Override
    public void onAddClientButtonClicked() {
        addClient();
    }
}