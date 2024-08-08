package countdownlatch;

/*
When all the chefs prepares the food than only we can serve the food.
countdown latch is similar to Join method but there is diffences in them
also we can reset count value of countdownlatch
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExamples {

    public static void main(String[] args) throws InterruptedException {
        int noOfChefs = 3;

        CountDownLatch countDownLatch = new CountDownLatch(noOfChefs);

        new Thread(new Chef("A","Pizza", countDownLatch)).start();
        new Thread(new Chef("B","Pasta", countDownLatch)).start();
        new Thread(new Chef("C","Salad", countDownLatch)).start();

        countDownLatch.await();

        System.out.println("All food items are ready start serving");
    }

}

class Chef implements Runnable{

    private final String name;
    private final String dishName;

    private final CountDownLatch countDownLatch;

    public Chef(String name, String dishName, CountDownLatch countDownLatch) {
        this.name = name;
        this.dishName = dishName;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("chef " + name + " is preparing dish " + dishName);
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        System.out.println("chef " + name + " is done preparing " + dishName);
        this.countDownLatch.countDown();
    }
}
