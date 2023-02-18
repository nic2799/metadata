import java.util.*;
import java.util.concurrent.Semaphore;

public class ottobre2021 {
    public static void main(String[] args) throws Exception {
        LinkedList<Integer> sportello = new LinkedList<>();
        Semaphore[] turni = new Semaphore[20];
        Semaphore mutex = new Semaphore(1);
        for (int i = 0; i < 20; i++) {
            turni[i] = new Semaphore(0);

        }
        for (int i = 0; i < 20; i++) {
            sportello.add(i);
        }
        Worker[] worker = new Worker[20];
        for (int i = 0; i < 20; i++) {
            worker[i] = new Worker(turni, sportello, mutex);
            worker[i].setName("worker: " + i);
            worker[i].start();
        }

    }
}

class Worker extends Thread {
    static int ID = 0;
    int id;
    Semaphore[] turni;
    int a;
    LinkedList<Integer> sportello;
    Semaphore mutex;

    Worker(Semaphore[] turni, LinkedList<Integer> sportello, Semaphore mutex) {
        this.turni = turni;

        this.sportello = sportello;
        this.mutex = mutex;

        id = ID;
        ID++;
        if (id == 19) {
            turni[0].release();
        }

    }

    public void run() {
        try {

            mutex.acquire();

            a = sportello.removeFirst();
            System.out.println(getName() + "è il primo ");
            System.out.println("il suo turno è " + a);
            mutex.release();

            for (int i = 0; i < 2; i++) {
                turni[a].acquire();

                System.out.println(getName() + "sta lavorando");
                turni[(a + 1) % 20].release();
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
