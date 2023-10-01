package lab2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileGenerator {
    private final int filesCount;
    private final String filename;
    private final String filepath;
    private final String filesuffix;
    private final Random random;
    private final ExecutorService executorService;
    private final long strCount;

    public FileGenerator(int filesCount, String filename, String filepath, String filesuffix, long strCount) {
        this.filesCount = filesCount;
        this.filename = filename;
        this.filepath = filepath;
        this.filesuffix = filesuffix;
        this.executorService = Executors.newFixedThreadPool(filesCount);
        this.strCount = strCount;
        this.random = new Random();
    }

    public void generateFiles(){
        var startGenerationFiles = LocalDateTime.now();
        File file = new File(this.filepath);
        file.mkdir();
        for (int i = 0; i < filesCount; i++) {
            int finalI = i;
            Future<?> future = executorService.submit(() -> {
                String filename = filepath + this.filename + finalI + filesuffix;
                File fileToWrite = new File(filename);

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileToWrite))) {
                    for (int j = 0; j < strCount; j++) {
                        int randomNumber = random.nextInt(21) - 10;
                        bw.write(Integer.toString(randomNumber));
                        bw.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        var endGenerationFiles = LocalDateTime.now();
        System.out.println("Generated files in " + Duration.between(startGenerationFiles, endGenerationFiles).toMillis() + " ms");
    }

}
