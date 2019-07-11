package basic_01.chapter_15;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 有关在运行时获取泛型信息
 */
public class GenericRunClassInfoTest {

    @Test
    public void testRunClassInfo() {
        List<Integer> myList = new MyList();
        System.out.println(Arrays.toString(myList.getClass().getTypeParameters()));
        System.out.println(myList.getClass().getGenericSuperclass());
//        System.out.println(Arrays.toString(genericSuperclass.getActualTypeArguments()));
    }

    @Test
    public void testRunClassInfo1() {
        List<Integer> myList1 = new MyList1<>();
        System.out.println(Arrays.toString(myList1.getClass().getTypeParameters()));
        System.out.println(myList1.getClass().getGenericSuperclass());
        ParameterizedType genericSuperclass = (ParameterizedType) myList1.getClass().getGenericSuperclass();
        System.out.println(Arrays.toString(genericSuperclass.getActualTypeArguments()));
    }

    @Test
    public void testRunClassInfo2() {
        List<Integer> myList2 = new MyList2<>();
        System.out.println(Arrays.toString(myList2.getClass().getTypeParameters()));
        System.out.println(myList2.getClass().getGenericSuperclass());
        ParameterizedType genericSuperclass = (ParameterizedType) myList2.getClass().getGenericSuperclass();
        System.out.println(Arrays.toString(genericSuperclass.getActualTypeArguments()));
    }

    @Test
    public void testRunClassInfo3() {
        List<Integer> myList3 = new ArrayList<Integer>(){};
        System.out.println(Arrays.toString(myList3.getClass().getTypeParameters()));
        ParameterizedType genericSuperclass = (ParameterizedType) myList3.getClass().getGenericSuperclass();
        System.out.println(Arrays.toString(genericSuperclass.getActualTypeArguments()));
    }

    @Test
    public void testRunClassInfo4() {
        MyClass myClass = new MyClass<Integer>();
        System.out.println(Arrays.toString(myClass.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(new Type[]{myClass.getClass().getGenericSuperclass()}));

        System.out.println(new MyList2<Integer>().getClass() == new MyList2<String>().getClass());
    }

    @Test
    public void test() {
        List<? extends Integer> list = new ArrayList<>();
    }
}

class MyList extends ArrayList {
}

class MyList1<T> extends ArrayList<T> {
}

/**
 * 在编译之后，继承的是java/util/ArrayList，
 * 字节code中是没有泛型信息的，只不过会多出一些其他描述性信息，来说明泛型信息
 * @param <T>
 */
class MyList2<T> extends ArrayList<Integer> {
}

class MyClass<Integer> {

}

