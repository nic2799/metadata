import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class luglio2016 {

    public static void main(String[] args) throws Exception {
        Semaphore[] turni = new Semaphore[2];
        turni[0] = new Semaphore(0);
        turni[1] = new Semaphore(0);
        Semaphore mutex = new Semaphore(1);
        Semaphore mutex2 = new Semaphore(0);
        Semaphore mutex3 = new Semaphore(1);
        Semaphore mutex4 = new Semaphore(0);
        LinkedList<Integer> codaX = new LinkedList<>();
        A a = new A(turni, codaX, mutex, mutex2);
        B b = new B(turni, codaX, mutex, mutex2);
        C c = new C(turni, codaX, mutex, mutex2, mutex3, mutex4);
        D d = new D(turni, codaX, mutex4, mutex2, mutex3, mutex4);
        a.setName("thread-A");
        b.setName("thread-B");
        c.setName("thread-C");
        d.setName("thread-D");

        a.start();
        b.start();

        c.start();
        d.start();

    }
}

class A extends Thread {
    Semaphore[] turni;
    Semaphore mutex;
    Semaphore mutex2;
    static int ID = 0;
    int id;
    LinkedList<Integer> codaX;
    int k;

    A(Semaphore[] turni, LinkedList<Integer> codaX, Semaphore mutex, Semaphore mutex2) {
        this.turni = turni;
        this.codaX = codaX;
        this.mutex = mutex;
        this.mutex2 = mutex2;
        id = ID;
        ID++;

    }

    public void run() {
        try {
            while (k < 6) {
                mutex.acquire();
                System.out.println(getName() + " il processo A inserisce nella codaX " + k);
                mutex2.release();
                k++;
            }

        } catch (Exception e) {
        }

    }
}

class B extends Thread {
    Semaphore[] turni;
    Semaphore mutex;
    Semaphore mutex2;

    static int ID = 0;
    int id;
    LinkedList<Integer> codaX;
    int k;

    B(Semaphore[] turni, LinkedList<Integer> codaX, Semaphore mutex, Semaphore mutex2) {
        this.turni = turni;
        this.codaX = codaX;
        this.mutex = mutex;
        this.mutex2 = mutex2;

        id = ID;
        ID++;

    }

    public void run() {
        try {
            while (k < 6) {
                mutex2.acquire();
                System.out.println(getName() + " il porcesso B inserisce nella codaX " + k);
                mutex.release();
                k++;
            }

            turni[0].acquire();
            System.out.println("Leggi");

        } catch (Exception e) {
        }

    }
}

class C extends Thread {
    Semaphore[] turni;
    Semaphore mutex;
    Semaphore mutex2;
    Semaphore mutex3;
    Semaphore mutex4;
    static int ID = 0;
    int id;
    LinkedList<Integer> codaX;
    int k;

    C(Semaphore[] turni, LinkedList<Integer> codaX, Semaphore mutex, Semaphore mutex2, Semaphore mutex3,
            Semaphore mutex4) {
        this.turni = turni;
        this.codaX = codaX;
        this.mutex = mutex;
        this.mutex2 = mutex2;
        this.mutex3 = mutex3;
        this.mutex4 = mutex4;

        id = ID;
        ID++;

    }

    public void run() {
        try {
            while (k < 6) {
                mutex3.acquire();
                System.out.println(getName() + " il processo C inserisce nella codaY " + k);
                mutex4.release();
                k++;
            }
        } catch (Exception e) {
        }

    }
}

class D extends Thread {
    Semaphore[] turni;
    Semaphore mutex;
    Semaphore mutex2;
    Semaphore mutex3;
    Semaphore mutex4;
    static int ID = 0;
    int id;
    LinkedList<Integer> codaX;
    int k;

    D(Semaphore[] turni, LinkedList<Integer> codaX, Semaphore mutex, Semaphore mutex2, Semaphore mutex3,
            Semaphore mutex4) {
        this.turni = turni;
        this.codaX = codaX;
        this.mutex = mutex;
        this.mutex2 = mutex2;
        this.mutex3 = mutex3;
        this.mutex4 = mutex4;
        id = ID;
        ID++;

    }

    public void run() {
        try {
            while (k < 6) {
                mutex4.acquire();
                System.out.println(getName() + " il porcesso D inserisce nella codaY " + k);
                mutex3.release();
                k++;
            }

            turni[0].release();

        } catch (Exception e) {
        }

    }
}