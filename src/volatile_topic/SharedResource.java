package volatile_topic;

public class SharedResource {
    private volatile boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void toggleFlag() {
        flag = !flag;
    }
}
