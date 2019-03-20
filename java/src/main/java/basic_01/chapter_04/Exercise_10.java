package basic_01.chapter_04;

import java.util.Arrays;

/**
 * 吸血鬼数字
 * <p>
 * 1260 = 21 * 60
 * 1827 = 21 * 87
 * 2187 = 27 * 81
 */
public class Exercise_10 {

    public static void main(String[] args) {

        for (int i1 = 1; i1 < 10; i1++) {
            for (int i2 = 0; i2 < 10; i2++) {
                for (int i3 = 1; i3 < 10; i3++) {
                    for (int i4 = 0; i4 < 10; i4++) {
                        if (compare((i1 * 10 + i2) * (i3 * 10 + i4)).toString().equals(compare(i1 * 1000 + i2 * 100 + i3 * 10 + i4).toString())&&((i1 * 10 + i2)<=(i3 * 10 + i4))) {
                            System.out.println("吸血鬼数：" + (i1 * 10 + i2) + "*" + (i3 * 10 + i4) + "=" + (i1 * 10 + i2) * (i3 * 10 + i4));
                        }
                    }
                }
            }
        }
    }

    static StringBuilder compare(int i1, int i2, int i3, int i4) {

        int[] a = {i1, i2, i3, i4};
        Arrays.sort(a);
        StringBuilder s = new StringBuilder();
        for (int anA : a) {
            s.append(anA);
        }
        return s;
    }

    static StringBuilder compare(int sum) {
        int a[] = {0, 0, 0, 0};
        for (int i = 3; i >= 0; i--) {
            a[i] = sum % 10;
            sum = sum / 10;
        }
        StringBuilder s = new StringBuilder();
        Arrays.sort(a);
        for (int anA : a) {
            s.append(anA);
        }
        return s;
    }
}
