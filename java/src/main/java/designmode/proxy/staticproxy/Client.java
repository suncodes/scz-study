package designmode.proxy.staticproxy;

/**
 * 客户
 *
 * @author Administrator
 */
public class Client {
    public static void main(String[] args) {
        Employee e = new TechnicalChief(new WangEmployee());
        e.work();
        e.eat();
    }
}
