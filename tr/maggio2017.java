import java.util.*;
import java.util.concurrent.Semaphore;;

public class maggio2017 {
    public static void main(String[] args) throws Exception {
        Semaphore mutex = new Semaphore(10);
        Semaphore scaffaleA = new Semaphore(0);
        Semaphore scaffaleB = new Semaphore(0);
        Semaphore scaffaleC = new Semaphore(0);
        Semaphore[] turni = new Semaphore[20];
        for (int i = 0; i < 20; i++) {
            turni[i] = new Semaphore(0);

        }
        Utente[] utente = new Utente[20];
        LinkedList<Integer> sportelloA = new LinkedList<>();
        LinkedList<Integer> sportelloB = new LinkedList<>();
        LinkedList<Integer> sportelloC = new LinkedList<>();

        for (int i = 0; i < 20; i++) {
            utente[i] = new Utente(mutex, sportelloA, sportelloB, sportelloC, turni, scaffaleA, scaffaleB,
                    scaffaleC);
            utente[i].setName("utente " + i);
            utente[i].start();

        }

    }
}

class Utente extends Thread {
    Semaphore mutex;
    Semaphore scaffaleA;
    Semaphore scaffaleB;
    Semaphore scaffaleC;
    static int ID = 0;
    int id;
    LinkedList<Integer> sportelloA;
    LinkedList<Integer> sportelloB;
    LinkedList<Integer> sportelloC;
    Semaphore turni[];
    static int conta = 0;
    static int k = 1;

    Utente(Semaphore mutex, LinkedList<Integer> sportelloA, LinkedList<Integer> sportelloB,
            LinkedList<Integer> sportelloC, Semaphore[] turni, Semaphore scaffaleA, Semaphore scaffaleB,
            Semaphore scaffaleC) {
        this.mutex = mutex;

        this.sportelloA = sportelloA;
        this.sportelloB = sportelloB;
        this.sportelloC = sportelloC;

        this.scaffaleA = scaffaleA;
        this.scaffaleB = scaffaleB;
        this.scaffaleC = scaffaleC;
        this.turni = turni;
        id = ID;

        ID++;
        if (id == 20 - 1) {
            scaffaleA.release();
            turni[0].release();
        }

    }

    public void run() {
        try {
            mutex.acquire();

            turni[id].acquire();
            System.out.println("eccomi " + getName());

            if (conta == 0) {
                sportelloA.add(1);
                System.out.println("ho inserito nello sportello A");

            }
            if (conta == 1) {
                sportelloB.add(1);
                System.out.println("ho inserito nello sportello B");

            }
            if (conta == 2) {
                sportelloC.add(1);
                System.out.println("ho inserito nello sportello C");
                conta = 0;
            }
            if (id == 9 * k) {
                mutex.release(10);
                System.out.println("inizia il secondo giro");

                k++;

            }
            conta++;
            turni[(id + 1)].release();

            // qui entra solo un utente alla volta

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}