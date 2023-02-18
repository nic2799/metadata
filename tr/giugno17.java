import java.util.*;
import java.util.concurrent.Semaphore;

public class giugno17 {
    public static void main(String[] args) throws Exception {
        Semaphore[] turni = new Semaphore[5];
        Produttore[] produttore = new Produttore[5];
        Semaphore mutex = new Semaphore(1);
        LinkedList<Integer> coda = new LinkedList<>();
        Semaphore mut = new Semaphore(1);
        for (int i = 0; i < 5; i++) {
            turni[i] = new Semaphore(0);
        }

        for (int i = 0; i < 5; i++) {
            produttore[i] = new Produttore(turni, coda, mutex, mut);
            produttore[i].setName("produttore " + i);
            produttore[i].start();

        }

    }
}

class Produttore extends Thread {
    Semaphore[] turni;
    static int ID = 0;
    int id;
    LinkedList<Integer> coda;
    static int conta = 0;
    Semaphore mutex;
    Semaphore mut;

    int a;

    Produttore(Semaphore[] turni, LinkedList<Integer> coda, Semaphore mutex, Semaphore mut) {
        this.turni = turni;
        this.coda = coda;
        this.mutex = mutex;
        this.mut = mut;
        id = ID;
        ID++;

    }

    public void run() {

        try {
            mutex.acquire();
            if (conta == 0) {
                System.out.println("il primo thread ad arrivare è:" + getName());
                turni[id].release();
                conta++;
            }
            mutex.release();

            if (conta > 0) {

                // turni[id].acquire();
                while (a < 5) {
                    turni[id].acquire();
                    System.out.println(getName() + " " + a);
                    a++;
                    turni[(id + 1) % 5].release();

                }
                // turni[id].acquire();
                System.out.println("--------");
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}