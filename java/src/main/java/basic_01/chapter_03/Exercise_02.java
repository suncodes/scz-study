package basic_01.chapter_03;

/**
 * “别名机制”，就是说传入的是引用，而不是值。
 */
public class Exercise_02 {
    float f = 1;

    static void f(Exercise_02 e) {

        Exercise_02 e2 = e;
        e2.f = 3;

    }

    public static void main(String[] args) {
        Exercise_02 exercise_02_01 = new Exercise_02();
        Exercise_02 exercise_02_02;
        exercise_02_02 = exercise_02_01;

        exercise_02_02.f = 2;
        System.out.println(exercise_02_01.f);

        // 函数别名机制
        f(exercise_02_01);
        System.out.println(exercise_02_01.f);
    }
}
