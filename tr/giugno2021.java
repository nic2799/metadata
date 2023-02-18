import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class giugno2021 {
    public static void main(String[] args) throws Exception {
        Semaphore mutex1 = new Semaphore(1);
        Semaphore mutex2 = new Semaphore(0);
        Semaphore[] turni = new Semaphore[5];
        LinkedList<Integer> depA = new LinkedList<>();
        LinkedList<Integer> depB = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            depA.add(i);
        }
        DepositoA depositoA = new DepositoA(depA);
        DepositoB depositoB = new DepositoB(depB);
        for (int i = 0; i < 2; i++) {
            turni[i] = new Semaphore(0);
        }
        LinkedList<Integer> list = new LinkedList<>();
        robot1[] robot = new robot1[5];
        for (int i = 0; i < 2; i++) {

            robot[i] = new robot1(list, mutex1, mutex2, turni, depositoA, depositoB);
            robot[i].setName("[robot " + i + "]");
            robot[i].start();
            // System.out.println("il id:" + p[i].id + " " + p[i].k);
        }

    }

}

class robot1 extends Thread {
    Semaphore mutex1, mutex2;
    Semaphore[] turni;
    static int ID = 0;
    int id;

    static int next;
    static int p = 0;
    DepositoA depA;
    DepositoB depB;
    int t;
    int s;

    // static boolean start = false;
    robot1(LinkedList list, Semaphore mutex1, Semaphore mutex2, Semaphore[] turni, DepositoA depositoA,
            DepositoB depositoB) {
        this.mutex1 = mutex1;
        this.mutex2 = mutex2;
        this.turni = turni;
        this.depA = depositoA;
        this.depB = depositoB;

        id = ID;
        ID++;
        next = ID;
        if (id == 1) {
            turni[0].release();

        }

    }

    public void run() {

        while (depA.depA.isEmpty() == false) {
            try {// inseriemao in ordine una volta generati nel cotruttore essi in generale
                 // possono entrare in disordine nel run pero ponendo l'id 0 e poi quell

                turni[id].acquire();

                t = depA.preleva();

                System.out.println("il robot legge " + getName() + " " + id + " il valore letto è:" + t);
                depB.depB.add(t);
                System.out.println("abbiamo immesso : " + depB.depB);

                turni[(id + 1) % 2].release();// quando arriva a1
                // ritorna a zero e lo rilascia

            } catch (Exception e) {

            }

            try {
                mutex1.acquire();

                mutex1.release();

            } catch (Exception e) {

            }
        }

        try {

        } catch (Exception e) {
        }

    }
}

class DepositoA {

    LinkedList<Integer> depA;

    public DepositoA(LinkedList<Integer> depA) {
        this.depA = depA;
    }

    public int preleva() {
        int a = depA.removeLast();
        return a;
    }
}

class DepositoB {
    LinkedList<Integer> depB;

    public DepositoB(LinkedList<Integer> depB) {
        this.depB = depB;
    }

    public void inserisci() {
        depB.add(1);
    }
}