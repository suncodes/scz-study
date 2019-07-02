package basic_01.chapter_11.lambda;

import org.junit.Test;

public class Man {

    @Test
    void test() {
        Person person = new Person() {
            @Override
            public void say() {
                System.out.println("yayayayaayayyay");
            }
        };
    }

    @Test
    void test1() {
        Person person = () -> System.out.println("yayayayaayayyay");
    }

    static void test2() {
        System.out.println("Man::test2");
    }
}
