package basic_01.chapter_05;

/**
 * @author scz
 */
public class Exercise_21 {
    enum Money {
        /**
         * Create an enum of the six lowest denominations
         * of paper currency.
         */
        ONE, TWO, FIVE, TEN, TWENTY, FIFTY
    }

    public static void main(String[] args) {
        for (Money money : Money.values()) {
            System.out.println(money.name() + ":" + money.ordinal());
        }
    }
}
