RTTI 是在运行期识别已经在编译期就确定的类的类型。

类型信息存放在Class对象内。

为什么向上转型之后，编译器还知道这个实例真实的类型信息，就是因为把它存放在class文件内了，或者是Class对象。



反射是在运行期识别未知的类型。

```java
package basic_01.chapter_11;

/**
 * 反射所用到的一个类
 */
public class Student {

    //---------------构造方法-------------------
    //（默认的构造方法）
    Student(String str) {
        System.out.println("(默认)的构造方法 s = " + str);
    }

    //无参构造方法
    public Student() {
        System.out.println("调用了公有、无参构造方法执行了。。。");
    }

    //有一个参数的构造方法
    public Student(char name) {
        System.out.println("姓名：" + name);
    }

    //有多个参数的构造方法
    public Student(String name, int age) {
        System.out.println("姓名：" + name + "年龄：" + age);//这的执行效率有问题，以后解决。
        this.age = age;
    }

    //受保护的构造方法
    protected Student(boolean n) {
        System.out.println("受保护的构造方法 n = " + n);
    }

    //私有构造方法
    private Student(int age) {
        System.out.println("私有的构造方法   年龄：" + age);
    }

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static void show() {
        System.out.println("hehehehehehehehhe");
    }

    public static void main(String[] args) {
        System.out.println("============main==============");
    }
}
```



```java
package basic_01.chapter_11;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 反射测试类
 */
public class ReflectTest {

    @Test
    public void test() {
        // 通过反射调用 相应的方法
        try {
            // 加载
            Class<?> aClass = Class.forName("basic_01.chapter_11.Student");

            // 获取public构造函数及实例  另，getDeclaredConstructor获取所有
            Constructor<?> constructor = aClass.getConstructor(String.class, int.class);
            Object o = constructor.newInstance("11", 11);

            // 第一个参数为 方法名称， 第二个参数为 参数类型
            Method show = aClass.getMethod("setAge", Integer.class);
            show.invoke(o, 12);

            // 执行静态方法时，第一个参数可为null
            aClass.getMethod("main", String[].class).invoke(null, (Object) new String[]{"11"});
            aClass.getMethod("show").invoke(null);
            System.out.println(aClass.getMethod("getAge").invoke(o));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

