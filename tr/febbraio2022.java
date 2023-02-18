import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class febbraio2022 {

    public static void main(String[] args) throws Exception {
        Runner[] runner = new Runner[2];
        Semaphore mutex = new Semaphore(0);
        LinkedList<Integer> list = new LinkedList<>();
        Semaphore[] turni = new Semaphore[2];
        turni[0] = new Semaphore(0);
        turni[1] = new Semaphore(0);

        for (int i = 0; i < 2; i++) {
            runner[i] = new Runner(mutex, turni, list);
            runner[i].setName("runner-" + i);
            runner[i].start();
        }
        Giudice giudice = new Giudice(turni, mutex, list);
        giudice.setName("GIUDICE ");
        giudice.start();

    }
}

class Runner extends Thread {
    int a;
    Semaphore mutex;
    Semaphore[] turni;
    static int ID = 0;
    int id;
    LinkedList<Integer> list;

    Runner(Semaphore mutex, Semaphore[] turni, LinkedList<Integer> list) {
        this.turni = turni;
        this.mutex = mutex;
        id = ID;
        ID++;
        this.list = list;
        // if (id == 1) {
        // turni[0].release();
        // }

    }

    public void run() {

        while (a < 10) {
            try {
                // list.add(id);
                turni[id].acquire();

                System.out.println(getName() + "abbiamo fatt: " + a);
                if (a == 9) {
                    System.out.println("fine" + id);
                    list.add(id);
                    a = 10;
                }

                a++;
                turni[(id + 1) % 2].release();

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        mutex.release();

    }

}

class Giudice extends Thread {
    Semaphore[] turni;
    Semaphore mutex;
    Random rand = new Random();
    // LinkedList<Integer> list = new LinkedList<>();
    int a;
    LinkedList<Integer> list;

    Giudice(Semaphore[] turni, Semaphore mutex, LinkedList<Integer> list) {
        this.turni = turni;
        this.mutex = mutex;
        this.list = list;
    }

    public void run() {
        try {
            // mutex.acquire();
            a = rand.nextInt(2);
            System.out.println("il thread iniziera con " + a);
            turni[a].release();

        } catch (Exception e) {

        }
        try {
            mutex.acquire();
            System.out.println("il vincitore è " + list + "-" + list.removeFirst());

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
