package basic_01.chapter_16;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 数组和容器的区别？
 * 1-效率:数组效率高于容器
 * 2-对象:数组只会持有一种对象类型;容器可以持有多种对象类型。
 * 如果往String[]里面添加整型，编译期报错。
 * 如果忘List里面添加不同的类型，运行期报错。
 * 因为有了泛型，容器也可以在编译期报错。
 * 3-基本类型:数组可以存储基本类型，而容器不可以。但因为有自动包装机制，应当于可以存储基本类型。
 */
public class GenericArray {

    /**
     * 有关泛型数组问题
     */
    @Test
    public void test() {
        Integer[] ints = {1, 2, 3, 4, 5};
        Double[] doubles = {1.1, 2.2, 3.3, 4.4, 5.5};

        Integer[] ints2 = new ClassParameter<Integer>().f(ints);
        Double[] doubles2 = new MethodParameter().f(doubles);

        System.out.println(Arrays.toString(ints2));
        System.out.println(Arrays.toString(doubles2));
    }

    /**
     * 有关 Arrays 的操作
     */
    @Test
    public void test1() {
        // 把一个可变参数列表，变为list
        List<Integer> integers = Arrays.asList(1, 2, 3);
        System.out.println(integers);
        // OUTPUT: [1, 2, 3]

        // 通过一个数组，以及数组中存在的元素值，获取元素所在的索引
        Integer[] ints = {1, 2, 3, 4, 5};
        int i = Arrays.binarySearch(ints, 1);
        System.out.println(i);
        // OUTPUT: 0

        // 输入一个数组，以及任意长度，返回新的数组；如果长度大于输入数组的长度，用null填充
        Integer[] integers1 = Arrays.copyOf(ints, 10);
        System.out.println(Arrays.toString(integers1));
        // OUTPUT: [1, 2, 3, 4, 5, null, null, null, null, null]

        // 不想，弄了。

    }
}

class ClassParameter<T> {
    public T[] f(T[] arg) {
        return arg;
    }
}

class MethodParameter {
    public <T> T[] f(T[] arg) {
        return arg;
    }
}
