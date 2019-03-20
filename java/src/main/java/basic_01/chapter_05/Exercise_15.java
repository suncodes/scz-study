package basic_01.chapter_05;

public class Exercise_15 {
    String s;

    {
        s = "'instance initialization'";
    }

    public Exercise_15() {
        System.out.println("Default constructor; s = " + s);
    }

    public Exercise_15(int i) {
        System.out.println("int constructor; s = " + s);
    }

    public static void main(String args[]) {
        new Exercise_15();
        new Exercise_15(1);
    }
}
