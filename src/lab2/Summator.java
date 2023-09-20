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

    public static void main(String[] args) {
        generateFiles();
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
