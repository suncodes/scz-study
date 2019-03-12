package basic_01.chapter_02;

/**
 * 考察知识点：
 * 域变量在没有赋值的情况下的默认值。
 */
public class Exercise_01 {
    int i;
    char c;

    public static void main(String args[]) {
        Exercise_01 exercise_01 = new Exercise_01();
        System.out.println(exercise_01.i + "\t" + exercise_01.c);
    }
}
