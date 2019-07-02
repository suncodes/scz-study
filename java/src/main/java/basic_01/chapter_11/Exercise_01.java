package basic_01.chapter_11;

import java.util.List;
import java.util.function.IntToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 本单元，主要是对集合进行处理，
 * 其实就衍生出两个任务
 *
 * 1，集合操作
 * 2，lambda表达式
 *
 *  List<Gerbil> gerbils = IntStream.range(1, 10).mapToObj(Gerbil::new).collect(Collectors.toList());
 *  gerbils.forEach(Gerbil::hop);
 */

public class Exercise_01 {

    public static void main(String[] args) {
        List<Gerbil> gerbils = IntStream.range(1, 10).mapToObj(Gerbil::new).collect(Collectors.toList());
        gerbils.forEach(Gerbil::hop);

        new Thread(() -> System.out.println(Thread.currentThread().getId())).start();

        IntToDoubleFunction intToDoubleFunction = (int x) -> {
            return x + 1;
        };
    }


}

class Gerbil {
    private Integer gerbilNumber;

    public Gerbil(Integer gerbilNumber) {
        this.gerbilNumber = gerbilNumber;
    }

    public void hop() {
        System.out.println("gerbilNumber is " + gerbilNumber + ", gerbil is hopping");
    }
}