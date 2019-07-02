package basic_01.chapter_11.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * 一、Lambda 表达式基础语法:Java8中引入一个新的操作符"->"该操作符称为箭头操作符或Lambda操作符
 *  箭头操作符将Lambda表达式拆分为两部分:
 *   左侧: Lambda表达式的参数列表
 *   右侧: Lambda表达式中所需要执行的功能，即Lambda体
 *
 *   语法格式一:无参数，无返回值
 *      () -> System.out.println("Hello Lambada!")
 *   语法格式二:有一个参数，无返回值
 *      (x) -> System.out.println(x)
 *   语法格式三:若只有一个参数，参数的小括号可以不写
 *      x -> System.out.println(x);
 *   语法格式四:有两个以上的参数，并且Lambda体中有多条语句
 *      Comparator<Integer> com = (x,y) ->{
 *          System.out.println("函数式接口");
 *          return Integer.compare(x,y);
 *       };
 *    语法格式五:若Lambda体中只有一条语句，return和大括号都可以不写
 *          Comparator<Integer> com = (x,y) -> Integer.compare(x,y);
 *    语法格式六:Lambda表达式的参数列表的数据类型可以省略不写，因为JVM编译器可以通过上下文
 *      推断出，数据类型。即"类型推断"
 *
 *  二、Lambda 表达式需要"函数式接口"的支持
 *  函数式接口:接口中只有一个抽象方法的接口，称为函数式接口。可以使用注解@FunctionalInterface修饰
 *      可以检查是否是函数式接口
 */
public class TestLambda2 {

    @Test
    public void test1(){
        //jdk1.7前，必须是final
        int num = 0;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                //匿名内部类中num++是错的
                System.out.println("Hello world!" + num);
            }
        };

        r.run();
        System.out.println("------------------------------");
        Runnable r1 = () -> System.out.println("Hello Lambda" + num);
        r1.run();
    }

    @Test
    public void test2(){
        Consumer<String> con = x -> System.out.println(x);
        con.accept("我的瓶子");
    }

    @Test
    public void test3(){
        Comparator<Integer> com = (x,y) -> Integer.compare(x,y);
    }

    @Test
    void test4() {
       Person person = Man::test2;
       Person person1 = Man::new;
    }

}