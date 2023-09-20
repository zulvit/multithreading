package lab2;

import java.io.File;
import java.io.IOException;

public class Summator {
    private static final String FILE_PATH = "/lab2/";
    public static void main(String[] args) {
        File filePath = new File("Save");
        filePath.mkdir();
        File file = new File(filePath + "\\test.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createFile(int n) throws IOException {
        File directory = new File("file.txt");
        if(directory.createNewFile()){
            System.out.println(1);
        }
        for (int i = 0; i < n; i++) {
            String pathName = FILE_PATH + "file" + i + ".txt";
            System.out.println(pathName);

            File file = new File(pathName);
            System.out.println(file.createNewFile());

        }
    }
}
