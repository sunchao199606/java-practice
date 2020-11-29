package cn.com.sun.other;

/**
 * Class {@code Object} is the root of the class hierarchy.
 * Every class has {@code Object} as a superclass. All objects,
 * including arrays, implement the methods of this class
 *
 * @author ：sunchao
 * @date ：Created in 2019/11/14 18:37
 * @description：使用clone方法创建对象
 */
public class CloneDemo {
    private static class Computer implements Cloneable {
        //private float size;
        private String brand;
        private CPU cpu;
        private RAM ram;

        private Computer(String brand, CPU cpu, RAM ram) {
            this.brand = brand;
            //this.size = size;
            this.cpu = cpu;
            this.ram = ram;
        }

        @Override
        protected Computer clone() throws CloneNotSupportedException {
            // 浅拷贝
            Computer cloneComputer = (Computer) super.clone();
            // brand
            cloneComputer.cpu = (CPU) cloneComputer.cpu.clone();
            return cloneComputer;
        }
    }

    private static class CPU implements Cloneable {

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    private static class RAM {
    }

    public static void main(String[] args) {
        Computer computer = new Computer("HuaWei", new CPU(), new RAM());
        Computer computer1 = null;
        try {
            computer1 = computer.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (computer1 != null) {
            System.out.println("computer == computer1 : " + (computer == computer1));
            System.out.println("computer.cpu == computer1.cpu : " + (computer.cpu == computer1.cpu));
            System.out.println("computer.ram == computer1.ram : " + (computer.ram == computer1.ram));
            System.out.println("computer.brand == computer1.brand : " + (computer.brand == computer1.brand));
        }
    }
}
