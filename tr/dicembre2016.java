import java.util.*;
import java.util.concurrent.Semaphore;

public class dicembre2016 {
    public static void main(String[] args) throws Exception {
        Semaphore[] turni = new Semaphore[5];
        Processo[] processo = new Processo[5];
        Semaphore mutex = new Semaphore(1);

        Semaphore mut = new Semaphore(10);
        for (int i = 0; i < 5; i++) {
            turni[i] = new Semaphore(0);
        }

        for (int i = 0; i < 5; i++) {
            processo[i] = new Processo(turni, mutex, mut);
            processo[i].setName("produttore " + i);
            processo[i].start();

        }

    }
}

class Processo extends Thread {
    Semaphore[] turni;
    Semaphore mut;
    Semaphore mutex; // qui non devono essre istanziati semafori

    static int ID = 0;
    int id;
    static int conta = 0;
    int myop = 2;

    Processo(Semaphore[] turni, Semaphore mutex, Semaphore mut) {

        id = ID;
        ID++;
        this.turni = turni;
        this.mut = mut;
        this.mutex = mutex;
        if (id == 4) {
            turni[0].release();
        }

    }

    public void run() {
        try {
            for (int i = 0; i < 2; i++) {
                mut.acquire();

                turni[id].acquire();
                System.out.println(getName() + "entrato in cpu");
                conta++;
                if (conta == 10) {// variabile conta che conta tutti i giri massimi che devono fa tutti in thread
                                  // alla fine ovvero ogni thread deve fare 2 giri per 5 thread ovvro in totale
                                  // fare 10 giri

                    System.out.println("fine");
                }
                turni[(id + 1) % 5].release();

            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}

class CPU extends Thread {

}