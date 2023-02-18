import java.util.*;
import java.util.concurrent.Semaphore;;

public class luglio2021 {
    public static void main(String[] args) throws Exception {
        Semaphore[] turni = new Semaphore[4];
        for (int i = 0; i < 4; i++) {
            turni[i] = new Semaphore(0);
        }
        Semaphore[] turni2 = new Semaphore[2];
        // for (int i = 0; i < 2; i++) {
        // turni2[i] = new Semaphore(0);
        // }
        runner[] runners = new runner[4];
        for (int i = 0; i < 2; i++) {
            runners[i] = new runner(turni, turni2);
            runners[i].setName("squadra A " + i);
            runners[i].start();
        }
        for (int i = 2; i < 4; i++) {
            runners[i] = new runner(turni, turni2);
            runners[i].setName("squadra B " + i);
            runners[i].start();
        }

    }
}
