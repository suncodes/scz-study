package basic_01.chapter_03;

/**
 * 显示hello word的ascii码，
 * 并排序
 */
public class Exercise_10 {
    public static void main(String[] args) {
        String s = "hello word";

        int c[] = new int[s.length()];

        for (int i = 0; i < s.length(); i++) {
            c[i] = (int) s.charAt(i);
        }
        for (int aC : c) {
            System.out.print(aC + "\t");
        }
        System.out.println();
        for (int j = 0; j < c.length; j++) {
            int temp = c[j];
            int v = j;
            for (int t = j + 1; t < c.length; t++) {
                if (c[j] > c[t]) {
                    c[j] = c[t];
                    v = t;
                }
            }
            c[v] = temp;
            System.out.println(c[j]);
        }

        for (int aC : c) {
            System.out.print((int) aC + "\t");
        }
    }

/**
 * 有点难受，全都忘了
 * 需要加油。
 */
}
