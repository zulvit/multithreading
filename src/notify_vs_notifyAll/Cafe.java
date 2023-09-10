package notify_vs_notifyAll;

// � ���� ������� � ��� ���� ����, ������� ����� ������������ �����������
// ������������ ���������� ��������. ������� ����� � ������� � ���� ������ ����,
// � ������� ������ ���� � ����� ��������� ���� ��������, ��� ���� �����.
public class Cafe {
    private int availableCoffee = 0;
    private final Object lock = new Object();

    public void makeCoffee(int n) {
        synchronized (lock) {
            availableCoffee += n;
            System.out.println(n + " ���� ������. ���������� ���� � �������.");
            lock.notifyAll();
        }
    }

    public void serveCustomer(String customerName) {
        synchronized (lock) {
            while (availableCoffee == 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            availableCoffee--;
            System.out.println("���� ��� ���� �������������: " + customerName);
        }
    }

    public static void main(String[] args) {
        Cafe cafe = new Cafe();

        Thread barista = new Thread(() -> {
            try {
                Thread.sleep(2000);
                cafe.makeCoffee(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread customer1 = new Thread(() -> cafe.serveCustomer("customer1"));
        Thread customer2 = new Thread(() -> cafe.serveCustomer("customer2"));
        Thread customer3 = new Thread(() -> cafe.serveCustomer("customer3"));

        customer1.start();
        customer2.start();
        customer3.start();
        barista.start();
    }
}


