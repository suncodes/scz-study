package basic_01.chapter_07;

/**
 *  代理：就是在一个类中组合另一个类，之后重写其 部分 需要 的方法。
 * @author scz
 */
public class Exercise_11 {

    static class Cleanser{
        private String s = "Creanser";
        public void append(String a) {
            s += a;
        }
        public void dilute(){
            append("dilute()");
        }
        public void apply(){
            append("apply()");
        }
        public void scrub(){
            append("scrub()");
        }
        @Override
        public String toString(){
            return s;
        }
    }

    static class Detergent extends Cleanser{
        @Override
        public void scrub() {
            append("Detergent.scrub()");
            super.scrub();
        }
        public void foam() {
            append("foam()");
        }
    }

    static class Detergent2 {
        private Cleanser cleanser = new Cleanser();
        public void scrub() {
            cleanser.append("Detergent.scrub()");
            cleanser.scrub();
        }
        public void foam() {
            cleanser.append("foam()");
        }
    }

    public static void main(String[] args) {
        Detergent x = new Detergent();
        x.dilute();
        x.apply();
        x.scrub();
        x.foam();
        System.out.println(x);

        Detergent2 x2 = new Detergent2();
//        x2.dilute();
//        x2.apply();
        x2.scrub();
        x2.foam();
        System.out.println(x2);
    }
}
