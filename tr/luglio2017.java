import java.util.*;
import java.util.concurrent.Semaphore;;

public class luglio2017 {
    public static void main(String[] args) throws Exception {
        Semaphore[] turni = new Semaphore[4];
        Semaphore mutex = new Semaphore(1);
        Semaphore mutex2 = new Semaphore(0);
        Semaphore mutex3 = new Semaphore(1);
        turni[0] = new Semaphore(0);// B1
        turni[1] = new Semaphore(0);
        turni[2] = new Semaphore(0);
        turni[3] = new Semaphore(0);

        Nera n1 = new Nera(turni, mutex, mutex2, mutex3);
        Nera n2 = new Nera(turni, mutex, mutex2, mutex3);

        n1.setName("n1 ");
        n2.setName("n2");

        Bianca b1 = new Bianca(turni, mutex, mutex2, mutex3);
        Bianca b2 = new Bianca(turni, mutex, mutex2, mutex3);

        b2.setName("b2");
        b1.setName("b1");

        b1.start();
        b2.start();
        n1.start();
        n2.start();

    }
}

class Bianca extends Thread {
    int id;
    Semaphore[] turni;
    Semaphore mutex;
    Semaphore mutex2;
    Semaphore mutex3;
    static int ID = 0;
    static int cont = 0;

    Bianca(Semaphore[] turni, Semaphore mutex, Semaphore mutex2, Semaphore mutex3) {
        this.turni = turni;
        this.mutex = mutex;
        this.mutex2 = mutex2;
        this.mutex3 = mutex3;

        id = ID;
        ID++;
        if (id == 1) {
            turni[0].release();
        }

    }

    public void run() {

        try {
            while (true) {
                // turni[0].acquire();
                //// System.out.println("il nome " + getName());

                // turni[id].acquire();// è verde solo quello con id = 0
                if (id == 0 && cont == 0) {
                    cont++;
                    mutex.acquire();
                    System.out.println("colpisce la palla " + getName());
                    mutex.release();
                    // turni[(id + 1) % 2].release();// al den metti il numero di elementi
                } else {
                    mutex2.acquire();// non ci interessa lordine percio non usiamo turni
                    System.out.println(getName() + "colpisce la palla ");
                    mutex3.release();
                }
            }
            /// turni[0].release();

        } catch (Exception e) {

        }

    }
}

class Nera extends Thread {
    Semaphore[] turni;
    Semaphore mutex;
    Semaphore mutex2;
    Semaphore mutex3;
    static int ID = 0;
    int id;

    Nera(Semaphore[] turni, Semaphore mutex, Semaphore mutex2, Semaphore mutex3) {
        this.turni = turni;
        this.mutex = mutex;
        this.mutex2 = mutex2;
        this.mutex3 = mutex3;

        id = ID;
        id++;
        // if (id == 1) {
        // turni[2].release();
        // }

    }

    public void run() {

        try {
            while (true) {
                mutex3.acquire();
                System.out.println(getName() + "Squadra nera risponde");
                mutex2.release();
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
