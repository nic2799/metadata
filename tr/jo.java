import java.util.*;
import java.util.concurrent.Semaphore;

public class jo {
    public static void main(String[] args) throws Exception {
        Semaphore[] turni = new Semaphore[2];
        LinkedList<Integer> depositoA = new LinkedList<>();
        LinkedList<Integer> depositoB = new LinkedList<>();
        LinkedList<Integer> a = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            turni[i] = new Semaphore(0);
        }
        for (int i = 0; i < 10; i++) {

            depositoA.add(i);
        }

        Robot[] robot = new Robot[2];

        Semaphore mutex = new Semaphore(1);

        robot[0] = new Robot(turni, mutex, depositoA, depositoB, a);
        robot[1] = new Robot(turni, mutex, depositoA, depositoB, a);
        robot[0].setName("ROBOT " + 0);
        robot[1].setName("ROBOT " + 1);
        robot[0].start();
        robot[1].start();

    }
}

class Robot extends Thread {
    LinkedList<Integer> depositoA;
    LinkedList<Integer> depositoB;
    LinkedList<Integer> a;
    Semaphore[] turni;
    Semaphore mutex;
    static int ID = 0;
    int id;

    Robot(Semaphore[] turni, Semaphore mutex, LinkedList<Integer> depositoA, LinkedList<Integer> depositoB,
            LinkedList<Integer> a) {
        this.depositoA = depositoA;
        this.depositoB = depositoB;
        this.mutex = mutex;
        this.turni = turni;
        this.a = a;
        id = ID;
        ID++;
        if (id == 1) {
            turni[0].release();

        }

    }

    public void run() {
        try {

            System.out.println(getName() + "hel");
            mutex.acquire();

        } catch (

        Exception e) {
            // TODO: handle exception
        } /// IMPORTANTE
        try {/// invece qui entrano di nuovo da capo i due thread
            System.out.println(getName() + "hello");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
