## lambda表达式 & Stream

什么是lambda表达式？

lambda表达式的语法？

简单的lambda表达式的应用？

有关stream的概念？

stream的几种基本操作？

lambda和stream混合使用？



##  1 - 什么是lambda表达式？

在弄清什么是lambda表达式之前，你需要知道什么是匿名内部类。



首先有一个接口：

```java
public interface Person {
    /** 说 */
    void say();
}
```

如果要是用这个接口，需要实现其中的函数：

```java
public class Test {
    @Test
    void test() {
        Person person = new Person() {
            @Override
            public void say() {
                System.out.println("yayayayaayayyay");
            }
        };
    }
}
```

这就是匿名内部类的用法。



而lambda表达式的作用就是 替代匿名内部类的作用。

**`在定义 一个接口实例的时候, 可以使用匿名内部类，进行定义，但也可以使用lambda表达式进行定义，返回的是接口实例，但同时也实现了，其中需要实现的方法。`**

```java
public class Test {
    @Test
    void test1() {
        Person person = () -> System.out.println("yayayayaayayyay");
    }
}
```

这段代码和上面的代码 可以互相替换。



这就是lambda表达式。



`注意`：

如果一个接口要使用lambda表达式创建实例，则需要是 函数式接口。

也就是说，接口中只能有一个方法。

可以通过添加注解进行说明：

```java
@FunctionalInterface
```

了解了lambda表达式是干什么的，其他的就是语法内容。



## 2 - lambda表达式的语法及应用

lambda表达式的语法：

(x, y) - > {

}

等价于

f(x, y) {

}

箭头左边是 方法的参数列表，花括号里面是要实现的功能代码。



一些语法及应用：

```java

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
                System.out.println("Hello world!" + num);//匿名内部类中num++是错的
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
    
}
```



## 3- [Java8 方法引用与构造器引用，数组引用](https://www.cnblogs.com/huangyichun/p/6803969.html)

作用：进一步简化 lambda表达式的语法。

不过简化的时候会有限制，不是所有的都能简化。

简化的前提：

1，方法已经实现。

2，在lambda体中只存在这一条语句。

3，要简化的lambda或函数式接口中的抽象方法的返回值以及参数列表与已经实现的方法一致。（这也是第二条的原因）

可以用来创建对象，调用方法，创建数组。



直接上代码：

首先定义一个类：

```java
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
    private int id;
    private String name;
    private int age;
    private double salary;

    public Employee(int id) {
        this.id = id;
    }
}
```

之后test：

