import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class giugno2018 {

    public static void main(String[] args) throws Exception {
        Semaphore mutex1 = new Semaphore(1);
        Semaphore mutex2 = new Semaphore(0);
        Semaphore[] turni = new Semaphore[5];
        LinkedList<Integer> dep = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            dep.add(i);
        }
        Depo deposito = new Depo(dep);

        for (int i = 0; i < 3; i++) {
            turni[i] = new Semaphore(0);
        }

        P[] p = new P[3];
        reset res = new reset(deposito);
        for (int i = 0; i < 3; i++) {

            p[i] = new P(mutex1, mutex2, turni, deposito, res);
            p[i].setName("[P " + i + "]");
            p[i].start();
            // System.out.println("il id:" + p[i].id + " " + p[i].k);
        }

    }

}

class P extends Thread {
    Semaphore mutex1, mutex2;
    Semaphore[] turni;
    Depo deposito;
    reset res;

    static int ID = 0;
    int id;
    int t;
    static int p = 0;
    int d;

    P(Semaphore mutex1, Semaphore mutex2, Semaphore[] turni, Depo deposito, reset res) {
        this.mutex1 = mutex1;
        this.mutex2 = mutex2;
        this.deposito = deposito;
        this.turni = turni;
        this.res = res;
        id = ID;
        ID++;
        if (id == 2) {
            turni[0].release();
        }

    }

    public void run() {
        while (p < 9) {
            try {
                turni[id].acquire();// pero se non lo volessimo ordinato? come faccio metto semphore normal e cosi
                                    // non devi manco attivare il successivo vai a comm ven

                deposito.inserisci();
                System.out.println(getName() + "abbiamo inserito oggetto: " + deposito.dep);
                if (p == 4 || p == 9) {
                    System.out.println("estraiamo");
                    res.preleva();

                }
                turni[(id + 1) % 3].release();// appena arriva a 2 ritorna a zeroe fa realese

                p++;

            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    }
}

class reset extends Thread {
    Depo dep;
    LinkedList<Integer> list = new LinkedList<>();

    reset(Depo dep) {

        this.dep = dep;

    }

    public void preleva() {
        for (int i = 0; i < 5; i++) {
            int a = dep.dep.removeLast();
            list.add(a);
            System.out.println("abbiamo estratto gli ultimi 5 inseriti: " + a);
        }

    }

}

class Depo {
    LinkedList<Integer> dep;

    Depo(LinkedList<Integer> dep) {
        this.dep = dep;

    }

    public void inserisci() {
        dep.add(1);
    }
}