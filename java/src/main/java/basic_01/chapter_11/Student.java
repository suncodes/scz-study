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
