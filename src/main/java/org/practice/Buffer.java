package org.practice;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Buffer<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    public Buffer(int capacity){
        this.capacity = capacity;
    }

    public synchronized void produce (T item) throws InterruptedException{
        while(queue.size() == capacity){
            wait();
        }
        queue.offer(item);
        System.out.println(Thread.currentThread().getName() + " Produced -> " + item);
        notifyAll();
    }

    public synchronized T consume() throws InterruptedException{
        while (queue.isEmpty()){
            wait();
        }
        T item = queue.poll();
        System.out.println(Thread.currentThread().getName() + "Consumed -> " + item);
        notifyAll();
        return item;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("What is the fixed size of the buffer : ");
        int capacity = sc.nextInt();
        int beyondCapacity = 10;
        Buffer<String> queue = new Buffer<>(capacity);

        //Anonyms function for producer
        Thread producer = new Thread(()->{
            int i=0;
            for(;;){
                try{
                    ++i;
                    String item = "Task-" + i;
                    queue.produce(item);
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        //Anonyms function for Consumer
        Thread consumer = new Thread(()->{
            while(true){
                try{
                    queue.consume();
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //Making the thread RUNNABLE
        producer.start();
        consumer.start();
    }
}
