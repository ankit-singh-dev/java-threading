package producerconsumer;

public class ServiceNow {

    static class Service {

        final Object lock = new Object();

        boolean isServicePrinted = false;

        public void service() {
            synchronized (lock) {
                lock.notify();
                System.out.println("Service");
                isServicePrinted = true;
                try {
                    Thread.sleep(500);
                    lock.wait(1000);
                } catch (InterruptedException e) {
                }
            }
        }

        public void now() {
            synchronized (lock) {
                lock.notify();
                try {
                    Thread.sleep(500);
                    if(isServicePrinted){
                        System.out.println("Now");
                    }
                    lock.wait(1000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        int noOfThreads = 10;
        var service = new Service();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < noOfThreads; i++) {
                service.service();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < noOfThreads; i++) {
                service.now();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Main thread");
    }

}
