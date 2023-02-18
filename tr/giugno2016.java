import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class giugno2016 {

    public static void main(String[] args) throws Exception {
        Semaphore mutex = new Semaphore(1);
        Semaphore[] turni = new Semaphore[5];
        LinkedList<Integer> list = new LinkedList<>();
        turno turn = new turno(list);
        for (int i = 0; i < 5; i++) {
            turni[i] = new Semaphore(0);
        }
        worker[] work = new worker[5];
        for (int i = 0; i < 5; i++) {
            work[i] = new worker(turni, list, turn, mutex);
            work[i].setName("thread " + i);
            work[i].start();
        }

    }
}

class worker extends Thread {
    Semaphore turni[];
    Semaphore mutex;
    LinkedList<Integer> list;
    turno turn;
    static int ID = 0;
    int id;
    int n = 5;
    int s;

    worker(Semaphore[] turni, LinkedList<Integer> list, turno turn, Semaphore mutex) {
        this.turni = turni;
        this.list = list;
        this.turn = turn;
        this.mutex = mutex;
        id = ID;
        ID++;
        if (id == 4) {
            turni[0].release();
        }

    }

    public void run() {

        System.out.println("il processo è" + getName() + " " + id);

        // turn.legge();
        list.add(id);
        s = turn.preleva();
        // System.out.println("stiamo prelevando " + s);

        try {
            // while (true) {
            turni[s].acquire();

            System.out.println("sono il turno " + getName() + " ");
            turni[(s + 1)].release();
            // }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}

class turno {
    LinkedList<Integer> list;

    turno(LinkedList<Integer> list) {
        this.list = list;

    }

    void legge() {
        System.out.println(list);
    }

    int preleva() {
        return list.getLast();
    }

}