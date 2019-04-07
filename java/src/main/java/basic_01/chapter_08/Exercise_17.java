package basic_01.chapter_08;

public class Exercise_17 {

    //略

    /**
     *
     * 知识点：
     *
     * 1.只有向上转型过的对象，再进行向下转型才不会出错
     *
     * 2.如果向上转型和向下转型的 类型呼应，不报错
     *
     * 3.否则异常
     *
     *
     */



    public static void main(String[] args) {
        B b = new C();
        C c = (C)b;
        //execption
        D d = (D)b;

    }
}

class B{}

class C extends B{}

class D extends B{}




