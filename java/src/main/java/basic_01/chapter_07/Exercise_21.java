package basic_01.chapter_07;

/**
 * 对于private 不能被继承，在子类中可以定义一样的函数
 * <p>
 * 对于 private finall 不能被继承
 * <p>
 * 对于public final 可以被继承，但不允许 重写此方法，
 * <p>
 * <p>
 * 对于private方法，隐式的不能继承，在子类中重写，不会报错
 * <p>
 * 对于public final 能被继承，但显示的不能重写，重写直接报错。
 * <p>
 * 无法被覆盖final
 * <p>
 * 无法被继承private
 */
class A {
    private void f() {
        System.out.println("A::private f()");
    }

    private final void g() {
        System.out.println("A:: private final g()");
    }

    public final void k() {
        System.out.println("A:: public final k()");
    }

    /**
     * 输出：
     * A:: public final k()
     * A::private f()
     * A:: private final g()
     * <p>
     * 说明 不能被继承或继承之后，只能调用父类方法，子类不能覆盖。
     *
     * @param args
     */
    public static void main(String[] args) {
        A a = new Exercise_21();
        a.k();
        a.f();
        a.g();
    }
}

public class Exercise_21 extends A {

    private void f() {
        System.out.println("E:: f()");
    }

    private final void g() {
        System.out.println("E:: g()");
    }
//    public final void k() {
//        System.out.println("E:: k()");
//    }

    public static void main(String[] args) {

        A a = new Exercise_21();
        ((Exercise_21) a).f();
        ((Exercise_21) a).g();
        a.k();

    }
}
