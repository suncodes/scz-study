package basic_01.chapter_07;

import java.util.Random;


/**
 *  static final 和 final 的区别？
 *
 *  static final 需要在定义的时候直接进行初始化，不然报错
 *
 *  final 可以在定义的时候，不进行初始化，在构造函数中初始化。
 *
 *  static final 属于类，多个对象的值是一样的，且只初始化一次。
 *
 *  final在每次新建对象的时候都会初始化。
 *
 */
public class Exercise_18 {

    private static Random random = new Random(10);
    private static final String SUN = String.valueOf(random.nextInt());
    private final String CHUI = String.valueOf(random.nextInt());

    public Exercise_18() {
    }

    @Override
    public String toString() {
        return "SUN = " + SUN + "\n" + "CHUI = " + CHUI;
    }

    public static void main(String[] args) {
        System.out.println(new Exercise_18());
        System.out.println(new Exercise_18());
        System.out.println(new Exercise_18());
    }
}
