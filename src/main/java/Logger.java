public class Logger {
    private Handler handler; // handles writing to file

    public Logger() { // received input string

    }

    public void addHandler(Handler newHandler){
        this.handler = newHandler;
    }

    public Handler getHandler() {
        return handler;
    }

    public boolean addLog(String input){

        return true;
    }

}
