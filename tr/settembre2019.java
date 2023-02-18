import java.util.*;
import java.util.concurrent.Semaphore;

public class settembre2019 {
    public static void main(String[] args) throws Exception {

        robotA[] robotA = new robotA[2];
        Semaphore mutex = new Semaphore(2);
        Semaphore mutex2 = new Semaphore(2);
        Semaphore mysem = new Semaphore(1);
        Semaphore mysem2 = new Semaphore(0);
        LinkedList<Integer> depositoA = new LinkedList<>();
        LinkedList<Integer> depositoB = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            depositoA.add(i);
        }
        robotB robotB = new robotB(depositoA, depositoB, mutex, mutex2, mysem, mysem2);
        for (int i = 0; i < 2; i++) {
            robotA[i] = new robotA(depositoA, depositoB, mutex, mutex2, mysem, mysem2);
            robotA[i].setName("rovot A " + i);

            robotA[i].start();

        }
        robotB.setName("robot B");
        robotB.start();

    }
}

class robotA extends Thread {
    LinkedList<Integer> depositoA;
    LinkedList<Integer> depositoB;
    Semaphore mutex;
    Semaphore mutex2;
    Semaphore mysem;
    Semaphore mysem2;
    int a;
    static int conta = 0;
    static int k = 1;

    robotA(LinkedList<Integer> depositoA, LinkedList<Integer> depositoB, Semaphore mutex, Semaphore mutex2,
            Semaphore mysem, Semaphore mysem2) {
        this.depositoA = depositoA;
        this.depositoB = depositoB;
        this.mutex = mutex;
        this.mutex2 = mutex2;
        this.mysem = mysem;
        this.mysem2 = mysem2;

    }

    public void run() {

        try {
            while (depositoA.size() > 0) {
                mutex.acquire();
                mysem.acquire();
                a = depositoA.removeFirst();

                depositoB.addLast(a);

                conta++;
                // a = depositoA.removeLast();

                // depositoB.addLast(a);
                System.out.println("abbiamo inserito i B" + a);
                if (conta == 2 * k) {
                    mysem2.release();
                    k++;

                }
                System.out.println(depositoB + "- " + depositoA);
                mysem.release();
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}

class robotB extends Thread {
    LinkedList<Integer> depositoA;
    LinkedList<Integer> depositoB;
    Semaphore mutex;
    Semaphore mutex2;
    Semaphore mysem;
    Semaphore mysem2;

    int a;
    static int cont = 0;

    robotB(LinkedList<Integer> depositoA, LinkedList<Integer> depositoB, Semaphore mutex, Semaphore mutex2,
            Semaphore mysem, Semaphore mysem2) {
        this.depositoA = depositoA;
        this.depositoB = depositoB;
        this.mutex = mutex;
        this.mutex2 = mutex2;
        this.mysem = mysem;
        this.mysem2 = mysem2;

    }

    public void run() {
        try {
            while (depositoB.size() > 0) {
                mysem2.acquire();

                a = depositoB.removeFirst();

                depositoA.addLast(a);

                System.out.println("abbiamo inserito i A" + a);
                mutex.release(2);
            }
            // mysem.release

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}