import java.util.*;
import java.util.concurrent.Semaphore;;

public class marzo2022 {
    public static void main(String[] args) throws Exception {
        Semaphore[] turni = new Semaphore[5];
        thread[] t = new thread[5];
        for (int i = 0; i < 5; i++) {
            turni[i] = new Semaphore(0);
        }
        for (int i = 0; i < 5; i++) {
            t[i] = new thread(turni);
            t[i].setName("thread: " + i);
            t[i].start();
        }

    }
}

class thread extends Thread {
    Random rand = new Random();
    int k = rand.nextInt(100);
    Semaphore[] turni;
    static int ID = 0;
    int id;
    static int kMax = 0;

    static int idMax = 0;
    static int conta = 0;

    thread(Semaphore[] turni) {
        this.turni = turni;

        id = ID;
        if (id == 4) {
            turni[0].release();
        }
        ID++;

    }

    public void run() {
        try {
            turni[id].acquire();
            // System.out.println(getName());

            if (k > kMax) {

                System.out.println(
                        getName() + "il valore massimo di k è cambiato e ora è :" + k + "il vecchio valore è: " + kMax
                                + "id massimo: " + id);
                kMax = k;
                idMax = id;

            }
            conta++;
            if (conta == 4) {
                turni[idMax].release();
            }
            turni[(id + 1) % 5].release();
            // for (int i = 0; i < 5; i++) {
            // turni[i].acquire();
            // System.out.println(getName() + "hello");
            // }
            while (true) {/// qui i thread vengono ancora non è che arrivano nella parte di codic sopra
                          /// finisce finiscono il codice di sopra i 5 thread e finisce il programma pure
                          /// se sotto c'è altro codice noooo arrivano anche li sottto
                System.out.println("bho" + getName());
            }

            // .out.println(getName());

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}