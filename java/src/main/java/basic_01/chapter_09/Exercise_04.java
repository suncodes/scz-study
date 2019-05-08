package basic_01.chapter_09;

/**
 * @author Administrator
 */
public class Exercise_04 {

    /**
     * 1,多重继承，继承类，实现接口
     * 2，如果父类和接口中出现同一个方法，会怎样？
     *
     *
     * 说明：
     * 1，如果在多继承的时候，方法一致，返回值，参数一致，没有问题
     * 2，如果，参数不一致，ok
     * 3，TODO 如果参数一致，返回值不一致，不ok，直接编译报错
     *
     *
     */


    public static void main(String[] args) {
//        new Father().say();
//        new Son().say();
//        Person person = new Son();
//        person.say();
//        Father father = new Son();
//        father.say();
    }
}

class Father {
    void say() {
        System.out.println("Father say ...");
    }
}

interface Person {
    int say();
}

interface Person2 {
    void say();
}
//class Son extends Father implements Person {
//
//
////    public void say() {
////        System.out.println("Son say ...");
////    }
//    @Override
//    public int say() {
//        System.out.println("Son say ...");
//        return 0;
//    }
//}
//
//class D implements Person , Person2 {
//
//    public int say() {
//        return 0;
//    }
//}