```java
import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;
import java.util.function.Function;

/**
 * 方法引用:若Lambda体中的内容有方法已经实现了，那么我们可以使用"方法引用"
 *  (可以理解为方法引用时Lambda表达式的另外一种表现形式
 *
 * 主要有三种语法格式:
 *
 * 对象::实例方法名
 *
 * 类::静态方法名
 *
 * 类::实例方法名
 *
 * 注意:
 *  1. Lambda体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的函数列表和返回值类型保存一致
 *  2.若Lambda参数列表中的第一个参数是实例方法的调用者，而第二个参数是实例方法的参数时，可以使用ClassName::method
 *
 *  二、构造器引用
 *  格式:
 *      ClassName::new
 *  注意:需要调用的构造器方法与函数式接口中抽象方法的参数列表保持一致
 *
 *  三、数组引用
 *  Type::new;
 */
public class TestMethodRef {

    //数组引用:
    @Test
    public void test7(){
        Function<Integer, String[]> fun = x -> new String[x];
        String[] strs = fun.apply(10);
        System.out.println(strs.length);

        Function<Integer,String[]> fun1 = String[]::new;
        strs = fun1.apply(20);
        System.out.println(strs.length);
    }


    //构造器引用
    @Test
    public void test5(){
        Supplier<Employee> sup = ()-> new Employee();
        Employee emp = sup.get();

        //构造器引用
        //根据参数列表自动匹配构造器
        Supplier<Employee> sup2 = Employee::new;
        emp = sup2.get();
        System.out.println(emp);
    }

    @Test
    public void test6(){
        Function<Integer,Employee> func = x -> new Employee(x);
        Employee emp = func.apply(10);
        System.out.println(emp);

        Function<Integer,Employee> func1 = Employee :: new;
        emp = func1.apply(10);
        System.out.println(emp);

       // BiFunction<Integer, Integer, Employee> bf = Employee::new;编译错误，没有两个Integer构造器
    }



    //对象::实例方法名
    @Test
    public void test1(){
        Consumer<String> con = x -> System.out.println(x);
        PrintStream ps =  System.out; //打印流
        //前提条件: Consumer中的方法体参数与返回值要与ps.println方法中的参数和返回值类型相同
        //Consumer： void accept(T t);在这里T为String
        //PrintStream:  public void println(String x)
        //两者传入的参数都为String,返回值都为void所以满足，可以使用方法引用
        Consumer<String> con1 = ps::println;

        Consumer<String> con2 = System.out::println;//这三种方式结果相同

        con.accept("huang");
        con1.accept("huang");
        con2.accept("huang");
    }


    @Test
    public void test2(){
        Employee emp = new Employee();
        Supplier<String> sup = () -> emp.getName();
        Supplier<String> sup2 = emp::getName;

    }

    //---------------------------------------

    //类::静态方法名
    @Test
    public void test3(){
        Comparator<Integer> com = (x, y) ->Integer.compare(x, y);
        //前提条件:和上面相同
        Comparator<Integer> com1 = Integer::compare;
    }

    //类::实例方法名
    @Test
    public void test4(){
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        boolean bool = bp.test(new String("huang"),"huang");
        System.out.println(bool);

        //前提:第一个参数是实例方法的调用者，第二个参数是实例方法的参数
        //例如 x 是equal方法的调用者，y是实例方法的参数
        BiPredicate<String,String> bp2 = String::equals;
        bool = bp2.test("huang","huang");
        System.out.println(bool);
    }

}
```



## 4 - 什么是Stream？

流(Stream) 到底是什么呢? 
是数据渠道，用于操作数据源(集合、数组等)所生成的元素序列。 
集合讲的是数据，流讲的是计算! 
注意: 
1. Stream自己不会存储元素。 
2. Stream不会改变原对象。相反，他们会返回一个持有结果的新Stream。 
3. Stream操作是延迟执行。这意味着他们会等到需要结果的时候才执行。



总的来说，就是stream是对`集合`，`数组`进行处理的一种操作。

主要和配合lambda，能够完成一些常用的 `集合` 的处理。



二、Stream操作的三个步骤
2.1 第一步：创建stream
一个数据源（如：集合或数组），获取一个流

2.2 第二步：中间操作
一个中间操作链，对数据源的数据进行处理。

2.3 第三步：终止操作（终端操作）
一个终止操作，执行中间操作链，并产生结果。



### 4-1 创建流



- 第一种方式通过 Collection 系列集合提供的方法 stream 或者 parallelStream

  Java8 中的 Collection 接口被扩展， 供了两个获取流的方法: 
  1. default Stream< E> stream() : 返回一个顺序流 
  2. default Stream< E> parallelStream() : 返回一个并行流
```JAVA
   List<Employee> list = new ArrayList<>();
   Stream<Employee> stream = list.stream();
   Stream<Employee> parallelStream = list.parallelStream();
```

- 第二种方式由数组创建流

  通过 Arrays中的静态方法 stream() 创建数据源 。 
  static < T> Stream< T> stream(T[] array): 返回一个流

  重载形式，能够处理对应基本类型的数组: 
  1. public static IntStream stream(int[] array) 
  2. public static LongStream stream(long[] array) 
  3. public static DoubleStream stream(double[] array)
 ```JAVA
 Integer[] num = new Integer[23];
 Stream<Integer> stream1 = Arrays.stream(num);
 ```

