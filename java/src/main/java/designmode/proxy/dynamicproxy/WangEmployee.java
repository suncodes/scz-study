package designmode.proxy.dynamicproxy;

/**
 * 姓王的员工
 */
public class WangEmployee implements Employee {

    public void eat() {
        System.out.println("wang 喜欢吃 水果");
    }

    public void work() {
        System.out.println("wang 正在做 登录项目");
    }
}