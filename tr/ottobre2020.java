import java.util.*;
import java.util.concurrent.Semaphore;

public class ottobre2020 {
    public static void main(String[] args) throws Exception {
        int k = 20;
        Semaphore[] turni = new Semaphore[5];
        Semaphore mutex = new Semaphore(0);
        Semaphore mut = new Semaphore(5);
        LinkedList<Integer> deposito = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            deposito.add(i);
        }
        for (int i = 0; i < 5; i++) {
            turni[i] = new Semaphore(0);
        }
        T[] thread = new T[5];
        for (int i = 0; i < 5; i++) {
            thread[i] = new T(turni, deposito, mutex, mut);
            thread[i].setName("thread " + i);
            thread[i].start();

        }

    }
}

class T extends Thread {
    Semaphore[] turni;
    Semaphore mutex;

    Semaphore mut;
    LinkedList<Integer> deposito;
    static int ID = 0;
    int id;
    static int conta = 0;
    static int cont = 0;
    int a;
    int r;
    Random rand = new Random();
    int k = 20;
    static int d = 1;

    T(Semaphore[] turni, LinkedList<Integer> deposito, Semaphore mutex, Semaphore mut) {

        this.turni = turni;
        this.deposito = deposito;
        this.mutex = mutex;
        this.mut = mut;
        id = ID;
        ID++;
        if (id == 4) {
            turni[0].release();
        }

    }

    public void run() {
        try {
            while (deposito.size() > 0) {// entra un thread fa tutte le op poi verifica se ciclo continua risale e si
                                         // mette in fila
                // mut.acquire();
                turni[id].acquire();// i thread continuano ad arrivare sempre e in maniera randomica non solo quando
                                    // i thread so 5 finiscono di arriva quando entranp 5 no fino a che non finisce
                                    // deposito ovviamente perche un thread una volta entrato deve fare n volt
                                    // quella operazione ora se c' è un semaforo prima del ciclo allora fa n volte
                                    // quell operazione e se ne va oppure se no risale e prende unposto in coda con
                                    // gli altri thread percio si ricreano

                cont++;

                System.out.println(getName() + "estrae token" + cont);
                deposito.removeLast();

                if (cont == 5 * d && cont < 16) {

                    r = rand.nextInt(5);
                    System.out.println("il prossimo threa" + r);
                    // mut.release(5);
                    turni[r].release();
                    d++;

                }
                if (cont != 5 * d && cont < 16) {
                    turni[(id + 1) % 5].release();
                }
                if (cont > 15) {
                    System.out.println("ultimo giro di rimozione" + getName() + " " + cont);
                    turni[(id - 1) % 5].release();
                    mut.release(5);

                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}