import java.util.*;
import java.util.concurrent.Semaphore;;

public class luglio2022 {
    public static void main(String[] args) throws Exception {
        Semaphore mySem = new Semaphore(1);
        LinkedList<Integer> list = new LinkedList<>();
        LinkedList<Integer> list_capo = new LinkedList<>();
        Semaphore mutexg = new Semaphore(0);
        Semaphore mutex = new Semaphore(5);
        Semaphore mut = new Semaphore(0);
        impiegato[] impiegato = new impiegato[5];
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        for (int i = 0; i < 5; i++) {

            impiegato[i] = new impiegato(mySem, list, list_capo, mutex, mut, mutexg);
            impiegato[i].setName("impiegato: " + i);
            impiegato[i].start();

        }
        capo capo = new capo(list_capo, mut, list, mutex, mutexg);
        capo.setName("capo");
        capo.start();

    }
}

class impiegato extends Thread {
    Semaphore mySem;
    LinkedList<Integer> list;
    LinkedList<Integer> list_capo;
    int a;
    Semaphore mutex;
    Semaphore mutexg;
    static int conta = 0;
    static int p = 0;
    static int k = 1;
    Semaphore mut;

    impiegato(
            Semaphore mySem, LinkedList<Integer> list,
            LinkedList<Integer> list_capo, Semaphore mutex, Semaphore mut, Semaphore mutexg) {
        this.mySem = mySem;
        this.list = list;
        this.list_capo = list_capo;
        this.mutex = mutex;
        this.mut = mut;
        this.mutexg = mutexg;

    }

    public void run() {
        try {
            while (list.size() > 0) {
                // System.out.println(getName());
                int d;
                mutex.acquire();// in questo pezzo di codice ne entrano solo 5 di processi

                mySem.acquire();// da qui ne entra solo 1
                a = list.removeLast();// DIPENDE TUTTO DA CIO QUESTO FERMA IL CICLO

                // System.out.println(getName() + "stiamo prelevando ");
                list_capo.add(a);
                System.out.println(getName() + "pratica fatta messa al capo " + list_capo.size() + "  " + a);
                // conta++;
                // mySem.acquire();
                // conta++;
                // System.out.println(getName() + " " + conta);
                // mySem.release();
                if (list_capo.size() == 5) {
                    mutexg.release();
                    k++;
                    System.out.println("hello");

                }
                mySem.release();

                // mySem.release();
            }

        } catch (

        Exception e) {

        }
    }

}

class capo extends Thread {
    Semaphore mut;
    static int cont = 0;
    LinkedList<Integer> list_capo;
    LinkedList<Integer> list;
    Semaphore mutex;
    Semaphore mutexg;

    capo(LinkedList<Integer> list_capo, Semaphore mut, LinkedList<Integer> list, Semaphore mutex, Semaphore mutexg) {
        this.mut = mut;
        this.list_capo = list_capo;
        this.list = list;
        this.mutex = mutex;
        this.mutexg = mutexg;
    }

    public void run() {
        try {
            // System.out.println("firmo" + list_capo.removeLast());
            // mutexg.acquire();
            while (list_capo.size() > 0) {
                mutexg.acquire();
                // cont++;
                for (int i = 0; i < 5; i++) {
                    System.out.println("firmo" + list_capo.removeLast());

                    if (list.size() == 5) {
                        mutex.release(5);
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}