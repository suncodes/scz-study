package basic_01.chapter_05;

public class Exercise_14 {

    static String s1 = "Initialized at definition";
    static String s2;

    static {
        s2 = "Initialized in static block";
    }

    public static void main(String args[]) {
        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);
    }
}
