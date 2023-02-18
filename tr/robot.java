import java.util.*;
import java.util.concurrent.Semaphore;

public class robot {
    public static void main(String[] args) throws Exception {
        Semaphore[] turni = new Semaphore[2];
        LinkedList<Integer> depositoA = new LinkedList<>();
        LinkedList<Integer> depositoB = new LinkedList<>();
        LinkedList<Integer> a = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            turni[i] = new Semaphore(0);
        }
        for (int i = 0; i < 10; i++) {

            depositoA.add(i);
        }

        Robot[] robot = new Robot[2];

        Semaphore mutex = new Semaphore(1);

        robot[0] = new Robot(turni, mutex, depositoA, depositoB, a);
        robot[1] = new Robot(turni, mutex, depositoA, depositoB, a);
        robot[0].setName("ROBOT " + 0);
        robot[1].setName("ROBOT " + 1);
        robot[0].start();
        robot[1].start();

    }
}

class Robot extends Thread {
    LinkedList<Integer> depositoA;
    LinkedList<Integer> depositoB;
    LinkedList<Integer> a;
    Semaphore[] turni;
    Semaphore mutex;
    static int ID = 0;
    int id;

    Robot(Semaphore[] turni, Semaphore mutex, LinkedList<Integer> depositoA, LinkedList<Integer> depositoB,
            LinkedList<Integer> a) {
        this.depositoA = depositoA;
        this.depositoB = depositoB;
        this.mutex = mutex;
        this.turni = turni;
        this.a = a;
        id = ID;
        ID++;
        if (id == 1) {
            turni[0].release();

        }

    }

    public void run() {
        try {
            while (depositoA.size() > 0) {// ciclo basato sull oggetto condiviso
                mutex.acquire();

                a.add(depositoA.removeLast());
                System.out.println(getName() + "preleva dal deposito A la dimensione è " + depositoA.size());
                mutex.release();// qui un thread viene bloccato e passa solo uno passa qu<nto conta==10

            }
            // System.out.println(getName() + " eccomi");

        } catch (Exception e) {
            // TODO: handle exception
        } /// IMPORTANTE
        try {
            System.out.println(getName() + "eccoim");// quindi in generale le situzioni con il while sono o ti trovi
                                                     // sempre nel while finhce non finisce e poi ono fa niente o si
                                                     // trova nel while e poi esce fuori se citroviamo in qursta seconda
                                                     // situazione la cosa migliore da fare è fa due try e catch
                                                     // terza situazione del while è usare var non statica

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
