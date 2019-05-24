package basic_01.chapter_10;

public class Exercise_01 {

    /**
     * 1, 普通的内部类，和外部类之间互相访问的权限问题
     * 2，静态内部类，和外部类之间互相访问的权限问题
     */

    /**
     * 1, 普通的内部类不能定义静态的变量及方法，可以访问外部类的任意变量
     * <p>
     * 2，静态内部类可以定义非静态的成员和方法，但不能访问外部类的非static的成员
     * <p>
     * 3，普通和static内部类都可以访问外部类的private成员
     * <p>
     * 4，外部类也可以访问内部类的private成员，不过访问普通内部类需要先new外部类，之后new内部类
     * 而访问静态内部类，可以直接new 静态内部类。
     * <p>
     * 5，静态内部类的初始化和外部类的初始化 互不干扰。
     * <p>
     * 6，有关在方法中定义类的，不必说，只要知道作用域不会超过方法的作用域，
     * 返回必定会返回已定义的接口类型，不会返回定义的内部类
     * <p>
     * 7，有关方法内，内部类传参问题：如果继承的类有有参构造，那么就需要传参；
     * 如果不传参要使用外部的变量，则此变量需要定义为final类型
     * A f(<b>final</b> int i) {
     *     return new A(){
     *         System.out.println(i);
     *     };
     * }
     * 如果是通过传参使用的，不需要定义final
     * A f(int i) {
     *     return new A(i){
     *         System.out.println(++i);
     *     };
     * }
     * <p>
     *
     */

    private String e1 = "private e1";
    public String e2 = "public e2";

    class A {
        private String a = "private a";
        public String b = "public b";

        private void f() {
            System.out.println("private f");
        }

        public void f1() {
            System.out.println("public f1");
        }

        public Exercise_01 f2() {
            Exercise_01 exercise_01 = new Exercise_01();
            System.out.println(exercise_01.e2);
            System.out.println(e1);
            return exercise_01;
        }
    }

    static {
        System.out.println("aaaaaaaaaaaaa");
    }

    static class B {
        static {
            System.out.println("bbbbbbbbbbbbbbb");
        }

        private String a = "private a";
        public String b = "public b";

        private void f() {
            System.out.println("private f");
        }

        public void f1() {
            System.out.println("public f1");
        }

        public Exercise_01 f2() {
            Exercise_01 exercise_01 = new Exercise_01();
            System.out.println(exercise_01.e2);
//            System.out.println(e1);
            return exercise_01;
        }


        public static void main(String[] args) {
//            Exercise_01 exercise_01 = new Exercise_01();
//            Exercise_01.A a = exercise_01.new A();
//            System.out.println(a.a);
//            System.out.println(a.b);
//            a.f2();
            System.out.println(new B().a);
        }
    }

    public static void main(String[] args) {
        new Exercise_01();
    }
}
