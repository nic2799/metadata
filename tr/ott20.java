import java.util.*;
import java.util.concurrent.Semaphore;

public class ott20 {
    public static void main(String[] args) throws Exception {

        Semaphore[] turni = new Semaphore[5];
        Semaphore mut = new Semaphore(5);

        P[] p = new P[5];
        LinkedList<Integer> deposito = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            deposito.add(i);

        }
        for (int i = 0; i < 5; i++) {
            turni[i] = new Semaphore(0);

        }
        for (int i = 0; i < 5; i++) {
            p[i] = new P(turni, mut, deposito);
            p[i].setName("P " + i);
            p[i].start();

        }
    }
}

class P extends Thread {
    Semaphore[] turni;
    Semaphore mut;
    LinkedList<Integer> deposito;
    static int ID = 0;
    int id;
    int gettone;
    int a;
    Random rand = new Random();

    P(Semaphore[] turni, Semaphore mut, LinkedList<Integer> deposito) {
        this.deposito = deposito;
        this.turni = turni;
        this.mut = mut;
        id = ID;
        ID++;
        if (id == 4) {
            turni[0].release();
        }

    }

    public void run() {

        try {

            while (deposito.size() > 0) {

                mut.acquire();
                turni[id].acquire();
                gettone = deposito.removeFirst();
                System.out.println(getName() + "ho rimosso" + gettone);
                a = rand.nextInt(4);

                if (deposito.size() < 6) {
                    System.out.println("ultimo gito");
                    turni[(id + 1) % 5].release();
                    mut.release(5);
                } else {
                    turni[a].release();
                    mut.release();
                    System.out.println("il prossimo thread " + a);
                }

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}