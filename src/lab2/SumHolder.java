package lab2;

class SumHolder {
    private int totalSum = 0;

    public synchronized void addToTotalSum(int sum) {
        totalSum += sum;
    }

    public synchronized int getTotalSum() {
        return totalSum;
    }
}