package basic_01.chapter_04;

/**
 * 裴波那契数列
 */
public class Exercise_09 {
    public static void main(String[] args) {
//        for (int i = 1; i <= 5; i++) {
//            System.out.print(f(i) + "\t");
//        }
        f1(7);
    }

    /**
     * 递归的方法
     *
     * @param n
     * @return
     */
    static int f(int n) {
        if (n < 1) {
            System.out.println("error");
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 1;
        }
        return f(n - 2) + f(n - 1);
    }

    /**
     * 非递归的方式
     * @param n
     */
    static void f1(int n) {
        if (n < 1) {
            System.out.println("error");
            return;
        }
        if (n == 1) {
            System.out.println(1);
            return;
        }
        if (n == 2) {
            System.out.print(1 + "\t" + 1);
            return;
        }
        int i = 1;
        int j = 1;
        System.out.print(1 + "\t" + 1 + "\t");
        for (int t = 3; t <= n; t++) {
            System.out.print(i + j + "\t");
            int temp;
            temp = i + j;
            i = j;
            j = temp;
        }
    }
}
