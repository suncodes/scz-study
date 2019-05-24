package basic_01.chapter_10;

public class Exercise_02 {
    /**
     * （1）有关内部类的继承
     * 如果内部类有构造函数怎样做
     *
     * 1，如果内部类默认无参构造，则子类需要通过父类的外部类，进行构造，调用 外部类.super();
     *
     * 2，如果内部类有有参构造，则子类的构造参数需要两个，一个是父类的外部类，一个是父类的构造函数的参数类型，
     * 调用 外部类.super(参数);
     *
     * class C extends Exercise_02.A {
     *     C(Exercise_02 e, int i) {
     *         e.super(i);
     *     }
     * }
     *
     * class DD extends Exercise_02.D {
     *     DD(Exercise_02 e) {
     *         e.super();
     *     }
     *
     * （2）内部类会被覆盖吗？
     * 不会，
     *
     * （3）局部内部类（定义在方法内的类）和匿名内部类的区别？
     * 1，大多数可以通用
     * 2，局部内部类可以有构造方法，匿名内部类不可以
     * 3，当局部内部类继承的类的构造方法有参时，不能用匿名（主要的一点）
     * 4，一般当实现接口的时候，使用匿名内部类
     *
     */

    abstract class A {
        A(int i) {
            System.out.println(i);
        }
    }

    class B extends A {

        B(int i) {
            super(i);
        }
    }

    class D {
    }

    A f() {
        class AA extends A {
            private AA(int i) {
                super(i);
            }
        }
        return new AA(1);
    }
    A f1() {

        return new A(1){

        };
    }

    public static void main(String[] args) {
        System.out.println(new Exercise_02().f1());
    }
}


class C extends Exercise_02.A {
    C(Exercise_02 e, int i) {
        e.super(i);
    }
}

class DD extends Exercise_02.D {
    DD(Exercise_02 e) {
        e.super();
    }
}