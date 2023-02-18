import java.util.*;
import java.util.concurrent.Semaphore;

public class nov19 {
    public static void main(String[] args) throws Exception {
        Semaphore[] turni = new Semaphore[10];
        for (int i = 0; i < 10; i++) {
            turni[i] = new Semaphore(0);

        }
        LinkedList<Integer> lista = new LinkedList<>();
        P[] p = new P[10];
        for (int i = 0; i < 10; i++) {
            p[i] = new P(turni, lista);
            p[i].setName("P " + i);
            p[i].start();
        }

    }
}

class P extends Thread {
    Semaphore[] turni;
    Random rand = new Random();
    static int ID = 0;
    int id;
    static int conta = 0;
    LinkedList<Integer> lista;
    int a;
    static int k = 1;

    P(Semaphore[] turni, LinkedList<Integer> lista) {
        this.turni = turni;
        this.lista = lista;
        id = ID;
        ID++;
        if (id == 9) {
            turni[0].release();
        }

    }

    public void run() {
        try {
            while (true) {
                turni[id].acquire();
                lista.add(1);
                System.out.println(getName() + " ha inserito 1" + conta);
                if (conta == 10 * k) {

                    a = rand.nextInt(10);
                    System.out.println(getName() + " ha deciso che il prossimo sara  " + a + "e " + conta);
                    k++;
                    turni[a].release();

                } else {
                    turni[(id + 1) % 10].release();
                    conta++;
                }

            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}