import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class giugno2022 {

    public static void main(String[] args) throws Exception {
        Semaphore[] turni = new Semaphore[10];

        Semaphore mutex = new Semaphore(1);
        caporeparto caporeparto = new caporeparto(turni, mutex);

        LinkedList<Integer> list = new LinkedList<>();
        ordine ord = new ordine(list);
        ord.riordina();
        worker[] worker = new worker[10];
        for (int i = 0; i < 10; i++) {
            turni[i] = new Semaphore(0);
        }
        for (int i = 0; i < 10; i++) {
            worker[i] = new worker(turni, list, ord, mutex);
            worker[i].setName("worker " + i);
            worker[i].start();

        }

    }
}

class worker extends Thread {

    Semaphore[] turni;
    Semaphore mutex;
    static int ID = 0;
    int id;
    int turno;
    LinkedList<Integer> list;
    static int i = 0;
    ordine ord;

    worker(Semaphore[] turni, LinkedList<Integer> list, ordine ord, Semaphore mutex) {
        this.list = list;
        this.turni = turni;
        this.ord = ord;
        this.mutex = mutex;
        id = ID;
        ID++;
        if (id == 9) {
            turni[0].release();

        }

    }

    public void run() {
        System.out.println("il thread che è arrivato prima è : " + getName());// se aggiugngimamo cpdice in questa fase
                                                                              // vediamo che viene tutto
        // disordinato mentre senza vengono in seq(con semaforo mutex
        // acquire e realese senza essi lo stesso disordinato)
        try {

            turno = ord.list.get(i);
            i++;
            mutex.acquire();
            System.out.println("il mio turno è : " + turno + " id :  " + id + " thread: " + getName());// ogni id e get
                                                                                                       // name sara
                                                                                                       // ssociato a un
                                                                                                       // turno

            mutex.release();

            turni[turno].acquire();
            System.out.println("ordine dei thread in base al turno : " + turno + " " + getName() + " id: " + id);// ok
                                                                                                                 // pero
            // lo devo
            // far fare
            // da una
            // classe
            // esterna

            turni[(turno + 1) % 10].release();
        } catch (Exception e) {

        }

    }
}

class ordine {
    LinkedList<Integer> list;

    ordine(LinkedList<Integer> list) {
        this.list = list;

    }

    void riordina() {
        for (int i = 0; i < 10; i++) {
            list.add(i);
            list.get(i);
        }
    }
}

class caporeparto extends Thread {
    Semaphore[] turni;
    Semaphore mutex;
    int ordine = 0;

    caporeparto(Semaphore[] turni, Semaphore mutex) {
        this.turni = turni;
        this.mutex = mutex;

    }

    public void run() {
        try {
            turni[ordine].acquire();
            System.out.println("il turno è :" + ordine);
            turni[ordine++].release();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}