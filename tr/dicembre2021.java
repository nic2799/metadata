import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class dicembre2021 {

    public static void main(String[] args) throws Exception {
        Semaphore mutex = new Semaphore(1);
        Semaphore[] turni = new Semaphore[5];
        for (int i = 0; i < 5; i++) {
            turni[i] = new Semaphore(0);

        }
        coordinatore co = new coordinatore();
        worker[] work = new worker[5];
        for (int i = 0; i < 5; i++) {

            work[i] = new worker(mutex, turni, co);
            work[i].setName("[P " + i + "]");
            work[i].start();
            // System.out.println("il id:" + p[i].id + " " + p[i].k);

        }

    }
}

class worker extends Thread {

    Semaphore mutex;
    Semaphore[] turni;
    int a;
    coordinatore co;
    static int ID = 0;
    int id;

    worker(Semaphore mutex, Semaphore[] turni, coordinatore co) {
        this.mutex = mutex;
        this.turni = turni;
        this.co = co;

        id = ID;
        ID++;

    }

    public void run() {
        try {
            mutex.acquire();
            a = co.ordine();
            System.out.println(getName() + " ECCOMI : " + a);// cosi arrivano in maniera casuale senza semafori mentre
                                                             // con
            // semafori ordina giustamente perche c'è un unico semaforo quindi
            // entrano in start in succ e poi fanno run rosso-> diventa verde
            // quando esce in realese start va prima 0 diventa rosso poi
            // realese e diventa verdee
            // poi viene il successivo 1 e fa stessa cosa e ecc
            // se non poniamo i semafori entreranno nel run
            // in maniera casuale
            mutex.release();
        } catch (Exception e) {

        }

    }

}

class coordinatore {
    Random rand = new Random();

    coordinatore() {

    }

    int ordine() {
        return rand.nextInt(5);

    }

}