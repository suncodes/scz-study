package basic_01.chapter_03;

/*
十六进制，八进制转二进制
 */
public class Exercise_08 {
    public static void main(String[] args) {
        Long l1 = 0x21L;
        Long l2 = 022L;
        System.out.println(Long.toBinaryString(l1));
        System.out.println(Long.toBinaryString(l2));
    }
}
