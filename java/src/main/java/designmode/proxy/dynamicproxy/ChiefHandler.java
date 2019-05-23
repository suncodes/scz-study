package designmode.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 处理器
 */
public class ChiefHandler implements InvocationHandler {

    // 把参数改为Object类型，这样就可以更具有通用性
    //这样就可以传入不同类型的参数
    private Object employee;//真实角色

    /**
     * 所有的流程控制都在invoke方法中
     * proxy：代理类
     * method：正在调用的方法
     * args：方法的参数
     */

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object = null;
        System.out.println("技术总监需要和别人谈合作项目");

        object = method.invoke(employee, args);//激活调用的方法

        System.out.println("实现之后需要联调测试");
        return object;
    }

    //通过构造器来初始化真实角色
    public ChiefHandler(Object employee) {
        super();
        this.employee = employee;
    }
}