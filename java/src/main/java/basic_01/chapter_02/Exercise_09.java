package basic_01.chapter_02;

/**
 * 自动装箱，拆箱功能
 *
 * 还需要再看。
 */
public class Exercise_09 {
    public static void main(String[] args) {
        int i = 1;
        int i1 = 1;
        int i2 = 257;
        Integer integer = 1;
        Integer integer1 = new Integer(257);
        Integer integer2 = new Integer(126);
        Integer integer3 = 126;


        System.out.println(i == i1);
        System.out.println(i == integer);
        System.out.println(integer.intValue() == i);
        System.out.println(i2==integer1);
        System.out.println(integer1==integer2);
        System.out.println(integer3 == integer2);
        System.out.println("===========================");

        String s = "a";
        String s1 = "a";
        String s2 = new String("a");
        System.out.println(s==s1);
        System.out.println(s==s2);;
        System.out.println(s==String.valueOf(s2));
        System.out.println(s2.equals(s));

        String s3 = "a"+"a";
        String s4 = "a"+s;
        String s5 = "aa";
        String s6 = "a"+new String("a");
        System.out.println("========================");
        System.out.println(s3==s4);
        System.out.println(s3==s5);
        System.out.println(s4==s5);;
        System.out.println(s3==s6);
    }
}
