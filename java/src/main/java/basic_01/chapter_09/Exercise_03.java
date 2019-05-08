package basic_01.chapter_09;

/**
 *  两个知识点;
 *  1, 类加载的准备阶段
 *  2，方法的解析过程
 *      1）对于方法的动态分派，会根据类的实际类型，
 *      2）实际类型就是指的 赋值= 右边的类型
 *
 *
 *
 */
public class Exercise_03 {
    public static void main(String args[]) {
        DerivedWithPrint dp = new DerivedWithPrint();
        dp.print1();
    }
}


abstract class BaseWithPrint {
    public BaseWithPrint() {
        print1();
    }

    public abstract void print1();
}

class DerivedWithPrint extends BaseWithPrint {
    int i = 47;

    @Override
    public void print1() {
        System.out.println("i = " + i);
    }
}