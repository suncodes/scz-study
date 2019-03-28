package basic_01.chapter_07;

/**
 * 有关类加载，是否查一查资料？
 * <p>
 * 本程序 只能回答什么时候类加载？
 * 不能回答类的加载机制。
 * <p>
 * 一个类被加载：
 * 1. 创建一个实例对象
 * 2. 子类被加载
 * 3 .调用类的静态成员
 * 4 .调用main() 函数（main属于静态函数）
 * <p>
 * 主要就是两种方式：创建实例，调用静态成员，其他的就是演变出来的
 * <p>
 * 过程：
 * <p>
 * 首先找到入口类main
 * <p>
 * 之后查找main在哪个类里面（属于哪个类，main相当于这个类的静态函数，静态函数被调用，这个类自然被加载）
 * <p>
 * 之后查找这个类是否有父类，如果有加载父类（静态成员变量及代码块）
 * <p>
 * 之后加载本类的静态代码块及成员变量
 * <p>
 * 如果在本类的静态代码块中，调用了其他类的静态成员，那么加载其他类。
 * <p>
 * 全部加载完之后，往下执行main函数
 * <p>
 * 如果有创建了其他实例，进行加载静态成员以及构造函数
 * <p>
 * 如果创建了已经加载过的类，不再进行加载，直接调用构造函数。
 * <p>
 * <p>
 * 其实就是说：
 * <p>
 * 创建实例，访问静态成员
 * <p>
 * 会引起类加载
 * <p>
 * 而第一次类加载，是通过main函数入口，
 * <p>
 * 加载所在的类，以及引起一连串的加载。
 */

class D {
    private static int i = 1;

    static {
        System.out.println("D:: static{}");
        Exercise_23.k();

    }

    public static void f() {
        System.out.println("D:: static f()");
    }

    public D() {

        System.out.println("D:: D()");
    }

    public static void main(String[] args) {
        System.out.println("D::static main()");
//        Exercise_23.main(new String[1]);
//        Exercise_23.k();
//        System.out.println(Exercise_23.j);
//        new Exercise_23();
    }
}

public class Exercise_23 extends D {

    public static int j = 0;

    static {
        System.out.println("E:: static{}");
    }

    public static void k() {
        System.out.println("E:: static k()");
    }

    public Exercise_23() {
        System.out.println("E:: E()");
    }

    public static void main(String[] args) {
        System.out.println("E::static main()");
    }
}
