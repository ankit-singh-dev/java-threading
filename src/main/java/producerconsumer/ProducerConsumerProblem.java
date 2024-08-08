package producerconsumer;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerProblem {

    private static int sequenceNo = 0;
    private static Object lock = new Object();
    private static List<Integer> list = new ArrayList<>();
    private static int lowerLimit = 0;

    private static int upperLimit = 5;

    public static void main(String[] args) {

        //producer thread
        Thread producerThread = new Thread(() -> {
            try {
                produceTheNos();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        producerThread.start();


        Thread consumerThread = new Thread(() -> {
            try {
                consumeTheNos();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        consumerThread.start();


    }

    private static void consumeTheNos() throws InterruptedException {
        synchronized (lock){
            while(true){
                if(list.size() == lowerLimit){
                    System.out.println("list is empty produce the nos");
                    lock.wait();
                }else{
                    int no = list.remove(0);
                    System.out.println("consuming the number "+no);
                    lock.notify();
                }
                Thread.sleep(1000);
            }
        }
    }

    private static void produceTheNos() throws InterruptedException {
        synchronized (lock){
            while(true) {
                if (list.size() == upperLimit) {
                    System.out.println("list is full stop producing");
                    lock.wait();
                } else {
                    list.add(sequenceNo++);
                    System.out.println("Producing the number "+sequenceNo);
                    lock.notify();
                }
                Thread.sleep(1000);
            }
        }
    }

}
