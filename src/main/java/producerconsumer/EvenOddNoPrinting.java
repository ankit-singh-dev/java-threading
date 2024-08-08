package producerconsumer;

public class EvenOddNoPrinting {

    public static void main(String[] args) {
        Worker worker = new Worker(new Object(),20);

        Thread evenPrinter = new Thread(() -> {
            try {
                worker.printEvenNo();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread oddPrinter = new Thread(() -> {
            try {
                worker.printOddNo();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        evenPrinter.start();
        oddPrinter.start();

    }
}

class Worker{

    private int sequenceNo = 0;

    private final int threshHold;
    private final Object lock;

    public Worker(Object lock, int threshHold){
        this.lock = lock;
        this.threshHold = threshHold;
    }

    public void printEvenNo() throws InterruptedException {
        synchronized (lock){
            while(true) {
                if(sequenceNo == threshHold+1){
                    break;
                }
                if (sequenceNo % 2 == 0) {
                    System.out.println("even no "+sequenceNo);
                    sequenceNo = sequenceNo + 1;
                    lock.notify();
                }else{
                    lock.wait();
                }
                Thread.sleep(500);
            }
        }
    }

    public void printOddNo() throws InterruptedException {
        synchronized (lock){
            while(true) {
                if(sequenceNo == threshHold+1){
                    break;
                }
                if (sequenceNo % 2 == 1) {
                    System.out.println("odd no "+sequenceNo);
                    sequenceNo = sequenceNo + 1;
                    lock.notify();
                }else{
                    lock.wait();
                }
                Thread.sleep(500);
            }
        }
    }

}