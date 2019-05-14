package designmode.proxy.staticproxy;

/**
 * 技术总监
 */
public class TechnicalChief implements Employee {

    private Employee employee;

    public TechnicalChief(Employee e) {
        employee = e;
    }
    public void eat() {
        System.out.println("技术总监 喜欢吃 米饭");
    }

    public void work() {
        System.out.println("技术总监需要和别人谈合作项目");
        employee.work();
        System.out.println("实现之后需要联调测试");
    }
}