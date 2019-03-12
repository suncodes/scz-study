package basic_01.chapter_02;

/**
 * static变量只有一个实例
 */
public class Exercise_08 {
    static int i = 0;

    public static void main(String[] args) {
        Exercise_08 exercise_08_1 = new Exercise_08();
        Exercise_08 exercise_08_2 = new Exercise_08();
        Exercise_08 exercise_08_3 = new Exercise_08();

        exercise_08_1.i = 1;
        exercise_08_2.i = 2;
        exercise_08_3.i = 3;

        System.out.println(exercise_08_1.i);
        System.out.println(exercise_08_2.i);
        System.out.println(exercise_08_3.i);

    }

}
