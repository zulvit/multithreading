package executor_framework;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageProcessor {
    private static final List<String> IMAGES = Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg");

    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newFixedThreadPool(5)) {

            for (String imagePath : IMAGES) {
                executor.submit(() -> {
                    String processedImage = applyFilter(imagePath);
                    saveImage(processedImage);
                });
            }

            executor.shutdown();
        }
    }

    private static String applyFilter(String imagePath) {
        return "Обработанное изображение: " + imagePath;
    }

    private static void saveImage(String image) {
        System.out.println("Сохранено: " + image);
    }
}
