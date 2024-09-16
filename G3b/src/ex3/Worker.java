package ex3;

public class Worker extends Thread {
    SynchronizedArrayList synchronizedList;
    int numero;

    public Worker(SynchronizedArrayList synchronizedList, int n) {
        super("Worker");
        this.synchronizedList = synchronizedList;
        this.numero = n;
    }

    public void run() {
        for(int i = 0; i < 5; i++) {
            try{
                synchronizedList.add("Frase " + i + " da thread " + numero);
                Thread.sleep(500 + i*10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                // Restore interrupted status
                Thread.currentThread().interrupt();
            }
        }
    }
}
