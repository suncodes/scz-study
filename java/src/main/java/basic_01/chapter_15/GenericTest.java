package basic_01.chapter_15;

import org.junit.Test;
import org.omg.CORBA.OBJ_ADAPTER;

import java.util.ArrayList;
import java.util.List;

public class GenericTest {

    /**
     * 有关通配符extends的问题
     */
    @Test
    public void test() {
        // 对于 <? extends E> 来说，不能赋值，只能返回
        List<? extends Number> list = new ArrayList<>();
//        list.add(new Number() {
//            @Override
//            public int intValue() {
//                return 0;
//            }
//
//            @Override
//            public long longValue() {
//                return 0;
//            }
//
//            @Override
//            public float floatValue() {
//                return 0;
//            }
//
//            @Override
//            public double doubleValue() {
//                return 0;
//            }
//        });
        System.out.println(list.contains(1));
    }

    /**
     * super
     */
    @Test
    public void test1() {
        // 可以对其赋值，但对其返回只能是Object
        List<? super Integer> list = new ArrayList<>();
        list.add(1);
        Object object = list.get(0);
        System.out.println(object.getClass());
    }


    @Test
    public void test2() {

        // https://blog.csdn.net/qq_38244984/article/details/84729563

        // 段落一
        List a1 = new ArrayList();
        a1.add(new Object());
        a1.add(new Integer(1));
        a1.add(new String("a1"));

        Object o = a1.get(1);
        System.out.println(o);

        List<Integer> a2 = new ArrayList<>();
//        a2.add(new Object());
        a2.add(new Integer(1));
//        a2.add(new String("a1"));

        Object o1 = a2.get(0);
        System.out.println(o1);

        List<?> a3 = a2;
        a3 = a1;
        a1 = a3;
        a1.add(new Object());

    }
}
