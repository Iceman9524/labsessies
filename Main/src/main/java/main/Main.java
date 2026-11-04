
package main;

import eventbroker.EventBroker;
import order.BlacklistOrderProcessor;
import order.Customer;
import order.OrderProcessor;

import java.util.ArrayList;
import java.util.List;


public class Main {
    
    public static int noOrders = 2;
    private static class customer_thread extends Thread{
        Customer customer;
        public customer_thread(Customer customer){
            this.customer=customer;
        }
        @Override
        public void run() {
            for(int i=0;i<customer.getNoOrders();i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException _) {}
                this.customer.buy("item-" + i);
        }
    }}

    public static void main(String[] args){
        
        String[] names = new String[]{"Jan", "Piet", "Joris", "Corneel"};
        ArrayList<customer_thread> customer_threads=new ArrayList<customer_thread>();
        

        EventBroker.getEventBroker().start();
        BlacklistOrderProcessor blop = BlacklistOrderProcessor.make_BlacklistOrderProcessor();

        for(String name : names){
            Customer customer = new Customer(name,noOrders);

            customer_thread runnable= new customer_thread(customer);
            customer_threads.add(runnable);
            runnable.start();
        }



        customer_threads.forEach((thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));

        try {
            EventBroker.getEventBroker().stop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(OrderProcessor.getNumberOfOrders());
    }
    
}
