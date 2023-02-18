import java.util.*;
import java.util.concurrent.Semaphore;;

public class luglio2018 {
    public static void main(String[] args) throws Exception {
        LinkedList<Integer> list = new LinkedList<>();
        final int n = 5;
        Semaphore[] turni = new Semaphore[n];
        Semaphore[] turn = new Semaphore[n];
        Semaphore mut = new Semaphore(0);
        Semaphore muto = new Semaphore(1);

        for (int i = 0; i < n; i++) {
            turni[i] = new Semaphore(0);
        }
        for (int i = 0; i < n; i++) {
            turn[i] = new Semaphore(0);
        }
        P[] p = new P[n];
        for (int i = 0; i < n; i++) {
            p[i] = new P(turni, list, mut, turn, muto);
            p[i].setName("p:" + i);
            p[i].start();
        }

    }
}

class P extends Thread {
    Semaphore[] turni;
    static Semaphore mutex = new Semaphore(1);
    LinkedList<Integer> list;
    Semaphore mut;
    Semaphore muto;
    static int ID = 0;
    int id;
    static int conta = 0;
    Semaphore[] turn;
    Semaphore mu = new Semaphore(1);

    static int cont = 1;
    static int o = 0;
    int c;
    int ids;
    Semaphore mySem = new Semaphore(1);
    int a;

    Semaphore next = new Semaphore(0);
    // Semaphore mut = new Semaphore(1);

    P(Semaphore[] turni, LinkedList<Integer> list, Semaphore mut, Semaphore[] turn, Semaphore muto) {
        this.list = list;
        this.turni = turni;
        this.mut = mut;
        this.turn = turn;
        this.muto = muto;

        id = ID;
        ID++;
        if (id == 4) {
            turni[0].release();
            turn[0].release();
        }

    }

    public void run() {

        try {
            turni[id].acquire();
            System.out.println(getName() + " inserisce");
            if (id == 4) {
                mut.release(5);
            }
            turni[(id + 1) % 5].release();/// vedere tuno(id%n) ///ultima traccia fatta
            // IMPORTANTE
            // System.out.println("bho " + (id + 1) % 5);// con %n fai si che turni rilascia
            // id= 0 poi 1 poi 2 poi 3 poi 4
            // poi 5 arrivato qui torna a 0 poi 1 poi 2 poi 3 poi 4 poi5 fine
            mut.acquire();
            // System.out.println("---------------");// questo pezzo di codice è separato da
            // quello precedente ovvero se
            // fai if id(== tot li riceverai solo quel thread o s fai> tot allora
            // solo thread succ perche id è una var statica)

            for (int i = 0; i < 5; i++) {/// while(conta<5)
                turn[id].acquire();
                // mutex.acquire();// passa solo semaforo con indice a ovvero con id ultimo
                System.out.println(getName() + " inserisce");
                conta++;

                if (conta == 4) {
                    a = id;
                    // System.out
                    turn[a].release();
                    conta = 0;
                    System.out.println("---------------");
                } else {
                    turn[(id + 1) % 5].release();

                }
                // mutex.release();
            }

            // conta = 0;

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}