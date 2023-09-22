package lab2;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class Summator {
    private static final String FILE_PATH = "Lab2_files";
    private static final int FILES_COUNT = 107;
    private static final int STR_COUNT = 10000000;
    private static final String FILE_NAME = "/test";
    private static final String FILE_SUFFIX = ".txt";
    private static final boolean NEED_GENERATION = false;

    public static void main(String[] args) throws InterruptedException {
        if(NEED_GENERATION) {
            generateFiles();
        }
        SumHolder sumHolder = new SumHolder();
        var start = LocalDateTime.now();
        for (int i = 0; i < FILES_COUNT; i++) {
            String fileName = FILE_PATH + FILE_NAME + i + FILE_SUFFIX;
            Thread thread = new Thread(new FileSumThread(fileName, sumHolder));
            thread.start();
            System.out.println(thread.getName() + " started");
            thread.join();
        }
        var end = LocalDateTime.now();
        System.out.println("Multithreading solution = " + Duration.between(start, end).toMillis() + " ms");
        System.out.println("Sum = " + sumHolder.getTotalSum());

        System.out.println("Starting solution in 1 thread");
        int sum = 0;
        var s = LocalDateTime.now();
        for (int i = 0; i < FILES_COUNT; i++) {
            String filename = FILE_PATH + FILE_NAME + i + FILE_SUFFIX;
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
                String line;
                while ((line = reader.readLine())!=null){
                    int number = Integer.parseInt(line);
                    sum+=number;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        var e = LocalDateTime.now();
        System.out.println("1 thread silution = " + Duration.between(s, e).toMillis() + " ms");
        System.out.println("sum = " + sum);
    }

    public static void generateFiles(){
        File filepath = new File(FILE_PATH);
        filepath.mkdir();
        Random random = new Random();
        for (int i = 0; i < FILES_COUNT; i++) {
            File f = new File(filepath + FILE_NAME + i + FILE_SUFFIX);
            try {
                f.createNewFile();
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))){
                for (int j = 0; j < STR_COUNT; j++) {
                    int randomNumber = random.nextInt(21) - 10;
                    writer.write(Integer.toString(randomNumber));
                    if(j!=STR_COUNT-1) {
                        writer.newLine();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