- 第三种方式由值创建流

  可以使用静态方法 Stream.of(), 通过显示值 创建一个流。它可以接收任意数量的参数。 
  public static< T> Stream< T> of(T… values) : 返回一个流

```JAVA
 Stream<Integer> stream2 = Stream.of(1, 5, 7);
```

- 第四种方式由函数创建流创建无限流

  可以使用静态方法 Stream.iterate() 和 Stream.generate(), 创建无限流。 
  1. 迭代：public static< T> Stream< T> iterate(final T seed, final UnaryOperator< T> f) 
  2. 生成：public static< T> Stream< T> generate(Supplier< T> s)
```
    // 迭代
    Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2).limit(2);
    stream3.forEach(System.out::println);

    System.out.println("-------------");

    // 生成
    Stream<Double> stream4 = Stream.generate(Math::random).limit(4);
    stream4.forEach(System.out::println);
```



## 5 - Stream 的中间操作

多个 中间操作 可以连接起来形成一个流水线，除非流水 线上触发终止操作，否则中间操作不会执行任何的处理! 而在终止操作时一次性全部处理，称为“惰性求值”。



```java
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
     *      filter(Predicate p)          接收 Lambda ， 从流中排除某些元素。
     *      distinct()               筛选，通过流所生成元素的 hashCode() 和 equals() 去 除重复元素
     *      limit(long maxSize)          截断流，使其元素不超过给定数量
     *      skip(long n)             跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素 不足 n 个，则返回一个空流。与 limit(n) 互补
     *
     *  2, 映射
     *      map(Function f)                    接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
     *      mapToDouble(ToDoubleFunction f)    接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 DoubleStream。
     *      mapToInt(ToIntFunction f)      接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 IntStream。
     *      mapToLong(ToLongFunction f)        接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 LongStream。
     *      flatMap(Function f)                接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     *  3，排序
     *      sorted()                   产生一个新流，其中按自然顺序排序
     *      sorted(Comparator comp)        产生一个新流，其中按比较器顺序排序
     *
     *  Stream 的终止操作
     *  终止操作会从流的流水线生成结果。其结果可以是任何不是流的值，例如:List、Integer，甚至是 void 。
     *  在这些终止操作中，注意两点：终止操作的入参，以及最终的返回值。
     *  1，查找与匹配
     *      allMatch(Predicate p)      检查是否匹配所有元素
     *      anyMatch(Predicate p)      检查是否至少匹配一个元素
     *      noneMatch(Predicate p)     检查是否没有匹配所有元素
     *      findFirst()                    返回第一个元素
     *      findAny()                  返回当前流中的任意元素
     *      count()                        返回流中元素总数
     *      max(Comparator c)          返回流中最大值
     *      min(Comparator c)          返回流中最小值
     *      forEach(Consumer c)            内部迭代(使用 Collection 接口需要用户去做迭 代，称为外部迭代。
     *                                  相反，Stream API 使用内部 迭代——它帮你把迭代做了)
     *
     *  2，归约
     *      reduce(T iden, BinaryOperator b)   可以将流中元素反复结合起来，得到一个值。 返回 T
     *      reduce(BinaryOperator b)           可以将流中元素反复结合起来，得到一个值。 返回 Optional< T>
     *
     *  3，收集
     *      collect(Collector c)     将流转换为其他形式。接收一个 Collector接口的 实现，用于给Stream中元素做汇总的方法
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
```



## 6- Stream 终止操作

终止操作会从流的流水线生成结果。其结果可以是任何不是流的值，例如:List、Integer，甚至是 void 。
在这些终止操作中，注意两点：终止操作的入参，以及最终的返回值。

见上面 代码



<https://blog.csdn.net/liudongdong0909/article/details/77429875>







