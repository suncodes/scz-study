package basic_01.chapter_07;

/**
 * fianl 类不能被继承
 * <p>
 * final 方法不能被覆盖
 * <p>
 * final 数据/参数 不能被修改
 */
final class B {
    public void f() {
        System.out.println("B:: f()");
    }

    @Override
    public String toString() {
        return "class B is final";
    }
}

//class C extends B {
//
//}
public class Exercise_22 {
    public static void main(String[] args) {
        System.out.println(new B());
    }
}
