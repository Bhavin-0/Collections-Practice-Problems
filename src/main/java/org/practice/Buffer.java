package org.practice;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//Understanding Reentrant Locks - conditions, await(),signalAll()

class Buffer<T> {
    private final Queue<T> queue = new LinkedList<>();

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final int capacity;

    public Buffer(int capacity){
        this.capacity = capacity;
    }

    public void produce (T item) throws InterruptedException{
        lock.lock();
        try{
            while(queue.size() == capacity){
                notFull.await();            //wait until there's space
            }
            queue.offer(item);
            System.out.println(Thread.currentThread().getName() + " Produced -> " + item);
            notEmpty.signalAll();          //notify other consumers
        }finally {
            lock.unlock();
        }
    }

    public T consume() throws InterruptedException{
        lock.lock();
        try{
            while (queue.isEmpty()){
                notEmpty.await();       //wait until there's something
            }
            T item = queue.poll();
            System.out.println(Thread.currentThread().getName() + " Consumed -> " + item);
            notFull.signalAll();    //notify other producers
            return item;
        }finally {
            lock.unlock();
        }
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
