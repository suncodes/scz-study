package basic_01.chapter_05;

import java.util.concurrent.ExecutionException;

/**
 * 可变参数
 */
public class Exercise_19 {
    Exercise_19(String... strings) {
        for (String s : strings) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        new Exercise_19("1", "2", "3");
        new Exercise_19(new String[]{"1", "2"});
    }
}
