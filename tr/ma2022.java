import java.util.*;
import java.util.concurrent.Semaphore;;

public class ma2022 {
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
    Semaphore mutex = new Semaphore(1);
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
            // System.out.println("lllllllllll" + getName());
            turni[id].acquire();
            System.out.println(getName() + "-" + conta);
            conta++;
            turni[(id + 1) % 5].release();

            // for (int i = 0; i < 5; i++) {
            // turni[i].acquire();
            // System.out.println(getName() + "hello");
            // }
            // turni[id].acquire();
            // mutex.acquire();
            ///// VEDI SEPPUR TUTTI I THREAD ENTRANO NELLA PRIMA PARTE DI CODICE IN MANIERA
            // ORDINAAT NON SIGNIFICA CHE DEVONOARRIVA IN MANIERA ORDINATA IN QUESTA PARTE
            // DI CODICE
            System.out.println("......." + getName());// viene in successione al codice
            if (conta > 4) {// qui è legato alla part sup perche conta è statico quindi passeranno solo i
                            // thread che hanno variabile statica conta>4 poiche ogni thread avra legata
                            // quella variabile conta che si influenzeranno tra loro quando a ìrrica a conta
                            // = 4 entra nell if solo i thread che sono riusciti ad arrivare a quel vaolre
                            // infatti se togliamo if ritorna cassuale

                System.out.println(getName() + "--------------------");
            }

            // mutex.release();
            // turni[(id + 1) % 5].release();

            // .out.println(getName());

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}