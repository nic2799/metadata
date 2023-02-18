import java.util.*;
import java.util.concurrent.Semaphore;

public class esame {
    public static void main(String[] args) throws Exception {
        Semaphore mutex = new Semaphore(1);
        Semaphore[] turni = new Semaphore[5];
        Semaphore mut = new Semaphore(5);
        LinkedList<Integer> coda = newq Linke

    }

}

class Produttore extends Thread {
    Semaphore[] turni;
    Semaphore mutex;
    Semaphore mut;

    Produttore(Semaphore[] turni, Semaphore mutex, Semaphore mut) {
        this.mut = mut;
        this.mutex = mutex;
        this.turni = turni;

    }

}