
package order;

import eventbroker.EventPublisher;


public class Customer extends EventPublisher{

    private final String name;
    private final int noOrders;

    
    public Customer(String name,int noOrders){
        this.name = name;
        this.noOrders=noOrders;
    }
    
    public void buy(String item){
        System.out.println(name + " places an order for item "+item);
        publishEvent(new OrderEvent(name, item));
    }
    public int getNoOrders(){
        return this.noOrders;
    }

}
