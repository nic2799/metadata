import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class test {
    public static void main(String[] args) throws Exception {
        Semaphore mutex1 = new Semaphore(1);
        Semaphore mutex2 = new Semaphore(0);
        LinkedList<Integer> list = new LinkedList<>();
        robot1[] robot = new robot1[5];
        for (int i = 0; i < 5; i++) {

            robot[i] = new robot1(list, mutex1, mutex2);
            robot[i].setName("[robot " + i + "]");
            robot[i].start();
            // System.out.println("il id:" + p[i].id + " " + p[i].k);
        }

    }

}

class robot1 extends Thread {
    Semaphore mutex1, mutex2;

    robot1(LinkedList list, Semaphore mutex1, Semaphore mutex2) {
        this.mutex1 = mutex1;
        this.mutex2 = mutex2;

    }

    public void run() {
        // System.out.println("bho " + getName());
        try {

            mutex1.acquire();
            System.out.println("hello" + getName());
            mutex1.release();

        } catch (Exception e) {
        }

    }
}