package basic_01.chapter_07;

/**
 * 在继承中，有关向上转型中，子类重新父类方法，调用父类方法，还是子类方法？？
 */
public class Exercise_17 {

    static class Amphibian {
        public void moveInWater() {
            System.out.println("Moving in Water");
        }

        public void moveOnLand() {
            System.out.println("Moving on Land");
        }
    }

    static class Frog extends Amphibian {
        @Override
        public void moveInWater() {
            System.out.println("Frog swimming");
        }

        @Override
        public void moveOnLand() {
            System.out.println("Frog jumping");
        }
        public void f(){
            System.out.println("qaaaaaaaaaaaaaaaaaaaa");
        }
    }

    public static void main(String[] args) {
        Amphibian a = new Frog();
        a.moveInWater();
        a.moveOnLand();
        //不能调用
//        a.f();

        Amphibian a1 =  new Amphibian();
//        Frog f = (Frog) a1;  // 直接报错
        if (a1 instanceof Frog) {
            Frog f = (Frog) a1;
            f.moveInWater();
            f.moveOnLand();
        }else {
            System.out.println("必须先向上转型之后，才能向下转型");
        }

        if (a instanceof Frog) {
            Frog f = (Frog) a;
            f.moveInWater();
            f.moveOnLand();
            //可以调用
            f.f();
        }

    }
}
