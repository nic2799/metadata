import java.util.concurrent.Semaphore;
public class novembre19 {
    public static void main(String[] args) throws Exception {
        int n = 5;

        P[] p = new P[n];

        
        Semaphore[] turni = new Semaphore[n];
        for (int i = 0; i < n; i++) {
        turni[i] = new Semaphore(0);

        }

        for (int i = 0; i < n; i++) {

            p[i] = new P(turni);
            p[i].setName("[p " + i + "]");
            p[i].start();
            // System.out.println("il id:" + p[i].id + " " + p[i].k);
        }

    }
}

class P extends Thread {
    final int n = 5;
    static int ID=0;
    static int next_start;
    static int op = 0;
    int id;
    Semaphore[] turni;
    
    public P(Semaphore[] turni) {
        
        this.turni = turni;
        this.id=ID;
        ID++;
       
        if(ID==n){
            turni[0].release();//abbiamo bisogno di questo perche inizia a mettere verde al primo semaforo pero successivamente all iniz
        }
       
        next_start=ID;
    }
    @Override
    public void run() {
 while(true){
        try {//tutti gli altri che non avreanno quell id andranno a rossso
   
            turni[id].acquire();// quindi il semaforo legato al processo id=0 e verde gli altri rossi quindi turno[0] verde il processo [0] puo entrare
            op++;//quando id=0 entra
            System.out.println(getName() + " iiinserisce un oggetto nel deposito " + id +" " + op);

            turni[(id+1)%n].release();//rilascia come verde il successivo mentre quello precedente essendo acquire rimane a zero quindi rosso quindi ora tutti rossi tranne il succe
        } catch (Exception e) {

        }
    }

    }

}