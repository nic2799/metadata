import java.util.*;
import java.util.concurrent.Semaphore;

public class tjt {
    public static void main(String[] args) throws Exception {
        int k = 20;
        Semaphore[] turni = new Semaphore[5];
        Semaphore mutex = new Semaphore(1);
        Semaphore mut = new Semaphore(5);
        LinkedList<Integer> deposito = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            deposito.add(i);
        }
        for (int i = 0; i < 5; i++) {
            turni[i] = new Semaphore(0);
        }
        T[] thread = new T[5];
        for (int i = 0; i < 2; i++) {
            thread[i] = new T(turni, deposito, mutex, mut);
            thread[i].setName("thread " + i);
            thread[i].start();

        }

    }
}

class T extends Thread {
    Semaphore[] turni;
    Semaphore mutex;

    Semaphore mut;
    LinkedList<Integer> deposito;
    static int ID = 0;
    int id;
    static int conta = 0;
    static int cont = 0;
    int a;
    int r;
    Random rand = new Random();
    int k = 20;
    static int d = 1;

    T(Semaphore[] turni, LinkedList<Integer> deposito, Semaphore mutex, Semaphore mut) {

        this.turni = turni;
        this.deposito = deposito;
        this.mutex = mutex;
        this.mut = mut;
        id = ID;
        ID++;
        if (id == 4) {
            turni[0].release();
        }

    }

    public void run() {
        try {
            for (int i = 0; i < 2; i++) {

                mutex.acquire();
                System.out.println(getName() + " conta" + conta);
                conta++;
                mutex.release();
            }
            if (conta < 5) {
                System.out.println(getName() + " aej" + conta);// dipende dall ordine in cui arrivano nel cic

            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}