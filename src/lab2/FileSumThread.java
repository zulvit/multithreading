package lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class FileSumThread implements Runnable {
    private final String fileName;
    private final SumHolder sumHolder;

    public FileSumThread(String fileName, SumHolder sumHolder) {
        this.fileName = fileName;
        this.sumHolder = sumHolder;
    }

    @Override
    public void run() {
        int sum = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int number = Integer.parseInt(line);
                sum += number;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        sumHolder.addToTotalSum(sum);
        System.out.println("Сумма чисел из файла " + fileName + ": " + sum);
    }
}