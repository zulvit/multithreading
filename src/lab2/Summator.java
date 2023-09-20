package lab2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Summator {
    private static final String FILE_PATH = "Lab2_files";
    private static final int FILES_COUNT = 17;
    private static final int STR_COUNT = 10;
    private static final String FILE_NAME = "/test";
    private static final String FILE_SUFFIX = ".txt";
    private static final boolean NEED_GENERATION = false;

    public static void main(String[] args) throws InterruptedException {
        if(NEED_GENERATION) {
            generateFiles();
        }
        SumHolder sumHolder = new SumHolder();
        for (int i = 0; i < FILES_COUNT; i++) {
            String fileName = FILE_PATH + FILE_NAME + i + FILE_SUFFIX;
            Thread thread = new Thread(new FileSumThread(fileName, sumHolder));
            thread.start();
            System.out.println(thread.getName() + " started");
            thread.join();
        }
        System.out.println(sumHolder.getTotalSum());
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
