package basic_01.chapter_11.lambda;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPI {
    /**
     *  Stream 的创建
     *      1 第一种方式通过 Collection 系列集合 提供的方法 stream 或者 parallelStream
     *      2 第二种方式由 数组 创建流
     *      3 第三种方式由 值 创建流
     *      4 第四种方式由 函数 创建流创建无限流
     *
     *  Stream 的中间操作
     *  1, 筛选与切片
     *      filter(Predicate p)	      接收 Lambda ， 从流中排除某些元素。
     *      distinct()	              筛选，通过流所生成元素的 hashCode() 和 equals() 去 除重复元素
     *      limit(long maxSize)	      截断流，使其元素不超过给定数量
     *      skip(long n)	          跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素 不足 n 个，则返回一个空流。与 limit(n) 互补
     *
     *  2, 映射
     *      map(Function f)	                接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
     *      mapToDouble(ToDoubleFunction f)	接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 DoubleStream。
     *      mapToInt(ToIntFunction f)	    接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 IntStream。
     *      mapToLong(ToLongFunction f)	    接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 LongStream。
     *      flatMap(Function f)	            接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     *  3，排序
     *      sorted()	                产生一个新流，其中按自然顺序排序
     *      sorted(Comparator comp)	    产生一个新流，其中按比较器顺序排序
     *
     *  Stream 的终止操作
     *  终止操作会从流的流水线生成结果。其结果可以是任何不是流的值，例如:List、Integer，甚至是 void 。
     *  在这些终止操作中，注意两点：终止操作的入参，以及最终的返回值。
     *  1，查找与匹配
     *      allMatch(Predicate p)	    检查是否匹配所有元素
     *      anyMatch(Predicate p)	    检查是否至少匹配一个元素
     *      noneMatch(Predicate p)	    检查是否没有匹配所有元素
     *      findFirst()	                返回第一个元素
     *      findAny()	                返回当前流中的任意元素
     *      count()	                    返回流中元素总数
     *      max(Comparator c)	        返回流中最大值
     *      min(Comparator c)	        返回流中最小值
     *      forEach(Consumer c)	        内部迭代(使用 Collection 接口需要用户去做迭 代，称为外部迭代。
     *                                  相反，Stream API 使用内部 迭代——它帮你把迭代做了)
     *
     *  2，归约
     *      reduce(T iden, BinaryOperator b)	可以将流中元素反复结合起来，得到一个值。 返回 T
     *      reduce(BinaryOperator b)	        可以将流中元素反复结合起来，得到一个值。 返回 Optional< T>
     *
     *  3，收集
     *      collect(Collector c)	  将流转换为其他形式。接收一个 Collector接口的 实现，用于给Stream中元素做汇总的方法
     *
     */

    @Test
    public void test() {
        // 第一种方式通过 Collection 系列集合 提供的方法 stream 或者 parallelStream (最常用)
        List<Employee> list = new ArrayList<>();
        Stream<Employee> stream = list.stream();
        Stream<Employee> parallelStream = list.parallelStream();

        // 第二种方式由 数组 创建流
        Integer[] num = new Integer[23];
        Stream<Integer> stream1 = Arrays.stream(num);

        // 第三种方式由 值 创建流
        Stream<Integer> stream2 = Stream.of(1, 5, 7);

        // 第四种方式由 函数 创建流创建无限流
        // 迭代  limit是为了限制次数
        Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2).limit(2);
        stream3.forEach(System.out::println);
        System.out.println("-------------");
        // 生成
        Stream<Double> stream4 = Stream.generate(Math::random).limit(4);
        stream4.forEach(System.out::println);
    }

    @Test
    public void test2() {
        // 1, 筛选与切片

        List<Employee> emps = Arrays.asList(
                new Employee(101, "林青霞", 28, 9889.99),
                new Employee(102, "东方不败", 29, 4329.85),
                new Employee(103, "周星驰", 40, 1233.88),
                new Employee(104, "大圣", 500, 5000.44),
                new Employee(105, "张无忌", 15, 3000.09),
                new Employee(102, "东方不败", 29, 4329.85)
        );


        System.out.println("==========filter===============");
        // TODO filter 筛选，去除某些元素
        // 参数类型 Predicate 断言（java8 四大内置函数接口之一）
        // 抽象方法 boolean test(T t);  传入一个参数，返回boolean类型
        emps.stream()
                .filter( (employee) -> {
                    if (employee.getAge() > 40) {
                        return true;
                    }
                    return false;
                })
                .forEach(System.out::println);

        // 进一步简化
        emps.stream()
                .filter( (employee) -> employee.getAge() > 40)
                .forEach(System.out::println);


        System.out.println("===========distinct==============");
        // TODO distinct 去重 没有参数，内部使用 hashCode() 和 equals() 去重，如果是自定义类型，需要重写
        // 在Employee类上 加上lombok注解 @EqualsAndHashCode
        emps.stream()
                .distinct()
                .forEach(System.out::println);


        System.out.println("===========limit==============");
        // TODO limit 截断流
        emps.stream()
                .limit(1)
                .forEach(System.out::println);


        System.out.println("=============skip=============");
        // TODO 跳过 前几个元素
        emps.stream()
                .skip(1)
                .forEach(System.out::println);

        //==========filter===============
        //Employee(id=104, name=大圣, age=500, salary=5000.44)
        //Employee(id=104, name=大圣, age=500, salary=5000.44)
        //===========distinct==============
        //Employee(id=101, name=林青霞, age=28, salary=9889.99)
        //Employee(id=102, name=东方不败, age=29, salary=4329.85)
        //Employee(id=103, name=周星驰, age=40, salary=1233.88)
        //Employee(id=104, name=大圣, age=500, salary=5000.44)
        //Employee(id=105, name=张无忌, age=15, salary=3000.09)
        //===========limit==============
        //Employee(id=101, name=林青霞, age=28, salary=9889.99)
        //=============skip=============
        //Employee(id=102, name=东方不败, age=29, salary=4329.85)
        //Employee(id=103, name=周星驰, age=40, salary=1233.88)
        //Employee(id=104, name=大圣, age=500, salary=5000.44)
        //Employee(id=105, name=张无忌, age=15, salary=3000.09)
        //Employee(id=102, name=东方不败, age=29, salary=4329.85)
    }

    @Test
    public void test3() {

        // map(Function f) 映射，多用于 提取一个类中某个属性。
        // Function 接口式函数 （java8 内置四大函数式接口之一）
        // R apply(T t); 传入一个类型的参数，返回另一个类型的参数
        List<String> list = Arrays.asList("aaa", "java", "ccc", "java8", "hello world");

        list.stream()
                // 传入 String类型，返回 String ；map 返回 Stream<String>
                // 简化 ：.map(String::toUpperCase)
                .map((x) -> x.toUpperCase())
                .forEach(System.out::println);

        System.out.println("-------------");

        List<Employee> emps = Arrays.asList(
                new Employee(101, "林青霞", 28, 9889.99),
                new Employee(102, "东方不败", 29, 4329.85),
                new Employee(103, "周星驰", 40, 1233.88),
                new Employee(104, "大圣", 500, 5000.44),
                new Employee(105, "张无忌", 15, 3000.09),
                new Employee(102, "东方不败", 29, 4329.85)
        );
        emps.stream()
                // 很常用
                .map(Employee::getAge)
                .forEach(System.out::println);

    }

    @Test
    public void test4() {
        List<Employee> emps = Arrays.asList(
                new Employee(101, "林青霞", 28, 9889.99),
                new Employee(102, "东方不败", 29, 4329.85),
                new Employee(103, "周星驰", 40, 1233.88),
                new Employee(104, "大圣", 500, 5000.44),
                new Employee(105, "张无忌", 15, 3000.09),
                new Employee(102, "东方不败", 29, 4329.85)
        );

        // 排序
        emps.stream()
                .map(Employee::getSalary)
                .sorted()
                .forEach(System.out::println);

        System.out.println("-----------------");

        emps.stream()
                .map(Employee::getAge)
                .sorted(Integer::compare)
                .forEach(System.out::println);
    }

    @Test
    public void test5() {
        List<Employee> emps = Arrays.asList(
                new Employee(101, "林青霞", 28, 9889.99),
                new Employee(102, "东方不败", 29, 4329.85),
                new Employee(103, "周星驰", 40, 1233.88),
                new Employee(104, "大圣", 500, 5000.44),
                new Employee(105, "张无忌", 15, 3000.09),
                new Employee(102, "东方不败", 29, 4329.85)
        );

        boolean allMatch = emps.stream()
                .allMatch((employee -> employee.getName().equals("林青霞")));
        System.out.println(allMatch);

        System.out.println("-----------------");

        boolean anyMatch = emps.stream()
                .anyMatch(employee -> employee.getName().equals("林青霞"));
        System.out.println(anyMatch);

        System.out.println("-----------------");

        boolean noneMatch = emps.stream()
                .noneMatch(employee -> employee.getName().equals("林青霞"));
        System.out.println(noneMatch);

        // forEach(Consumer<? super T> action) 也是终止操作
        // 入参 Consumer （java8 四大内置函数式接口 之一）
        // void accept(T t); 入参一个，无返回值
        emps.forEach(System.out::println);


        Optional<String> first = emps.stream()
                .map(Employee::getName)
                .sorted()
                .findFirst(); // 获取第一个元素
        System.out.println(first.get());

        System.out.println("-----------------");

        Optional<Employee> findAny = emps.parallelStream()
                .filter(employee -> employee.getName().equals("林青霞"))
                .findAny(); //任意一个元素
        System.out.println(findAny.get());


        Stream<Employee> stream = emps.stream();
        long count = stream.count();
        System.out.println(count);

        System.out.println("-----------------");

        Optional<Double> doubleOptional = emps.stream()
                .map(Employee::getSalary)
                .max(Double::compare); //最大值
        System.out.println(doubleOptional.get());

        System.out.println("-----------------");

        Optional<Employee> employeeOptional = emps.stream()
                .min((x, y) -> Double.compare(x.getSalary(),  y.getSalary())); // 最小值
        System.out.println(employeeOptional.get());

    }

    @Test
    public void test14() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // 求和
        Integer sum = list.stream()
                // R apply(T t, U u);
                .reduce(0, (x, y) -> x + y);
        System.out.println(sum);

        List<Employee> emps = Arrays.asList(
                new Employee(101, "林青霞", 28, 9889.99),
                new Employee(102, "东方不败", 29, 4329.85),
                new Employee(103, "周星驰", 40, 1233.88),
                new Employee(104, "大圣", 500, 5000.44),
                new Employee(105, "张无忌", 15, 3000.09),
                new Employee(102, "东方不败", 29, 4329.85)
        );
        Optional<Double> doubleOptional = emps.stream()
                .map(Employee::getSalary)
                .reduce(Double::max);
        System.out.println(doubleOptional);

        System.out.println("-----------------");

    }

    @Test
    public void test16(){
        List<Employee> emps = Arrays.asList(
                new Employee(101, "林青霞", 28, 9889.99),
                new Employee(102, "东方不败", 29, 4329.85),
                new Employee(103, "周星驰", 40, 1233.88),
                new Employee(104, "大圣", 500, 5000.44),
                new Employee(105, "张无忌", 15, 3000.09),
                new Employee(102, "东方不败", 29, 4329.85)
        );

        // Collector 接口中方法的实现决定了如何对流执行收集操作(如收 集到 List、Set、Map)。
        // 但是 < Collectors > 实用类 供了很多静态方法，可以方便地创建常见收集器实例
        List<String> collect = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        collect.forEach(System.out::println);

        System.out.println("-------------------");

        Set<String> set = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        set.forEach(System.out::println);

        System.out.println("-------------------");

        HashSet<String> hashSet = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        hashSet.forEach(System.out::println);
    }

}
