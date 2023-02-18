import java.util.*;
import java.util.concurrent.Semaphore;

public class exame {
    public static void main(String[] args) throws Exception {
        Semaphore mutex = new Semaphore(1);
        Semaphore[] turni = new Semaphore[5];
        Semaphore mut = new Semaphore(5);
        LinkedList<Integer> coda = new LinkedList<>();

        Produttore[] produttore = new Produttore[10];
        Consumatore[] consumatore = new Consumatore[5];

        for (int i = 0; i < 5; i++) {
            turni[i] = new Semaphore(0);
        }
        for (int i = 0; i < 10; i++) {
            produttore[i] = new Produttore(turni, mutex, mut, coda);
            produttore[i].setName("produttore " + i);
            produttore[i].start();

        }
        for (int i = 0; i < 5; i++) {
            consumatore[i] = new Consumatore(turni, mutex, mut, coda);
            consumatore[i].setName("consumatore" + i);
            consumatore[i].start();

        }

    }

}

class Produttore extends Thread {
    Semaphore[] turni;
    Semaphore mutex;
    Semaphore mut;
    LinkedList<Integer> coda;

    Produttore(Semaphore[] turni, Semaphore mutex, Semaphore mut, LinkedList<Integer> coda) {
        this.mut = mut;
        this.mutex = mutex;
        this.turni = turni;
        this.coda = coda;

    }

    public void run() {
        try {
            while (true) {
                mut.acquire();
                mutex.acquire();
                coda.addFirst(1);
                System.out.println(getName() + " inserisce");
                if (coda.size() == 5) {
                    turni[0].release();

                } else {
                    mutex.release();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}

class Consumatore extends Thread {
    Semaphore[] turni;
    Semaphore mutex;
    Semaphore mut;
    LinkedList<Integer> coda;
    static int ID = 0;
    int id;

    Consumatore(Semaphore[] turni, Semaphore mutex, Semaphore mut, LinkedList<Integer> coda) {
        this.mut = mut;
        this.mutex = mutex;
        this.turni = turni;
        this.coda = coda;
        id = ID;
        ID++;

    }

    public void run() {
        try {
            while (true) {
                turni[id].acquire();
                coda.removeLast();
                System.out.println(getName() + "rimuove");
                if (coda.size() == 0) {
                    mutex.release();
                    mut.release(5);

                } else {
                    turni[id + 1].release();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}