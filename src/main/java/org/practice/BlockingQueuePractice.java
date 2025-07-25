package org.practice;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Producer implements Runnable{
    BlockingQueue<String> queue;
    String producerName;

    public Producer(BlockingQueue<String> queue,String producerName){
        this.queue = queue;
        this.producerName = producerName;
    }

    @Override
    public void run() {
        try{
         for(int i=0;i<=5;i++){
             String task = producerName + "Task : "+i;
             queue.put(task); //blocks if the queue is full
             System.out.println(producerName + "Produced : " + task);
             Thread.sleep(100);
         }
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        } ;
    }
}

class Consumer implements Runnable{
    BlockingQueue<String> queue;
    String consumerName;

    public Consumer(BlockingQueue<String> queue,String consumerName){
        this.queue = queue;
        this.consumerName = consumerName;
    }

    @Override
    public void run(){
        try{
            for(int i=0;i<=5;i++){
                String task = queue.take();
                System.out.println(consumerName + "Consumed : " + task);
                Thread.sleep(1500);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
public class BlockingQueuePractice {
    public static void main(String[] args) {
        BlockingQueue<String> taskQueue = new ArrayBlockingQueue<>(1);

        Thread producer1 = new Thread(new Producer(taskQueue,"Producer-1"));
        Thread consumer1 = new Thread(new Consumer(taskQueue,"Consumer-1"));

        producer1.start();
        consumer1.start();

    }
}
