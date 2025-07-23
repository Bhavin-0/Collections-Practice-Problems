package org.logger;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;

public class TicketCounterSystem {

     static class TicketCounter{
        Queue<String> token = new LinkedList<>();

        void addCustomer(Scanner input){
            System.out.println("Enter customer name : ");
            String customerName = input.nextLine();
            token.offer(customerName);
            System.out.println("Successfully added : " + customerName);
        }

        void serve(){
            String customer = token.poll();
            if(customer == null){
                System.out.println("No Customers Left to Serve !");
            }else{
                System.out.println("Serving : " + customer);
            }
        }

        void showNextCustomer(){
            if(token.isEmpty()){
                System.out.println("No Customers Left..");
            }else{
                System.out.println("Customer : " + token.peek() );
            }
        }
        void customerList(){
            if(token.isEmpty()){
                System.out.println("Customer List is Empty..");
            }else{
                System.out.println("Customer List : " + token);
            }
        }
        void cancelOrder(){
            try{
                String removed = token.remove();
                System.out.println("Cancel Order for : " + removed);
            }catch (NoSuchElementException e){
                System.out.println("No Customers Orders Left ");
            }
        }

    }


    public static void main(String[] args) {
         TicketCounter system = new TicketCounter();
         Scanner input = new Scanner(System.in);

         int choice;
         do {
             System.out.println("========== Welcome to the Ticket Counter ========== ");
             System.out.println("1. Add Customer\n2. Serve Customer\n3. Show Queue\n4. Peek Next\n5. Cancel order \n6. Exit");
             System.out.println("\nEnter your choice : ");
             choice = input.nextInt();
             input.nextLine();
             switch (choice){
                 case 1:
                     system.addCustomer(input);
                     break;
                 case 2:
                     system.serve();
                     break;
                 case 3:
                     system.customerList();
                     break;
                 case 4:
                     system.showNextCustomer();
                     break;
                 case 5:
                     system.cancelOrder();
                     break;
                 case 6:
                     System.out.println("Exiting the Ticket Counter Application ....");
                     break;
                 default:
                     System.out.println("Invalid choice : enter within (1-6) digits");
             }
         }while(choice != 6);
         input.close();
    }
}
