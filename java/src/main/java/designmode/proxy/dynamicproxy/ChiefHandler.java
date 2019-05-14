package designmode.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 处理器
 */
public class ChiefHandler implements InvocationHandler {
    private Employee employee;//真实角色

    /**
     * 所有的流程控制都在invoke方法中
     * proxy：代理类
     * method：正在调用的方法
     * args：方法的参数
     */

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object = null;
        System.out.println("技术总监需要和别人谈合作项目");

        object = method.invoke(employee, args);//激活调用的方法

        System.out.println("实现之后需要联调测试");
        return object;
    }

    //通过构造器来初始化真实角色
    public ChiefHandler(Employee employee) {
        super();
        this.employee = employee;
    }
}