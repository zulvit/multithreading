package lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Summator {
    private final String FILE_PATH;
    private final int FILES_COUNT;
    private final String FILE_NAME;
    private final String FILE_SUFFIX;

    public Summator(String filePath, int filesCount, String fileName, String fileSuffix) {
        FILE_PATH = filePath;
        FILES_COUNT = filesCount;
        FILE_NAME = fileName;
        FILE_SUFFIX = fileSuffix;
    }

    public void asyncSumCount() {
//        SumHolder sumHolder = new SumHolder();
//        var start = LocalDateTime.now();
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < FILES_COUNT; i++) {
//                    String fileName = FILE_PATH + FILE_NAME + i + FILE_SUFFIX;
//                    Thread thread = new Thread(new FileSumThread(fileName, sumHolder));
//                    thread.start();
//                    System.out.println(thread.getName() + " started");
//                    System.out.println(thread.getState());
////            thread.join();
//                    System.out.println(thread.getState());
//                }
//            }
//        });
//        var end = LocalDateTime.now();
//        System.out.println("Multithreading solution = " + Duration.between(start, end).toMillis() + " ms");
//        System.out.println("Sum = " + sumHolder.getTotalSum());
        SumHolder sumHolder = new SumHolder();
        List<Future<Long>> list = new ArrayList<>();
        var start = LocalDateTime.now();
        ExecutorService executorService = Executors.newFixedThreadPool(FILES_COUNT);
        for (int i = 0; i < FILES_COUNT; i++) {
            int finalI = i;
            Future<Long> future = executorService.submit(() -> {
                        long sum = 0;
                        String fileName = FILE_PATH + FILE_NAME + finalI + FILE_SUFFIX;
                        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                int number = Integer.parseInt(line);
                                sum += number;
                            }
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        return sum;
                    }
            );
            list.add(future);
        }
        executorService.shutdown();
        list.forEach(f -> {
            try {
                sumHolder.addToTotalSum(f.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        var end = LocalDateTime.now();
        System.out.println("Multithreading solution = " + Duration.between(start, end).toMillis() + " ms");
        System.out.println("Sum = " + sumHolder.getTotalSum());
    }

    public void syncSumCount() {
        int sum = 0;
        var start = LocalDateTime.now();
        for (int i = 0; i < FILES_COUNT; i++) {
            String filename = FILE_PATH + FILE_NAME + i + FILE_SUFFIX;
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    int number = Integer.parseInt(line);
                    sum += number;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        var end = LocalDateTime.now();
        System.out.println("1 thread solution = " + Duration.between(start, end).toMillis() + " ms");
        System.out.println("sum = " + sum);
    }
}
