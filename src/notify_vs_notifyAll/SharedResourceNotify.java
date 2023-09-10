package notify_vs_notifyAll;

public class SharedResourceNotify {
    private int data;
    private boolean isDataAvailable = false;

    public synchronized void produce(int value) {
        while (isDataAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        data = value;
        isDataAvailable = true;
        notify(); //����������� ������ ������ �� ��������� �������
    }

    public synchronized int consume() {
        while (!isDataAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            isDataAvailable = false;
            notify(); //����������� ������ ������ �� ��������� �������
        }
        return data;
    }
}
