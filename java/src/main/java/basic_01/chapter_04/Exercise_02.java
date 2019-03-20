package basic_01.chapter_04;

import java.util.Random;

/**
 * 随机产生25个随机数，并比较
 */
public class Exercise_02 {
    public static void main(String[] args) {
//        int i = new Random(100).nextInt();
//        int j = new Random(100).nextInt();
        /*
        在这里有学的一个知识：
        不能用上面的方法，用上面的方法打印出来的都是i=j

         */
        Random r = new Random(100);
        int i = r.nextInt();
        int j = r.nextInt();
        for (int t = 0; t < 24; t++) {
            if (i < j) {
                System.out.println("i < j");
            } else if (i > j) {
                System.out.println("i > j");
            } else {
                System.out.println("i = j");
            }
            i = j;
            j = r.nextInt();
        }
    }
}
