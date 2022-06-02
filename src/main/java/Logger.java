public class Logger {
    private final String inputString;
    private Handler handler; // handles writing to file

    public Logger(String inputString) { // received input string
        this.inputString = inputString;
    }

    public int add(int a, int b) {
        return a + b;
    }

    public void addHandler(Handler newHandler){
        this.handler = newHandler;
    }

    public Handler getHandler() {
        return handler;
    }
}
