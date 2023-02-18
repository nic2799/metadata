import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class giu22 {

    public static void main(String[] args) throws Exception {
        Semaphore[] turni = new Semaphore[10];

        Semaphore mutex = new Semaphore(1);
        Semaphore capo = new Semaphore(0);
        // caporeparto caporeparto = new caporeparto(turni, capo);

        LinkedList<Integer> list = new LinkedList<>();
        ordine ord = new ordine(list);
        ord.riordina();
        worker[] worker = new worker[10];
        for (int i = 0; i < 10; i++) {
            turni[i] = new Semaphore(0);
        }
        for (int i = 0; i < 10; i++) {
            worker[i] = new worker(turni, list, ord, mutex, capo);
            worker[i].setName("worker " + i);
            worker[i].start();

        }
        caporeparto caporeparto = new caporeparto(turni, capo);
        caporeparto.setName("capo ");
        caporeparto.start();

    }
}

class worker extends Thread {

    Semaphore[] turni;
    Semaphore mutex;
    Semaphore capo;
    static int ID = 0;
    int id;
    int turno;
    LinkedList<Integer> list;
    static int i = 0;
    ordine ord;
    Semaphore mysem;

    worker(Semaphore[] turni, LinkedList<Integer> list, ordine ord, Semaphore mutex, Semaphore capo) {
        this.list = list;
        this.turni = turni;
        this.ord = ord;
        this.mutex = mutex;
        this.capo = capo;
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
            mysem = turni[turno];
            i++;
            mutex.acquire();// senza semafori è come se ogni operazione fosse fatta dai processi in
                            // disordine in maniera casuate anche piu operazioni diverse fra lor vanno in
                            // ordine diverso//CON SEMAFORI NON è ORDINATO ARRIVA IN MANIERA CASUALE OGNI
                            // AZIONE FATTA HA ORDINE DEI THREAD CASUALE
            System.out.println("il mio turno è : " + turno + " id :  " + id + " thread: " + getName());// ogni id e get
            mutex.release(); // name sara
            // ssociato a un
            // list.add(turno, null); // turno
            turni[turno].acquire();
            System.out.println("ordine dei thread in base al turno : " + turno + " " + getName() + " id: " + id);// ok//
                                                                                                                 // cosi
                                                                                                                 // è
                                                                                                                 // ORDINATO
                                                                                                                 // NON
                                                                                                                 // BAST
                                                                                                                 // AEMPLICEMENTE
                                                                                                                 // METTERE
                                                                                                                 // DUE
                                                                                                                 // SEMAFORI
                                                                                                                 // DEVI
                                                                                                                 // FAR
                                                                                                                 // EARRAY
                                                                                                                 // DI
                                                                                                                 // SEMAFORI
                                                                                                                 // E
                                                                                                                 // SEQUENZIARE
                                                                                                                 // LA
                                                                                                                 // VARIABILE
                                                                                                                 // TURNO
                                                                                                                 // pero
            // lo devo
            // far fare
            // da una
            // classe
            // esterna

            // turni[(turno + 1) % 10].release();
            capo.release();
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
    Semaphore capo;
    int ordine = 1;

    caporeparto(Semaphore[] turni, Semaphore capo) {
        this.turni = turni;
        this.capo = capo;

    }

    public void run() {
        try {
            while (ordine < 10) {
                capo.acquire();

                System.out.println(getName() + "ho rilasciato questo turno :" + ordine);
                // ordine++;
                turni[ordine].release();
                ordine++;

            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}