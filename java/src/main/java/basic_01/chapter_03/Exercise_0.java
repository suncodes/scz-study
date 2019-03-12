package basic_01.chapter_03;

import java.util.Random;

/**
 * 模拟仍硬币的结果
 * 思路：通过随机数值，验证正反的概率。
 */
public class Exercise_0 {
    public static void main(String[] args) {
        Random random = new Random(47);
        boolean bool = random.nextBoolean();
        int i = 0, j = 0, s = 0;
        while (s < 1000000) {
            if (bool) {
                i++;
            } else {
                j++;
            }
            s++;
            bool = random.nextBoolean();
        }
        System.out.println("正面：" + (double) i / s);
        System.out.println("反面：" + (double) j / s);
    }
}
