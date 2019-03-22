package basic_01.chapter_06;

import basic_01.chapter_06.connection.Connection;
import basic_01.chapter_06.connection.ConnectionManager;

/**
 * 类的包访问权限，不能直接访问
 * 需要通过在同包下定义方法返回。
 */
public class Exercise_08 {
    public static void main(String args[]) {
        Connection c = ConnectionManager.getConnection();
        // 这只是定义了一个句柄，而不是创建对象。
        Connection[] c1 = new Connection[10];
//        Connection c1 = new Connection();
        while (c != null) {
            System.out.println(c);
            c.doSomething();
            c = ConnectionManager.getConnection();
        }
    }
}
