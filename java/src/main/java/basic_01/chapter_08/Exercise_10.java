package basic_01.chapter_08;

/**
 * 一个基类：方法1调用方法2
 * 一个子类：继承方法2
 * 定义一个子类，向上转型为基类，调用方法1
 * 结果：
 */

/**
 *
 *  结果：A::f1
 *        E::f2
 *  说明java尽可能使用派生类的方法
 *  只要找到能覆盖的方法，就使用子类的。
 *
 */
public class Exercise_10 extends A {

    @Override
    public void f2() {
        System.out.println("E::f2");
    }

    public static void main(String[] args) {
        A a = new Exercise_10();
        a.f1();
    }
}

class A {
    public void f1() {
        System.out.println("A::f1");
        f2();
    }

    public void f2() {
        System.out.println("A::f2");
    }
}

