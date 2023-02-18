import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class gi22 {

    public static void main(String[] args) throws Exception {
        Semaphore[] turni = new Semaphore[10];

        Semaphore mutex = new Semaphore(1);
        Semaphore capo = new Semaphore(10);
        // caporeparto caporeparto = new caporeparto(turni, capo);

        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        worker[] worker = new worker[10];
        for (int i = 0; i < 10; i++) {
            turni[i] = new Semaphore(0);
        }
        for (int i = 0; i < 10; i++) {
            worker[i] = new worker(turni, list, mutex, capo);
            worker[i].setName("worker " + i);
            worker[i].start();

        }

    }
}

class worker extends Thread {

    Semaphore[] turni;
    Semaphore mutex;
    Semaphore capo;
    static int ID = 0;
    int id;
    int turno;
    LinkedList<Integer> list;

    Semaphore mysem;
    int a;

    worker(Semaphore[] turni, LinkedList<Integer> list, Semaphore mutex, Semaphore capo) {
        this.list = list;
        this.turni = turni;

        this.mutex = mutex;
        this.capo = capo;
        id = ID;
        ID++;
        if (id == 9) {
            turni[0].release();

        }

    }

    public void run() {
        try {
            capo.acquire();

            mutex.acquire();
            a = list.removeFirst();
            System.out.println("il turno di " + getName() + " è " + a + list.size());
            if (list.size() == 0) {
                capo.release(10);
            }
            mutex.release();

        } catch (Exception e) {
        }
        try {
            // System.out.println(getName() + "eccomi" + a);
            capo.acquire();
            turni[a].acquire();
            System.out.println(getName() + "eccomi" + a);
            turni[(a + 1) % 10].release();

        } catch (Exception e) {
            // TODO: handle exception
        }
        // TODO: handle exception

    }
}
