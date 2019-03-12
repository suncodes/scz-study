package basic_01.chapter_02;

/**
 * 自动拆箱，装箱
 */
public class Exercise_10 {
    public static void main(String[] args) {
        //part1
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        //part2
        System.out.println(c == d);//1
        System.out.println(e == f);//2
        System.out.println(c == (a + b));//3
        System.out.println(c.equals(a + b));//4
        System.out.println(g == (a + b));//5
        System.out.println(g.equals(a + b));//6

        /*
        包装类的**==**运算在不遇到算数运算的情况下不会自动拆箱
        包装类型存在缓存,如Integer在-128~127是有缓存的,具体参见8大基本类型的包装类型缓存探究
        包装类型的equals不处理转型问题
        包装类型遇到算数运算时会进行自动拆箱
        基本类型在需要包装类型时会进行自动装箱

        https://juejin.im/post/5bf4025ae51d451cf615725d

        其实就是要记住，什么时候会装箱，什么时候会拆箱。

         */
    }

}
