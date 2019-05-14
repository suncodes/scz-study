package designmode.proxy.dynamicproxy;

import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {
        //真实角色
        Employee employee = new WangEmployee();
        //处理器
        ChiefHandler chiefHandler = new ChiefHandler(employee);
        //代理类
        Employee proxy = (Employee) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{Employee.class},
                chiefHandler);
        System.out.println(proxy.getClass().getName());
        proxy.work();
    }
}
