package lab2;

public class MainSum {
    private static final String FILE_PATH = "Lab2_files";
    private static final int FILES_COUNT = 107;
    private static final int STR_COUNT = 10000000;
    private static final String FILE_NAME = "/test";
    private static final String FILE_SUFFIX = ".txt";
    private static final boolean NEED_GENERATION = true;
    private static final Summator summator = new Summator(FILE_PATH, FILES_COUNT, FILE_NAME, FILE_SUFFIX);

    public static void main(String[] args) throws InterruptedException {
        if (NEED_GENERATION) {
            generateFiles();
        }
        System.out.println("Starting async solution");
        asyncSumCount();
        System.out.println("Starting solution in 1 thread");
        syncSumCount();
    }

    public static void generateFiles() throws InterruptedException {
        FileGenerator fileGenerator = new FileGenerator(FILES_COUNT, FILE_NAME, FILE_PATH, FILE_SUFFIX, STR_COUNT);
        fileGenerator.generateFiles();
    }

    public static void asyncSumCount() {
        summator.asyncSumCount();
    }

    public static void syncSumCount() {
        summator.syncSumCount();
    }
}
