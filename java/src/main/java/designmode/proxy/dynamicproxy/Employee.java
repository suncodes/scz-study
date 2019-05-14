package designmode.proxy.dynamicproxy;

/**
 * 抽象角色：提供代理角色和真实角色对外提供的公共方法
 */
public interface Employee {

    /**
     * 员工都需要吃饭
     */
    void eat();

    /**
     * 工作
     */
    void work();
}