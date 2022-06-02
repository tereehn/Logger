public class Main {

    public static void main(String args[])  //static method
    {

        FileHandler handler = new FileHandler("test");
        Logger logger = new Logger();
        logger.addHandler(handler);

    }
}
