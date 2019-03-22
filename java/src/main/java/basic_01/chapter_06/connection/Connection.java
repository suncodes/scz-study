package basic_01.chapter_06.connection;

public class Connection {
    private static int counter = 0;
    private int id = counter++;

    Connection() {
    }

    @Override
    public String toString() {
        return "Connection " + id;
    }

    public void doSomething() {
    }
}
