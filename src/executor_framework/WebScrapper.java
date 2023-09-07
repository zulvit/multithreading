package executor_framework;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebScrapper {
    private static final List<String> URLs = Arrays.asList(
            "https://example.com",
            "https://example.org",
            "https://example.net");

    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(5)) {
            for (String url : URLs) {
                executorService.submit(() -> {
                    String content = fetchContentFromURL(url);
                    saveContent(content);
                });
            }
            executorService.shutdown();
        }
    }

    private static void saveContent(String content) {
        System.out.println("Данные сохранены: " + content);
    }

    private static String fetchContentFromURL(String url) {
        return url;
    }
}