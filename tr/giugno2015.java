import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class giugno2015 {

    public static void main(String[] args) throws Exception {
        P[] p = new P[5];
        Semaphore[] turni = new Semaphore[5];
        LinkedList<Integer> list = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            turni[i] = new Semaphore(0);

        }

        for (int i = 0; i < 5; i++) {
            p[i] = new P(turni, list);
            p[i].setName("p " + i);
            p[i].start();

        }

    }
}

class P extends Thread {
    Semaphore[] turni;
    static int ID = 0;
    int id;
    int token;
    LinkedList<Integer> list;

    P(Semaphore[] turni, LinkedList<Integer> list) {
        this.turni = turni;
        this.list = list;
        id = ID;
        ID++;
        if (id == 4) {
            turni[0].release();
            // list.add(0, 1);

        }

    }

    public void run() {
        try {

            turni[id].acquire();
            list.add(id, 1);
            if (id > 0) {
                list.remove(id - 1);
                list.add((id - 1) % 4, 0);
            }

            System.out.println(getName() + "uffa " + list);
            turni[(id + 1) % 4].release();
        } catch (Exception e) {

        }

    }

}