package basic_01.chapter_11;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Test
    @SuppressWarnings("unchecked")
    public void test1() {
        List<String> list = new ArrayList<>();
        list.add("1111");

        Class aClass = list.getClass();
        try {
            Method add = aClass.getMethod("add", Object.class);
            add.invoke(list, 100);
            System.out.println(list.size());
            // java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
            // 为什么会出现异常呢？
            // 泛型返回的不是[E]吗？已经擦除到Object了，怎样get返回的是String类型呢？
            // 这是因为编译器已经悄悄做了强制类型转换；在get返回时(String)get，只是没有显示说明。
            System.out.println(list.get(0).getClass());

            // 获取 get 方法的返回类型为Object，足以证明 编译器是会进行强制类型转换的。
            Method get = aClass.getMethod("get", int.class);
            System.out.println(get.getReturnType());

            System.out.println(Class.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        List<String> list = new ArrayList<>();
        TypeVariable<? extends Class<? extends List>>[] typeParameters = list.getClass().getTypeParameters();
        System.out.println(Arrays.toString(typeParameters));
    }

    @Test
    public void test3() {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String key = format.format(new Date());
        System.out.println(key);
    }

    @Test
    public void test4() {
        Map<String , String> a = new HashMap<>();
        String b = a.get("hehe");
        System.out.println(b);
    }

    @Test
    public void test5() {

        String format = DateFormatUtils.format(new Date(), DateFormatUtils.ISO_DATE_FORMAT.getPattern());
        System.out.println(format);
        try {
            System.out.println(DateUtils.parseDate(format, new String[]{"yyyy-MM-dd", "yyyyMMdd"}));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
