import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class dic21 {

    public static void main(String[] args) throws Exception {
        Semaphore mutex = new Semaphore(0);
        Semaphore[] turni = new Semaphore[5];
        for (int i = 0; i < 5; i++) {
            turni[i] = new Semaphore(0);

        }
        coordinatore coordinatore = new coordinatore(mutex, turni);
        coordinatore.setName("[Coordinatore]");
        coordinatore.start();
        worker[] work = new worker[5];

        for (int i = 0; i < 5; i++) {

            work[i] = new worker(mutex, turni);
            work[i].setName("[P " + i + "]");
            work[i].start();
            // System.out.println("il id:" + p[i].id + " " + p[i].k);

        }

    }
}

class worker extends Thread {

    Semaphore mutex;
    Semaphore[] turni;
    Random rand = new Random();
    int ran = rand.nextInt(4);
    int a;
    int s;

    static int ID = 0;
    int id;

    worker(Semaphore mutex, Semaphore[] turni) {
        this.mutex = mutex;
        this.turni = turni;

        id = ID;
        ID++;
        if (id == 4) {
            turni[ran].release();
        }

    }

    public void run() {
        try {
            while (true) {
                turni[id].acquire();// inizialmente solo per l'id=0 sara verde quindi solo il processo che avra id =
                                    // 0 il semaforo saraverde per tutti gli altri sara rosso

                System.out.println(getName() + " ECCOMI : " + id);// cosi arrivano in maniera casuale senza
                                                                  // semafori
                // mentre
                // con
                // semafori ordina giustamente perche c'è un unico semaforo quindi
                // entrano in start in succ e poi fanno run rosso-> diventa verde
                // quando esce in realese start va prima 0 diventa rosso poi
                // realese e diventa verdee
                // poi viene il successivo 1 e fa stessa cosa e ecc
                // se non poniamo i semafori entreranno nel run
                // in maniera casuale
                mutex.release();
            }

        } catch (Exception e) {

        }

    }

}

class coordinatore extends Thread {
    Random rand = new Random();
    int next = -1;
    worker work;
    Semaphore[] turni;
    Semaphore mutex;

    coordinatore(Semaphore mutex, Semaphore[] turni) {
        this.turni = turni;
        this.mutex = mutex;
        // next = rand.nextInt(4);
        // turni[next].release();

    }

    public void run() {
        try {
            while (true) {
                mutex.acquire();
                next = rand.nextInt(4);
                turni[next].release();// rilascia il semaforo con valore next ma il semaforo turni è stato
                                      // inzializzato uno per ogni id cioe fata l'operazione in worker solo quelli che
                                      // avranno quell id scelto in maniera casuale da next
                System.out.println(getName() + "il prossimo id è : " + next);
            }
        } catch (Exception e) {

        }
    }

}