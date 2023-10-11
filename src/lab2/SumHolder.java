package lab2;

class SumHolder {
    private long totalSum = 0;

    public synchronized void addToTotalSum(long sum) {
        totalSum += sum;
    }

    public synchronized long getTotalSum() {
        return totalSum;
    }
}