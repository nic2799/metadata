import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class gennaio2016 {

    public static void main(String[] args) throws Exception {
        Semaphore[] turni = new Semaphore[4];

        int[] a = { 0, 3, 1, 2 };
        for (int i = 0; i < 4; i++) {
            turni[i] = new Semaphore(0);

        }
        P[] p = new P[4];
        for (int i = 0; i < 4; i++) {
            p[i] = new P(turni, a);
            p[i].setName("p " + i);
            p[i].start();

        }

    }

}

class P extends Thread {
    static int ID = 0;
    int id;
    Semaphore[] turni;
    Semaphore myTurn = new Semaphore(1);
    int myturn;
    int next;
    int[] a;
    static int cont = 0;

    P(Semaphore[] turni, int[] a) {

        this.turni = turni;
        this.a = a;
        id = ID;
        ID++;
        if (id == 3) {
            turni[0].release();
        }

    }

    public void run() {
        try {
            myturn = a[id];// quindi ad ogni elemento del vettore a sara assocaito un id cioe quanod passa
                           // id = 0 avremo a[i]=0 ma quando avremo id = 1 avremo a[i]=2 quindi ora quando
                           // do al semaforo la variabile myturn si creeranno 5 semafori cui ogni idavra un
                           // semaforoma associato ai valori di a e non dell' id quindi alla fine avremo 5
                           // semafori con indice myturn myturn sara associato all id perche quell indice
                           // myturn lo avremo solo quando a vremo un determinato id quindi quando passera
                           // il processo con un det id esso avra un det valore myturn che sara legato al
                           // semaforquindi se rendo un semaforo con un particolare indice verde non rendo
                           // verdeil processo con id che è l'indice ma attivo il processo con indice che è
                           // legato al myturn che è uguale a quell'indice
            while (true) {
                turni[myturn].acquire();
                System.out.println("sono il thread " + getName() + "turno :" + myturn + "id :" + id);
                turni[(myturn + 1) % 4].release();

            }

            // System.out.println(getName() + "eccomi");
            // turni[myturn].release();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}

class ordina extends Thread {

}
