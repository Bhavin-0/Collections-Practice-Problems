package org.practice;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Task{
    private String name;
    private int priority;

    //Constructor
    public Task(String name, int priority){
        this.name = name;
        this.priority = priority;
    }

    //Getters
    public String getName(){
        return name;
    }

    public int getPriority(){
        return priority;
    }

    //Setters
    public void setName(String name){
        this.name = name;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }

    //To print the final string
    @Override
    public String toString() {
        String priorityLabel;
        switch (priority){
            case 1 -> priorityLabel = "Low";
            case 2 -> priorityLabel = "Medium";
            case 3 -> priorityLabel = "High";
            default -> priorityLabel = "Unknown";
        }
        return "Task { Name = '" +name + "', Priority = " + priorityLabel + " }";
    }
}

public class TaskScheduler {
    Queue<Task> tasks = new LinkedList<>();

    void addTask(Scanner sc){
        System.out.print("\nEnter Task name : ");
        String taskName = sc.nextLine();
        int taskPriority;
        do {
            System.out.print("\nEnter priority Level (1-3) : ");
             taskPriority= sc.nextInt();
             if(isInvalidPriority(taskPriority)){
                 System.out.println("Please try again...");
             }
        }while(isInvalidPriority(taskPriority));
        sc.nextLine();
        Task task = new Task(taskName,taskPriority);
        tasks.offer(task);
    }

    //verify if the priority levels are correct
    boolean isInvalidPriority(int priority){
       return priority<1 || priority >5;
    }

    void showAllTasks(){
        if(tasks.isEmpty()){
            System.out.println("No pending tasks.");
            return;
        }
        System.out.println("Total pending tasks : " + tasks.size());
        System.out.println("List of pending tasks ->");
        int count =0;
        for(Task task : tasks){
            count++;
            System.out.println(count + " -> " + task);
        }
    }

    void showNextTask(){
        Task nextTask = tasks.peek();
        if(tasks.isEmpty() || nextTask == null){
            System.out.println("No record of next task.");
            return;
        }
        System.out.println("Next Task : " + nextTask);
    }

    void processTask(){
        Task processedTask = tasks.poll();
        if(processedTask == null){
            System.out.println("No task pending");
            return;
        }
        System.out.println("Processing ->" + processedTask);
    }

    void cancelTaskByName(Scanner sc){
        System.out.print("Enter task name to delete : ");
        String taskName = sc.nextLine();
        if(tasks.isEmpty()){
            System.out.println("No pending tasks..");
            return;
        }
        boolean removed = false;

        //deleting task using iterator
        Iterator<Task> iterator = tasks.iterator();
        while(iterator.hasNext()){
            Task task = iterator.next();
            if(task.getName().equalsIgnoreCase(taskName)){
                iterator.remove();
                System.out.println("Successfully removed the task : "+ taskName);
                removed = true;
                break;
            }
        }

        if(!removed){
            System.out.println("Task not found !");
        }
    }

    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("======= Task Scheduler =======");
            System.out.println("1. Add Task\n2. Show All Tasks\n3. Show Next Task\n4. Process Task\n5. Cancel Task\n6. Exit");
            System.out.print("\nEnter your choice : ");
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1 -> scheduler.addTask(sc);
                case 2 -> scheduler.showAllTasks();
                case 3 -> scheduler.showNextTask();
                case 4 -> scheduler.processTask();
                case 5 -> scheduler.cancelTaskByName(sc);
                case 6 -> System.out.println("Exiting the Task Schedular Program....");
                default -> System.out.println("Invalid input : enter within (1-6) digits");
            }
        }while(choice != 6);
    }
}
