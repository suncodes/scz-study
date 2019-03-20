package basic_01.chapter_04;

/**
 * 打印1-100以内的素数
 *
 * 感觉挺巧妙的。尤其是对2来说。
 */
public class Exercise_04 {
    public static void main(String[] args) {
        for (int i = 2; i < 100; i++) {
            int j = 2;
            for (; j <= i; j++) {
                if (i % j == 0) {
                    break;
                }
            }
            if (i == j) {
                System.out.println("素数：" + i);
            }
        }
    }
}
