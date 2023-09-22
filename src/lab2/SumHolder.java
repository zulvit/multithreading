package lab2;

class SumHolder {
    private int totalSum = 0;

    public void addToTotalSum(int sum) {
        totalSum += sum;
    }

    public int getTotalSum() {
        return totalSum;
    }
}